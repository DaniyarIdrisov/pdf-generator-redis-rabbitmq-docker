package ru.kpfu.itis.pdfgenerator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public String convertAndSend(String type, String json) {
        log.info("PDF SEND BY TYPE {} & JSON {}", type, json);
        rabbitTemplate.convertAndSend(type, json);
        return json;
    }

}
