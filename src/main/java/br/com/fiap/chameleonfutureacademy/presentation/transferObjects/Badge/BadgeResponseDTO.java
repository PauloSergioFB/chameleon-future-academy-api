package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Badge;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.UserBadge;
import lombok.Builder;

@Builder
public record BadgeResponseDTO(
        Long badgeId,
        Long courseId,
        String title,
        String iconUrl) {

    public static BadgeResponseDTO from(Badge badge) {
        if (badge == null)
            return null;

        return BadgeResponseDTO.builder()
                .badgeId(badge.getBadgeId())
                .courseId(badge.getCourse().getCourseId())
                .title(badge.getTitle())
                .iconUrl(badge.getIconUrl())
                .build();
    }

    public static BadgeResponseDTO from(UserBadge badge) {
        if (badge == null)
            return null;

        return from(badge.getBadge());
    }

}
