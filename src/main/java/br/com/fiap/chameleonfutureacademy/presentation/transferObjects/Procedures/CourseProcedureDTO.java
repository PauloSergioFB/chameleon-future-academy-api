package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;

public record CourseProcedureDTO(
        String title,
        String description,
        String author,
        String thumbnailUrl) {

    public static Course toEntity(CourseProcedureDTO dto) {
        if (dto == null)
            return null;

        return Course.builder()
                .title(dto.title())
                .description(dto.description())
                .author(dto.author())
                .thumbnailUrl(dto.thumbnailUrl())
                .build();
    }

}
