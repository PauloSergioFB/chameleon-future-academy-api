package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Enrollment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUserUserId(Long userId);

}
