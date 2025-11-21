package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;

public record TagProcedureDTO(
        String description) {

    public static Tag toEntity(TagProcedureDTO dto) {
        if (dto == null)
            return null;

        return Tag.builder()
                .description(dto.description())
                .build();
    }

}
