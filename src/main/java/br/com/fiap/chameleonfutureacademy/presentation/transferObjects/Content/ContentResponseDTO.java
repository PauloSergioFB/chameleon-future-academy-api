package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Content;

import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import lombok.Builder;

@Builder
public record ContentResponseDTO(
        Long contentId,
        Long courseId,
        String type,
        Integer position) {

    public static ContentResponseDTO from(Content content) {
        if (content == null)
            return null;

        return ContentResponseDTO.builder()
                .contentId(content.getContentId())
                .courseId(content.getCourse().getCourseId())
                .type(content.getType())
                .position(content.getPosition())
                .build();
    }

}
