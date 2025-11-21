package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import java.time.LocalDateTime;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.domainmodel.UserBadge;

public record UserBadgeProcedureDTO(
        Long userId,
        Long badgeId,
        LocalDateTime earnedAt) {

    public static UserBadge toEntity(UserBadgeProcedureDTO dto) {
        if (dto == null)
            return null;

        return UserBadge.builder()
                .user(User.builder().userId(dto.userId()).build())
                .badge(Badge.builder().badgeId(dto.badgeId()).build())
                .earnedAt(dto.earnedAt())
                .build();
    }

}
