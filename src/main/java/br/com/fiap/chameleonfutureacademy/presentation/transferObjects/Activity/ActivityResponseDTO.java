package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Activity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.ActivityOption.ActivityOptionResponseDTO;
import lombok.Builder;

@Builder
public record ActivityResponseDTO(
        Long activityId,
        Long contentId,
        String title,
        String body,
        String explanation,
        LocalDateTime createdAt,
        List<ActivityOptionResponseDTO> options) {

    public static ActivityResponseDTO from(Activity activity) {
        if (activity == null)
            return null;

        return ActivityResponseDTO.builder()
                .activityId(activity.getActivityId())
                .contentId(activity.getContent().getContentId())
                .title(activity.getTitle())
                .body(activity.getBody())
                .explanation(activity.getExplanation())
                .createdAt(activity.getCreatedAt())
                .options(activity.getActivityOptions() != null
                        ? activity.getActivityOptions().stream().map(ActivityOptionResponseDTO::from).toList()
                        : new ArrayList<>())
                .build();
    }

}
