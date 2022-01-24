package ru.kpfu.itis.pdfgenerator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.pdfgenerator.dto.SimplePdfDto;
import ru.kpfu.itis.pdfgenerator.exceptions.JsonException;

public class JsonUtil {

    public static String pdfDtoToJson(SimplePdfDto simplePdfDto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(simplePdfDto);
        } catch (JsonProcessingException e) {
            throw new JsonException("OBJECT_NOT_WRITE_TO_JSON");
        }
    }

}
