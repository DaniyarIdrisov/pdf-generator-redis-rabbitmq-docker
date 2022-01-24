package ru.kpfu.itis.pdfgenerator.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.pdfgenerator.dto.PdfDto;
import ru.kpfu.itis.pdfgenerator.dto.PdfForAddressDto;
import ru.kpfu.itis.pdfgenerator.dto.PdfForBankAccountDto;
import ru.kpfu.itis.pdfgenerator.exceptions.InvalidCredentialsException;
import ru.kpfu.itis.pdfgenerator.exceptions.PdfCreatingException;
import ru.kpfu.itis.pdfgenerator.exceptions.PdfNotFoundException;
import ru.kpfu.itis.pdfgenerator.exceptions.UserNotFoundException;
import ru.kpfu.itis.pdfgenerator.mappers.PdfMapper;
import ru.kpfu.itis.pdfgenerator.mappers.UsersMapper;
import ru.kpfu.itis.pdfgenerator.models.Pdf;
import ru.kpfu.itis.pdfgenerator.models.User;
import ru.kpfu.itis.pdfgenerator.repositories.PdfsRepository;
import ru.kpfu.itis.pdfgenerator.repositories.UsersRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final PdfsRepository pdfsRepository;
    private final PdfMapper pdfMapper;
    private final UsersRepository usersRepository;

    @SneakyThrows
    public byte[] generatePdfForAddress(PdfForAddressDto pdfForAddressDto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();

            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.setLeading(14.5f);

            content.newLineAtOffset(25, 700);

            String state = "State: " + pdfForAddressDto.getState();
            content.showText(state);
            content.newLine();

            String city = "City: " + pdfForAddressDto.getCity();
            content.showText(city);
            content.newLine();

            String postcode = "Postcode: " + pdfForAddressDto.getPostcode();
            content.showText(postcode);
            content.newLine();

            String homeAddress = "Home address: " + pdfForAddressDto.getHomeAddress();
            content.showText(homeAddress);
            content.newLine();

            content.endText();
            content.close();

            document.save(byteArrayOutputStream);
            document.close();

            User user = usersRepository.findById(pdfForAddressDto.getUserId())
                    .orElseThrow((Supplier<Throwable>) () -> new UserNotFoundException("USER_NOT_FOUND"));
            Pdf pdf = Pdf.builder()
                    .user(user)
                    .pdfBytes(byteArrayOutputStream.toByteArray())
                    .type(pdfForAddressDto.getPdfType().name())
                    .filename(pdfForAddressDto.getFilename())
                    .build();
            pdfsRepository.save(pdf);

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new PdfCreatingException("ERROR_CREATING_PDF");
        }
    }

    @SneakyThrows
    public byte[] generatePdfForBankAccount(PdfForBankAccountDto pdfForBankAccountDto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();

            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.setLeading(14.5f);

            content.newLineAtOffset(25, 700);

            String number = "Number: " + pdfForBankAccountDto.getNumber();
            content.showText(number);
            content.newLine();

            String validity = "Validity: " +pdfForBankAccountDto.getValidity();
            content.showText(validity);
            content.newLine();

            String name = "Name: " + pdfForBankAccountDto.getName();
            content.showText(name);
            content.newLine();

            String secretCode = "Secret code: " + pdfForBankAccountDto.getSecretCode();
            content.showText(secretCode);
            content.newLine();

            content.endText();
            content.close();

            document.save(byteArrayOutputStream);
            document.close();

            User user = usersRepository.findById(pdfForBankAccountDto.getUserId())
                    .orElseThrow((Supplier<Throwable>) () -> new UserNotFoundException("USER_NOT_FOUND"));
            Pdf pdf = Pdf.builder()
                    .user(user)
                    .pdfBytes(byteArrayOutputStream.toByteArray())
                    .type(pdfForBankAccountDto.getPdfType().name())
                    .filename(pdfForBankAccountDto.getFilename())
                    .build();
            pdfsRepository.save(pdf);

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new PdfCreatingException("ERROR_CREATING_PDF");
        }
    }

    @SneakyThrows
    public PdfDto getPdfBytesById(Long pdfId) {
        Pdf pdf = pdfsRepository.findById(pdfId)
                .orElseThrow((Supplier<Throwable>) () -> new PdfNotFoundException("PDF_NOT_FOUND"));
        return pdfMapper.toPdfDto(pdf);
    }

}
