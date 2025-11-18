package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseListRow;

public interface CourseRepositoryCustom {

    Page<CourseListRow> findFiltered(String title, String author, String tag, Pageable pageable);

    Page<CourseListRow> findSearch(String search, String tag, Pageable pageable);

}
