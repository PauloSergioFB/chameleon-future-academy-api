package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

}
