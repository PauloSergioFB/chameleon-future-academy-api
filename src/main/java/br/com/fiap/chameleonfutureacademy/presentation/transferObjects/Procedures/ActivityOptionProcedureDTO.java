package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.domainmodel.ActivityOption;

public record ActivityOptionProcedureDTO(
        Long activityId,
        String label,
        String description,
        boolean isCorrect) {

    public static ActivityOption toEntity(ActivityOptionProcedureDTO dto) {
        if (dto == null)
            return null;

        return ActivityOption.builder()
                .activity(Activity.builder().activityId(dto.activityId()).build())
                .label(dto.label())
                .description(dto.description())
                .isCorrect(dto.isCorrect())
                .build();
    }

}
