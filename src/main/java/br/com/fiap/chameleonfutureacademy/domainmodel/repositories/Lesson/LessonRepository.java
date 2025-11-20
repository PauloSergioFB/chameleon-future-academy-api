package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Lesson;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    public Optional<Lesson> findByContentContentId(Long contentId);

}
