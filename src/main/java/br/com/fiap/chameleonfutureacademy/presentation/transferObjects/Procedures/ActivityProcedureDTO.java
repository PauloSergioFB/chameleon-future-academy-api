package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.domainmodel.Content;

public record ActivityProcedureDTO(
        Long contentId,
        String title,
        String body,
        String explanation) {

    public static Activity toEntity(ActivityProcedureDTO dto) {
        if (dto == null)
            return null;

        return Activity.builder()
                .content(Content.builder().contentId(dto.contentId()).build())
                .title(dto.title())
                .body(dto.body())
                .explanation(dto.explanation())
                .build();
    }

}
