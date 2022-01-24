package ru.kpfu.itis.pdfgenerator.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInForm {

    private String login;

    private String password;

}
