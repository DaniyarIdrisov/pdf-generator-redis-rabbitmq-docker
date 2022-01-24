package ru.kpfu.itis.pdfgenerator.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PdfForBankAccountDto extends SimplePdfDto {

    private String number;

    private String validity;

    private String name;

    private String secretCode;

}
