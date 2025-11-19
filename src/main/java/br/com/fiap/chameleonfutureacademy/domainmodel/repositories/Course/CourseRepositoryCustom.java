package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseTagRow;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.DetailedCourseRow;

public interface CourseRepositoryCustom {

    public Page<CourseTagRow> findFiltered(String title, String author, String tag, Pageable pageable);

    public Page<CourseTagRow> findSearch(String search, String tag, Pageable pageable);

    public List<DetailedCourseRow> findDetailedCourse(Long courseId);

}
