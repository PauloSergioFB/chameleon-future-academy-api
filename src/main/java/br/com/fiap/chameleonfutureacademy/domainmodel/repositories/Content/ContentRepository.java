package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {

    public List<Content> findByCourseCourseId(Long courseId);

}
