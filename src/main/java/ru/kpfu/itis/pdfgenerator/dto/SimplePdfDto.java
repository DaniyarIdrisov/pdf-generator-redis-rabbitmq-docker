package ru.kpfu.itis.pdfgenerator.dto;

import lombok.*;
import ru.kpfu.itis.pdfgenerator.utils.PdfType;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimplePdfDto {

    private PdfType pdfType;

    private String filename;

    private Long userId;

}
