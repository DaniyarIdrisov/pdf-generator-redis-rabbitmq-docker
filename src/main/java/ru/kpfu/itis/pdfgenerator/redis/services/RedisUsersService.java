package ru.kpfu.itis.pdfgenerator.redis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.models.User;
import ru.kpfu.itis.pdfgenerator.redis.models.RedisUser;
import ru.kpfu.itis.pdfgenerator.redis.repositories.RedisUsersRepository;
import ru.kpfu.itis.pdfgenerator.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisUsersService {

    private final UsersRepository usersRepository;
    private final BlackListService blackListService;
    private final RedisUsersRepository redisUsersRepository;

    public void addTokenToUser(User user, String token) {
        String redisId = user.getRedisId();

        RedisUser redisUser;
        if (redisId != null && !redisId.equals("")) {
            redisUser = redisUsersRepository.findById(redisId).orElseThrow(IllegalArgumentException::new);
            if (redisUser.getTokens() == null) {
                redisUser.setTokens(new ArrayList<>());
            }
            redisUser.getTokens().add(token);
        } else {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(Collections.singletonList(token))
                    .build();
        }
        redisUsersRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        usersRepository.save(user);
    }

}
