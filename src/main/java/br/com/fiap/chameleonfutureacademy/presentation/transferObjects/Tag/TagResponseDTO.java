package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDTO {

    private Long tagId;
    private String description;

    public static TagResponseDTO fromEntity(Tag tag) {
        if (tag == null)
            return null;

        return TagResponseDTO.builder()
                .tagId(tag.getTagId())
                .description(tag.getDescription())
                .build();
    }

    public static Tag toEntity(TagResponseDTO dto) {
        if (dto == null)
            return null;

        return Tag.builder()
                .tagId(dto.getTagId())
                .description(dto.getDescription())
                .build();
    }

}
