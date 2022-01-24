package ru.kpfu.itis.pdfgenerator.redis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.redis.repositories.BlackListRepository;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public void add(String token) {
        blackListRepository.save(token);
    }

    public boolean exists(String token) {
        return blackListRepository.exists(token);
    }

}
