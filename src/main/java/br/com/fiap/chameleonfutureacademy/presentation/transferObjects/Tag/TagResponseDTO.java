package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import lombok.Builder;

@Builder
public record TagResponseDTO(
        Long tagId,
        String description) {

    public static TagResponseDTO from(Tag tag) {
        if (tag == null)
            return null;

        return TagResponseDTO.builder()
                .tagId(tag.getTagId())
                .description(tag.getDescription())
                .build();
    }

}
