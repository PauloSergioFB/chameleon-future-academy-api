package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public List<Tag> findByCoursesCourseId(Long courseId);

}
