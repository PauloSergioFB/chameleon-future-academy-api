package br.com.fiap.chameleonfutureacademy.infrastructure.queries.User;

import java.time.LocalDateTime;

public record UserProfileRow(
        Long userId,
        String fullName,
        String email,
        String biography,
        String whatsapp,
        String profileImage,
        LocalDateTime userCreatedAt,

        Long enrollmentId,
        Integer progress,
        String status,
        LocalDateTime startedAt,
        LocalDateTime completedAt,

        Long courseId,
        String courseTitle,
        String courseDescription,
        String courseAuthor,
        String courseThumbnailUrl,
        LocalDateTime courseCreatedAt,

        Long badgeId,
        String badgeTitle,
        String badgeIconUrl) {

}
