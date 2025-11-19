package br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course;

import java.time.LocalDateTime;

public record CourseTagRow(
        Long courseId,
        String title,
        String author,
        String thumbnailUrl,
        LocalDateTime createdAt,
        Long tagId,
        String tagDescription) {
}
