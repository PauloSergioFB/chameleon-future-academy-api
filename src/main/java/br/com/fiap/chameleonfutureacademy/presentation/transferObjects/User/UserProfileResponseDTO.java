package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.User.UserProfileRow;
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

    public static UserProfileResponseDTO from(List<UserProfileRow> rows) {
        if (rows == null || rows.isEmpty())
            return null;

        UserProfileRow user = rows.get(0);

        List<EnrollmentResponseDTO> enrollments = new ArrayList<>();
        List<BadgeResponseDTO> badges = new ArrayList<>();

        Set<Long> addedEnrollmentIds = new HashSet<>();
        Set<Long> addedBadgeIds = new HashSet<>();

        for (var row : rows) {

            if (row.enrollmentId() != null && !addedEnrollmentIds.contains(row.enrollmentId())) {
                enrollments.add(new EnrollmentResponseDTO(
                        row.enrollmentId(),
                        row.userId(),
                        row.courseId(),
                        row.progress(),
                        row.status(),
                        row.startedAt(),
                        row.completedAt()));

                addedEnrollmentIds.add(row.enrollmentId());
            }

            if (row.badgeId() != null && !addedBadgeIds.contains(row.badgeId())) {
                badges.add(new BadgeResponseDTO(
                        row.badgeId(),
                        row.courseId(),
                        row.badgeTitle(),
                        row.badgeIconUrl()));

                addedBadgeIds.add(row.badgeId());
            }
        }

        return new UserProfileResponseDTO(
                user.userId(),
                user.fullName(),
                user.email(),
                user.biography(),
                user.whatsapp(),
                user.profileImage(),
                user.userCreatedAt(),
                enrollments,
                badges);
    }

}
