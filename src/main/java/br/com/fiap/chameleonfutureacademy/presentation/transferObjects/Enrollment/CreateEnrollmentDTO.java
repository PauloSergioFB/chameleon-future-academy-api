package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateEnrollmentDTO {

    @NotNull(message = "O ID do usuário não pode ser nulo")
    @Positive(message = "O ID do usuário deve ser um número positivo")
    private Long userId;

    @NotNull(message = "O ID do curso não pode ser nulo")
    @Positive(message = "O ID do curso deve ser um número positivo")
    private Long courseId;

    @NotNull(message = "O status não pode estar em branco")
    @Size(max = 15, message = "O status deve ter no máximo 15 caracteres")
    @Pattern(regexp = "^(in progress|completed|suspended)$", message = "O status deve ser 'in progress', 'completed' ou 'suspended'")
    private String status;

    public static Enrollment to(CreateEnrollmentDTO dto) {
        if (dto == null)
            return null;

        return Enrollment.builder()
                .user(User.builder().userId(dto.getUserId()).build())
                .course(Course.builder().courseId(dto.getCourseId()).build())
                .status(dto.getStatus())
                .build();
    }

}
