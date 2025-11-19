package br.com.fiap.chameleonfutureacademy.service.Course;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course.CourseRepository;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseTagRow;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.DetailedCourseRow;
import br.com.fiap.chameleonfutureacademy.infrastructure.utils.CaseConverter;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.CourseResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.DetailedCourseResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService<Course, Long> {

    private final CourseRepository courseRepository;

    private static final Set<String> VALID_ORDER_FIELDS = Arrays.stream(Course.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());

    @Override
    public Page<CourseResponseDTO> findAllFiltered(
            String title,
            String author,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) throws BadRequestException {

        String orderByField = CaseConverter.snakeToCamel(orderBy);

        if (!VALID_ORDER_FIELDS.contains(orderByField)) {
            throw new BadRequestException("Campo de ordenação inválido.");
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new BadRequestException("Direção inválida. Use 'asc' ou 'desc'.");
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(orderByField).ascending()
                : Sort.by(orderByField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CourseTagRow> rowsPage = courseRepository.findFiltered(title, author, tag, pageable);
        List<CourseResponseDTO> dtoList = CourseResponseDTO.from(rowsPage.getContent());

        return new PageImpl<>(dtoList, rowsPage.getPageable(), rowsPage.getTotalElements());
    }

    @Override
    public Page<CourseResponseDTO> findAllSearch(
            String search,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) throws BadRequestException {

        String orderByField = CaseConverter.snakeToCamel(orderBy);

        if (!VALID_ORDER_FIELDS.contains(orderByField)) {
            throw new BadRequestException("Campo de ordenação inválido.");
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new BadRequestException("Direção inválida. Use 'asc' ou 'desc'.");
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(orderByField).ascending()
                : Sort.by(orderByField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CourseTagRow> rowsPage = courseRepository.findSearch(search, tag, pageable);
        List<CourseResponseDTO> dtoList = CourseResponseDTO.from(rowsPage.getContent());

        return new PageImpl<>(dtoList, rowsPage.getPageable(), rowsPage.getTotalElements());
    }

    @Override
    public Optional<DetailedCourseResponseDTO> findDetailedCourseById(Long id) {
        List<DetailedCourseRow> rows = courseRepository.findDetailedCourse(id);
        return Optional.ofNullable(DetailedCourseResponseDTO.from(rows));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

}
