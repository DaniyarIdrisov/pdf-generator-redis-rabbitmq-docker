package ru.kpfu.itis.pdfgenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.pdfgenerator.dto.TokenDto;
import ru.kpfu.itis.pdfgenerator.dto.UserDto;
import ru.kpfu.itis.pdfgenerator.dto.UserSignInForm;
import ru.kpfu.itis.pdfgenerator.dto.UserSignUpForm;
import ru.kpfu.itis.pdfgenerator.services.UsersService;

import javax.annotation.security.PermitAll;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @PermitAll
    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody UserSignUpForm userSignUpForm) {
        return usersService.signUp(userSignUpForm);
    }

    @PermitAll
    @PostMapping("/signIn")
    public TokenDto signIn(@RequestBody UserSignInForm userSignInForm) {
        return usersService.signIn(userSignInForm);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/logout")
    public void logout(@RequestHeader("Authorization") String bearerToken) {
        usersService.logout(bearerToken);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return usersService.getUserByContext();
    }

}
