package br.com.fiap.chameleonfutureacademy.service.Course;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course.CourseRepository;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseListRow;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.ShortCourseResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService<Course, Long> {

    private final CourseRepository courseRepository;

    private static final Set<String> VALID_ORDER_FIELDS = Arrays.stream(Course.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());

    @Override
    public Page<ShortCourseResponseDTO> findAllFiltered(
            String title,
            String author,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) throws BadRequestException {

        String orderByField = validateSortParameters(orderBy, direction);

        Page<CourseListRow> rowsPage = courseRepository.findFiltered(
                title, author, tag, page, size, orderByField, direction);

        List<ShortCourseResponseDTO> dtoList = convertRowsToDTO(rowsPage.getContent());

        return new PageImpl<>(dtoList, rowsPage.getPageable(), rowsPage.getTotalElements());
    }

    @Override
    public Page<ShortCourseResponseDTO> findAllSearch(
            String search,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) throws BadRequestException {

        String orderByField = validateSortParameters(orderBy, direction);

        Page<CourseListRow> rowsPage = courseRepository.findSearch(
                search, tag, page, size, orderByField, direction);

        List<ShortCourseResponseDTO> dtoList = convertRowsToDTO(rowsPage.getContent());

        return new PageImpl<>(dtoList, rowsPage.getPageable(), rowsPage.getTotalElements());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    private static String convertSnakeToCamel(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        boolean toUpper = false;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                toUpper = true;
            } else {
                if (toUpper) {
                    result.append(Character.toUpperCase(c));
                    toUpper = false;
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }

    private String validateSortParameters(String orderBy, String direction) throws BadRequestException {
        String field = convertSnakeToCamel(orderBy);

        if (!VALID_ORDER_FIELDS.contains(field)) {
            throw new BadRequestException("Campo de ordenação inválido.");
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new BadRequestException("Direção inválida. Use 'asc' ou 'desc'.");
        }

        return field;
    }

    private List<ShortCourseResponseDTO> convertRowsToDTO(List<CourseListRow> rows) {
        Map<Long, ShortCourseResponseDTO> dtoMap = new LinkedHashMap<>();

        for (CourseListRow row : rows) {
            ShortCourseResponseDTO dto = dtoMap.computeIfAbsent(
                    row.courseId(),
                    id -> ShortCourseResponseDTO.fromRow(row));

            if (row.tagDescription() != null) {
                dto.getTags().add(row.tagDescription());
            }
        }

        return new ArrayList<>(dtoMap.values());
    }

}
