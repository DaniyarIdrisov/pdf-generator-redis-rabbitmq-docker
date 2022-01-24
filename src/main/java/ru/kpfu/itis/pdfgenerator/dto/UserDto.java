package ru.kpfu.itis.pdfgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.pdfgenerator.models.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String login;

    private String hashPassword;

    private String name;

    private User.Role role;

    private User.State state;

}
