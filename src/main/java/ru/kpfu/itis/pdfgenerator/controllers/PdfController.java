package ru.kpfu.itis.pdfgenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.pdfgenerator.dto.PdfDto;
import ru.kpfu.itis.pdfgenerator.dto.PdfForAddressDto;
import ru.kpfu.itis.pdfgenerator.dto.PdfForBankAccountDto;
import ru.kpfu.itis.pdfgenerator.services.PdfService;
import ru.kpfu.itis.pdfgenerator.services.ProducerService;
import ru.kpfu.itis.pdfgenerator.utils.JsonUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf")
public class PdfController {

    private final ProducerService producerService;
    private final PdfService pdfService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/address")
    public ResponseEntity<String> generatePdfForAddress(@RequestBody PdfForAddressDto pdfForAddressDto) {
        return ResponseEntity.ok(
                producerService.convertAndSend(pdfForAddressDto.getPdfType().toString(),
                        JsonUtil.pdfDtoToJson(pdfForAddressDto)
                )
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/bankAccount")
    public ResponseEntity<String> generatePdfForBankAccount(@RequestBody PdfForBankAccountDto pdfForBankAccountDto) {
        return ResponseEntity.ok(
                producerService.convertAndSend(pdfForBankAccountDto.getPdfType().toString(),
                        JsonUtil.pdfDtoToJson(pdfForBankAccountDto)
                )
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{pdf-id}")
    public ResponseEntity<byte []> getPdfById(@PathVariable("pdf-id") Long pdfId) {
        PdfDto pdfDto = pdfService.getPdfBytesById(pdfId);
         return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment").filename(pdfDto.getFilename()).build().toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(pdfDto.getPdfBytes());
    }

}
