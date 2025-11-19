package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Badge.BadgeResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment.EnrollmentResponseDTO;
import lombok.Builder;

@Builder
public record UserProfileResponseDTO(
        Long userId,
        String fullName,
        String email,
        String biography,
        String whatsapp,
        String profileImage,
        LocalDateTime createdAt,
        List<EnrollmentResponseDTO> enrollments,
        List<BadgeResponseDTO> badges) {

    public static UserProfileResponseDTO from(User user) {
        if (user == null)
            return null;

        return UserProfileResponseDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .biography(user.getBiography())
                .whatsapp(user.getWhatsapp())
                .profileImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .enrollments(user.getEnrollments() != null
                        ? user.getEnrollments().stream().map(EnrollmentResponseDTO::from).toList()
                        : new ArrayList<>())
                .badges(user.getBadges() != null
                        ? user.getBadges().stream().map(BadgeResponseDTO::from).toList()
                        : new ArrayList<>())
                .build();
    }

}
