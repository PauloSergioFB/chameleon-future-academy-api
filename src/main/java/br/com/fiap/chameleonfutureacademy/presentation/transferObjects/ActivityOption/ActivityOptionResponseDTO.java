package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.ActivityOption;

import br.com.fiap.chameleonfutureacademy.domainmodel.ActivityOption;
import lombok.Builder;

@Builder
public record ActivityOptionResponseDTO(
        Long activityOptionId,
        Long activityId,
        String label,
        String description,
        Boolean isCorrect) {

    public static ActivityOptionResponseDTO from(ActivityOption activityOption) {
        if (activityOption == null)
            return null;

        return ActivityOptionResponseDTO.builder()
                .activityOptionId(activityOption.getActivityOptionId())
                .activityId(activityOption.getActivity().getActivityId())
                .label(activityOption.getLabel())
                .description(activityOption.getDescription())
                .isCorrect(activityOption.getIsCorrect())
                .build();
    }
}
