package br.com.fiap.chameleonfutureacademy.service.Enrollment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.UserBadge;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course.CourseRepository;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Enrollment.EnrollmentRepository;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User.UserRepository;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.UserBadge.UserBadgeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService<Enrollment, Long> {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserBadgeRepository userBadgeRepository;

    @Override
    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public List<Enrollment> findByUserId(Long userId) {
        return enrollmentRepository.findByUserUserId(userId);
    }

    @Override
    public Enrollment create(Enrollment enrollment) {
        if (!userRepository.existsById(enrollment.getUser().getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        if (!courseRepository.existsById(enrollment.getCourse().getCourseId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado");
        }

        if (enrollmentRepository.existsByUserUserIdAndCourseCourseId(
                enrollment.getUser().getUserId(), enrollment.getCourse().getCourseId())) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já inscrito neste curso");
        }

        enrollment.setProgress(0);

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        Enrollment existing = enrollmentRepository.findById(enrollment.getEnrollmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        enrollment.setUser(existing.getUser());
        enrollment.setCourse(existing.getCourse());

        int totalContents = Optional.ofNullable(existing.getCourse().getContents())
                .map(List::size)
                .orElse(0);

        System.out.println(totalContents);

        if (totalContents > 0 && enrollment.getProgress() >= totalContents) {

            enrollment.setStatus("completed");
            enrollment.setCompletedAt(LocalDateTime.now());

            List<Badge> courseBadges = enrollment.getCourse().getBadges();

            if (courseBadges != null) {

                courseBadges.forEach(badge -> {
                    boolean hasBadge = enrollment.getUser().getBadges() != null &&
                            enrollment.getUser().getBadges().stream()
                                    .anyMatch(ub -> ub.getBadge().getBadgeId().equals(badge.getBadgeId()));

                    if (!hasBadge) {
                        userBadgeRepository.save(UserBadge.builder()
                                .user(enrollment.getUser())
                                .badge(badge)
                                .build());
                    }
                });
            }
        }

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void removeById(Long id) {
        if (!existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada");

        enrollmentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return enrollmentRepository.existsById(id);
    }

}
