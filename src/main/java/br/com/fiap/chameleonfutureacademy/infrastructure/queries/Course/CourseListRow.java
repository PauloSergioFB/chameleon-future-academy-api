package br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course;

import java.time.LocalDateTime;

public record CourseListRow(
        Long courseId,
        String title,
        String author,
        String thumbnailUrl,
        LocalDateTime createdAt,
        String tagDescription) {
}
