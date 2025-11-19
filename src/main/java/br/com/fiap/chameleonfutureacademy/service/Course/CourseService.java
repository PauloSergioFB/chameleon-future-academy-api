package br.com.fiap.chameleonfutureacademy.service.Course;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.CourseResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.DetailedCourseResponseDTO;

public interface CourseService<T, ID> {

    public Page<CourseResponseDTO> findAllFiltered(
            String title, String author, String tag, int page, int size, String orderBy, String direction)
            throws BadRequestException;

    public Page<CourseResponseDTO> findAllSearch(
            String search, String tag, int page, int size, String orderBy, String direction)
            throws BadRequestException;

    public Optional<DetailedCourseResponseDTO> findDetailedCourseById(ID id);

    public Optional<T> findById(ID id);

}
