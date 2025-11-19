package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User;

import java.time.LocalDateTime;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long userId,
        String fullName,
        String email,
        String biography,
        String whatsapp,
        String profileImage,
        LocalDateTime createdAt) {

    public static UserResponseDTO from(User user) {
        if (user == null)
            return null;

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .biography(user.getBiography())
                .whatsapp(user.getWhatsapp())
                .profileImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
