package com.example.jobtracker_api.model.entity.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignupRequest {
    @NotBlank @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
