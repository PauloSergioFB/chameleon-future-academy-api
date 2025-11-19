package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequestDTO {

        @NotEmpty(message = "Refresh Token é obrigatório")
        private String refreshToken;

}
