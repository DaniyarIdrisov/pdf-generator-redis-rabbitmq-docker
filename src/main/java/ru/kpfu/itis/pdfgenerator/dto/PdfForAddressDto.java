package ru.kpfu.itis.pdfgenerator.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PdfForAddressDto extends SimplePdfDto {

    private String state;

    private String city;

    private String postcode;

    private String homeAddress;

}
