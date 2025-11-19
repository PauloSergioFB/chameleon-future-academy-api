package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Badge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    public List<Badge> findByCourseCourseId(Long courseId);

}
