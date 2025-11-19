package br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course;

import java.time.LocalDateTime;

public record DetailedCourseRow(
        Long courseId,
        String title,
        String description,
        String author,
        String thumbnailUrl,
        LocalDateTime createdAt,

        Long tagId,
        String tagDescription,

        Long badgeId,
        String badgeTitle,
        String iconUrl,

        Long contentId,
        String type,
        Integer position) {

}
