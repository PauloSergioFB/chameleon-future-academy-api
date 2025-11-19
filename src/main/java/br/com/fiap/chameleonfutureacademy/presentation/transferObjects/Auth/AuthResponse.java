package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth;

public record AuthResponse(
        String token,
        String refreshToken) {

}
