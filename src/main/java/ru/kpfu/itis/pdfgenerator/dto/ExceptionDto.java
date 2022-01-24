package ru.kpfu.itis.pdfgenerator.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private Instant timestamp;

    private Integer code;

    private String message;

    private String error;

}
