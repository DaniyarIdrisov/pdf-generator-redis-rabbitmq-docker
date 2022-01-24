package ru.kpfu.itis.pdfgenerator.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.pdfgenerator.redis.models.RedisUser;

@Repository
public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {
}
