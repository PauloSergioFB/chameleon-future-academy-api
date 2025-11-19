package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Lesson;

import java.time.LocalDateTime;

import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;
import lombok.Builder;

@Builder
public record LessonResponseDTO(
        Long lessonId,
        Long contentId,
        String title,
        String body,
        LocalDateTime createdAt) {

    public static LessonResponseDTO from(Lesson lesson) {
        if (lesson == null)
            return null;

        return LessonResponseDTO.builder()
                .lessonId(lesson.getLessonId())
                .contentId(lesson.getContent().getContentId())
                .title(lesson.getTitle())
                .body(lesson.getBody())
                .createdAt(lesson.getCreatedAt())
                .build();
    }

}
