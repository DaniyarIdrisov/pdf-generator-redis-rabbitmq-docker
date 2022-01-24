package ru.kpfu.itis.pdfgenerator.controllers;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kpfu.itis.pdfgenerator.dto.ExceptionDto;
import ru.kpfu.itis.pdfgenerator.exceptions.*;

import java.time.Instant;

@RestControllerAdvice
public class ControllerAdvice {

    @SneakyThrows
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionDto> invalidCredentialsException(Exception exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .timestamp(Instant.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @SneakyThrows
    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<ExceptionDto> invalidJWTException(Exception exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .timestamp(Instant.now())
                .code(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @SneakyThrows
    @ExceptionHandler({PdfCreatingException.class, JsonException.class})
    public ResponseEntity<ExceptionDto> internalServerError(Exception exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .timestamp(Instant.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @SneakyThrows
    @ExceptionHandler({PdfNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionDto> notFound(Exception exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .timestamp(Instant.now())
                .code(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
        return ResponseEntity.badRequest().body(exceptionDto);
    }

}
