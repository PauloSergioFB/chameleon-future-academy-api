package br.com.fiap.chameleonfutureacademy.infrastructure.config;

import lombok.Builder;

@Builder
public record JwtUserData(
        Long userId,
        String email) {

}
