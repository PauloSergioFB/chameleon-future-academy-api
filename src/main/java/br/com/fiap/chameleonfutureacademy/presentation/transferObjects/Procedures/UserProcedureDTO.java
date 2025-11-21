package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public record UserProcedureDTO(
        String name,
        String email,
        String password,
        String biography,
        String whatsapp,
        String profileImage) {

    public static User toEntity(UserProcedureDTO dto) {
        if (dto == null)
            return null;

        return User.builder()
                .fullName(dto.name())
                .email(dto.email())
                .hashedPassword(dto.password())
                .biography(dto.biography())
                .whatsapp(dto.whatsapp())
                .profileImage(dto.profileImage())
                .build();
    }

}
