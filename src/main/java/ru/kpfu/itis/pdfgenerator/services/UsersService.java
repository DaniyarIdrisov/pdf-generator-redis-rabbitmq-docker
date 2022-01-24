package ru.kpfu.itis.pdfgenerator.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.dto.TokenDto;
import ru.kpfu.itis.pdfgenerator.dto.UserDto;
import ru.kpfu.itis.pdfgenerator.dto.UserSignInForm;
import ru.kpfu.itis.pdfgenerator.dto.UserSignUpForm;
import ru.kpfu.itis.pdfgenerator.exceptions.InvalidCredentialsException;
import ru.kpfu.itis.pdfgenerator.mappers.UsersMapper;
import ru.kpfu.itis.pdfgenerator.models.User;
import ru.kpfu.itis.pdfgenerator.redis.services.BlackListService;
import ru.kpfu.itis.pdfgenerator.redis.services.RedisUsersService;
import ru.kpfu.itis.pdfgenerator.repositories.UsersRepository;

import java.time.Instant;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisUsersService redisUsersService;
    private final Algorithm algorithm;
    private final BlackListService blackListService;
    private final TokenService tokenService;

    public UserDto signUp(UserSignUpForm userSignUpForm) {
        User user = User.builder()
                .login(userSignUpForm.getLogin())
                .hashPassword(passwordEncoder.encode(userSignUpForm.getPassword()))
                .name(userSignUpForm.getName())
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .build();
        usersRepository.save(user);
        return usersMapper.toUserDto(user);
    }

    @SneakyThrows
    public TokenDto signIn(UserSignInForm userSignInForm) {
        User user = usersRepository.findByLogin(userSignInForm.getLogin())
                .orElseThrow((Supplier<Throwable>) () -> new InvalidCredentialsException("INVALID_LOGIN_OR_PASSWORD"));
        if (passwordEncoder.matches(userSignInForm.getPassword(), user.getHashPassword())) {
            String token = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("login", user.getLogin())
                    .withClaim("createdAt", Instant.now().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("state", user.getState().toString())
                    .sign(algorithm);
            redisUsersService.addTokenToUser(user, token);
            return TokenDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new InvalidCredentialsException("INVALID_USERNAME_OR_PASSWORD");
        }
    }

    public void logout(String bearerToken) {
        String token = tokenService.getTokenFromHeader(bearerToken);
        blackListService.add(token);
        SecurityContextHolder.clearContext();
    }

    @SneakyThrows
    public UserDto getUserByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getCredentials();
        User user = usersRepository.findByLogin(login)
                .orElse(null);
        if (user != null) {
            return usersMapper.toUserDto(user);
        }
        return null;
    }

}
