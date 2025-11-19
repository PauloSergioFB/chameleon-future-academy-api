package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseTagRow;

public interface CourseRepositoryCustom {

    Page<CourseTagRow> findFiltered(String title, String author, String tag, Pageable pageable);

    Page<CourseTagRow> findSearch(String search, String tag, Pageable pageable);

}
