package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.Course;

public record BadgeProcedureDTO(
        Long courseId,
        String title,
        String iconUrl) {

    public static Badge toEntity(BadgeProcedureDTO dto) {
        if (dto == null)
            return null;

        return Badge.builder()
                .course(Course.builder().courseId(dto.courseId()).build())
                .title(dto.title())
                .iconUrl(dto.iconUrl())
                .build();
    }

}
