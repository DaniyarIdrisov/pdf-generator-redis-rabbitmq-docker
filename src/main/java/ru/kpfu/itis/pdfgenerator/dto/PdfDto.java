package ru.kpfu.itis.pdfgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfDto {

    private Long id;

    private String type;

    private byte [] pdfBytes;

    private String filename;

    private UserDto userDto;

}
