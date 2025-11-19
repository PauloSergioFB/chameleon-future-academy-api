package br.com.fiap.chameleonfutureacademy.service.Tag;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Tag.TagRepository;
import br.com.fiap.chameleonfutureacademy.infrastructure.utils.CaseConverter;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService<Tag, Long> {

    private final TagRepository tagRepository;

    private static final Set<String> VALID_ORDER_FIELDS = Arrays
            .stream(Tag.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());

    @Override
    public Page<TagResponseDTO> findAll(
            int page, int size, String orderBy, String direction)
            throws BadRequestException {

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

        return tagRepository.findAll(pageable).map(TagResponseDTO::from);
    }

    @Override
    public List<Tag> findByCourseId(Long courseId) {
        return tagRepository.findByCoursesCourseId(courseId);
    }

}
