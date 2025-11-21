package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.Course;

public record ContentProcedureDTO(
        Long courseId,
        String type,
        Integer position) {

    public static Content toEntity(ContentProcedureDTO dto) {
        if (dto == null)
            return null;

        return Content.builder()
                .course(Course.builder().courseId(dto.courseId()).build())
                .type(dto.type())
                .position(dto.position())
                .build();
    }

}
