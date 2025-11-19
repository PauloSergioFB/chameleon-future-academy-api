package br.com.fiap.chameleonfutureacademy.service.Enrollment;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Enrollment.EnrollmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService<Enrollment, Long> {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> findByUserId(Long userId) {
        return enrollmentRepository.findByUserUserId(userId);
    }

}
