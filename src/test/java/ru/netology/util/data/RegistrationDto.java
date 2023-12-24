package ru.netology.util.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationDto {
    private final String login;
    private final String password;
    private final String status;
}
