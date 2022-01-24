package ru.kpfu.itis.pdfgenerator.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.pdfgenerator.exceptions.InvalidJWTException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Algorithm algorithm;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication tokenAuthentication = (JwtAuthentication) authentication;

        DecodedJWT jwt;

        try {
            jwt = JWT
                    .require(algorithm)
                    .build()
                    .verify(authentication.getName());

        } catch (JWTVerificationException e) {
            throw new InvalidJWTException("BAD_JWT_TOKEN");
        }

        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setAuthority(jwt.getClaim("role").asString());
        tokenAuthentication.setLogin(jwt.getClaim("login").asString());
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }

}
