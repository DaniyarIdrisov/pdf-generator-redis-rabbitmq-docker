package ru.kpfu.itis.pdfgenerator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.exceptions.InvalidJWTException;

@Service
@RequiredArgsConstructor
public class TokenService {

    public String getTokenFromHeader(String bearerToken) {
        String token;
        try {
            String [] split = bearerToken.split(" ");
            token = split[1];
        } catch (Exception e) {
            throw new InvalidJWTException("INVALID_JWT");
        }
        return token;
    }

}
