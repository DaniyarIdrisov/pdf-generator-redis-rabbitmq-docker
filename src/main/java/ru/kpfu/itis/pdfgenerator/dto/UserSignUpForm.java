package ru.kpfu.itis.pdfgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpForm {

    private String login;

    private String password;

    private String name;

}
