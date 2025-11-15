package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.domainmodel.UserBadge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String fullName;
    private String email;
    private String hashedPassword;
    private String biography;
    private String whatsapp;
    private String profileImage;
    private LocalDateTime createdAt;
    private List<Enrollment> enrollments;
    private List<UserBadge> badges;

    public static UserResponseDTO fromEntity(User user) {
        if (user == null)
            return null;

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .hashedPassword(user.getHashedPassword())
                .biography(user.getBiography())
                .whatsapp(user.getWhatsapp())
                .profileImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .enrollments(user.getEnrollments())
                .badges(user.getBadges())
                .build();
    }

    public static User toEntity(UserResponseDTO dto) {
        if (dto == null)
            return null;

        return User.builder()
                .userId(dto.getUserId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .hashedPassword(dto.getHashedPassword())
                .biography(dto.getBiography())
                .whatsapp(dto.getWhatsapp())
                .profileImage(dto.getProfileImage())
                .createdAt(dto.getCreatedAt())
                .enrollments(dto.getEnrollments())
                .badges(dto.getBadges())
                .build();
    }

}
