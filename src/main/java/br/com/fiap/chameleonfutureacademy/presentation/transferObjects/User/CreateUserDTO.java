package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateUserDTO {

    @NotBlank(message = "O nome do usuário não pode estar em branco")
    @Size(max = 100, message = "O nome do usuário deve ter no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres")
    private String password;

    @Size(max = 500, message = "A biografia deve ter no máximo 500 caracteres")
    private String biography;

    @Pattern(regexp = "^[1-9]{2}[0-9]{8,9}$", message = "O número de WhatsApp deve ser válido (somente dígitos, 10 ou 11 caracteres)")
    private String whatsapp;

    @Pattern(regexp = "^https?://[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(/.*)?$", message = "A URL da foto de perfil é inválida")
    @Size(max = 100, message = "A URL da foto de perfil deve ter no máximo 100 caracteres")
    private String profileImage;

    public static CreateUserDTO fromEntity(User user) {
        if (user == null)
            return null;

        return CreateUserDTO.builder()
                .name(user.getFullName())
                .email(user.getEmail())
                .password(user.getHashedPassword())
                .biography(user.getBiography())
                .whatsapp(user.getWhatsapp())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static User toEntity(CreateUserDTO dto) {
        if (dto == null)
            return null;

        return User.builder()
                .fullName(dto.getName())
                .email(dto.getEmail())
                .hashedPassword(dto.getPassword())
                .biography(dto.getBiography())
                .whatsapp(dto.getWhatsapp())
                .profileImage(dto.getProfileImage())
                .build();
    }

}
