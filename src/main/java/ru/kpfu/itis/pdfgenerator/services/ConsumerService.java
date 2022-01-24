package ru.kpfu.itis.pdfgenerator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.dto.PdfForAddressDto;
import ru.kpfu.itis.pdfgenerator.dto.PdfForBankAccountDto;
import ru.kpfu.itis.pdfgenerator.exceptions.JsonException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PdfService pdfService;

    @RabbitListener(queues = "queue_address")
    public void addressWorker(String message) {
        log.info("MESSAGE ACCEPTED {}", message);
        PdfForAddressDto pdf;
        try {
            pdf = objectMapper.readValue(message, PdfForAddressDto.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON_NOT_READ");
        }
        pdfService.generatePdfForAddress(pdf);
    }

    @RabbitListener(queues = "queue_bank_account")
    public void bankAccountWorker(String message) {
        log.info("MESSAGE ACCEPTED {}", message);
        PdfForBankAccountDto pdf;
        try {
            pdf = objectMapper.readValue(message, PdfForBankAccountDto.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("JSON_NOT_READ");
        }
        pdfService.generatePdfForBankAccount(pdf);
    }

}
