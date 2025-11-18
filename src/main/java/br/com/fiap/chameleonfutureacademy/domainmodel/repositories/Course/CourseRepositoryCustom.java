package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import org.springframework.data.domain.Page;

import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseListRow;

public interface CourseRepositoryCustom {

    Page<CourseListRow> findFiltered(
            String title, String author, String tag, int page, int size, String orderBy, String direction);

    Page<CourseListRow> findSearch(
            String search, String tag, int page, int size, String orderBy, String direction);

}
