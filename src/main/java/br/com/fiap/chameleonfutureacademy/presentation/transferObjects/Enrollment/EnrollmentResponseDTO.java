package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment;

import java.time.LocalDateTime;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import lombok.Builder;

@Builder
public record EnrollmentResponseDTO(
        Long enrollmentId,
        Long userId,
        Long courseId,
        Integer progress,
        String status,
        LocalDateTime startedAt,
        LocalDateTime completedAt) {

    public static EnrollmentResponseDTO from(Enrollment enrollment) {
        if (enrollment == null)
            return null;

        return EnrollmentResponseDTO.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .userId(enrollment.getUser().getUserId())
                .courseId(enrollment.getCourse().getCourseId())
                .progress(enrollment.getProgress())
                .status(enrollment.getStatus())
                .startedAt(enrollment.getStartedAt())
                .completedAt(enrollment.getCompletedAt())
                .build();
    }

}
