package ru.kpfu.itis.pdfgenerator.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.kpfu.itis.pdfgenerator.dto.UserDto;
import ru.kpfu.itis.pdfgenerator.models.User;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

}
