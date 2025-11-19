package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequestDTO {

    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    private String password;

}
