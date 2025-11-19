package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.PageResponse;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Badge.BadgeResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Content.ContentResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.CourseResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course.DetailedCourseResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Badge.BadgeService;
import br.com.fiap.chameleonfutureacademy.service.Content.ContentService;
import br.com.fiap.chameleonfutureacademy.service.Course.CourseService;
import br.com.fiap.chameleonfutureacademy.service.Tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Cursos", description = "Operações de listagem de cursos")
public class CourseApiController {

    private final CourseService<Course, Long> courseService;
    private final TagService<Tag, Long> tagService;
    private final BadgeService<Badge, Long> badgeService;
    private final ContentService<Content, Long> contentService;

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

    @Operation(summary = "Listar tags do curso", description = "Retorna todas as tags associadas ao curso informado pelo seu identificador.")
    @GetMapping("/{id}/tags")
    public ResponseEntity<List<TagResponseDTO>> findTagsByCourseId(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findByCourseId(id)
                .stream()
                .map(TagResponseDTO::from)
                .toList());
    }

    @Operation(summary = "Listar badges do curso", description = "Retorna todas as badges vinculadas ao curso informado, normalmente utilizadas como recompensas por progresso ou conclusão.")
    @GetMapping("/{id}/badges")
    public ResponseEntity<List<BadgeResponseDTO>> findBadgesByCourseId(@PathVariable Long id) {
        return ResponseEntity.ok(badgeService.findByCourseId(id)
                .stream()
                .map(BadgeResponseDTO::from)
                .toList());
    }

    @Operation(summary = "Listar conteúdos do curso", description = "Retorna a lista de conteúdos pertencentes ao curso informado, incluindo aulas, capítulos ou materiais complementares.")
    @GetMapping("/{id}/contents")
    public ResponseEntity<List<ContentResponseDTO>> findContentsByCourseId(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.findByCourseId(id)
                .stream()
                .map(ContentResponseDTO::from)
                .toList());
    }

    @Operation(summary = "Obter detalhes completos do curso", description = "Retorna uma visão detalhada do curso informado, incluindo dados gerais, conteúdos, tags, badges e outras informações relevantes para exibição do curso completo.")
    @GetMapping("/{id}/details")
    public ResponseEntity<DetailedCourseResponseDTO> findDetailedCourse(@PathVariable Long id) {
        return courseService.findDetailedCourseById(id)
                .map(course -> ResponseEntity.ok(course))
                .orElse(ResponseEntity.notFound().build());
    }

}
