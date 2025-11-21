package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;

public record LessonProcedureDTO(
        Long contentId,
        String title,
        String body) {

    public static Lesson toEntity(LessonProcedureDTO dto) {
        if (dto == null)
            return null;

        return Lesson.builder()
                .content(Content.builder().contentId(dto.contentId()).build())
                .title(dto.title())
                .body(dto.body())
                .build();
    }

}