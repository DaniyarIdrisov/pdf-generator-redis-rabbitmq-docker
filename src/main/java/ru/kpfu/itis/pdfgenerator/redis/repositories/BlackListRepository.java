package ru.kpfu.itis.pdfgenerator.redis.repositories;

public interface BlackListRepository {

    void save(String token);

    boolean exists(String token);

}
