package ru.kpfu.itis.pdfgenerator.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.kpfu.itis.pdfgenerator.dto.PdfDto;
import ru.kpfu.itis.pdfgenerator.models.Pdf;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = UsersMapper.class)
public interface PdfMapper {

    PdfDto toPdfDto(Pdf pdf);

    Pdf toPdf(PdfDto pdfDto);

}
