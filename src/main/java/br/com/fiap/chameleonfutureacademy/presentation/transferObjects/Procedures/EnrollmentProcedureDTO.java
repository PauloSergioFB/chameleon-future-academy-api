package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures;

import java.time.LocalDateTime;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public record EnrollmentProcedureDTO(
        Long userId,
        Long courseId,
        Integer progress,
        String status,
        LocalDateTime startedAt,
        LocalDateTime completedAt) {

    public static Enrollment toEntity(EnrollmentProcedureDTO dto) {
        if (dto == null)
            return null;

        return Enrollment.builder()
                .user(User.builder().userId(dto.userId()).build())
                .course(Course.builder().courseId(dto.courseId()).build())
                .progress(dto.progress())
                .status(dto.status())
                .startedAt(dto.startedAt())
                .completedAt(dto.completedAt())
                .build();
    }

}
