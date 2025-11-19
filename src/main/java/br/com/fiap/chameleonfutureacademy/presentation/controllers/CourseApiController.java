package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.PageResponse;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.CourseResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses")
@Tag(name = "Cursos", description = "Operações de listagem de cursos")
public class CourseApiController {

    private final CourseService<Course, Long> courseService;

    @Operation(summary = "Listar todos os cursos", description = "Retorna uma lista paginada de cursos, com filtros opcionais por título, autor e tag.")
    @GetMapping
    public ResponseEntity<PageResponse<CourseResponseDTO>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, name = "tag_description") String tagDescription,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "course_id", name = "order_by") String orderBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request) throws BadRequestException {

        return ResponseEntity.ok(PageResponse
                .from(courseService.findAllFiltered(
                        title, author, tagDescription, page, size, orderBy, direction)));
    }

    @Operation(summary = "Listar todos os cursos com busca", description = "Retorna cursos paginados com busca parcial por título, autor ou tag e filtro opcional por tag exata.")
    @GetMapping("/search")
    public ResponseEntity<PageResponse<CourseResponseDTO>> findAllSearch(
            @RequestParam(required = false, name = "query") String search,
            @RequestParam(required = false, name = "tag_description") String tagDescription,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "course_id", name = "order_by") String orderBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request) throws BadRequestException {

        return ResponseEntity.ok(PageResponse
                .from(courseService.findAllSearch(
                        search, tagDescription, page, size, orderBy, direction)));
    }

    @Operation(summary = "Buscar curso por ID", description = "Retorna as informações de um curso específico com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> findById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(course -> ResponseEntity.ok(CourseResponseDTO.from(course)))
                .orElse(ResponseEntity.notFound().build());
    }

}
