package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Activity.ActivityResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Lesson.LessonResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Activity.ActivityService;
import br.com.fiap.chameleonfutureacademy.service.Lesson.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contents")
@Tag(name = "Conteúdos", description = "Endpoints para consulta de conteúdos específicos de um curso, incluindo lições e atividades.")
public class ContentApiController {

    private final LessonService<Lesson, Long> lessonService;
    private final ActivityService<Activity, Long> activityService;

    @Operation(summary = "Buscar aula do conteúdo", description = "Retorna a aula associada ao conteúdo informado pelo seu identificador.")
    @GetMapping("/{id}/lesson")
    public ResponseEntity<LessonResponseDTO> findLesson(@PathVariable Long contentId) {

        return lessonService.findByContentId(contentId)
                .map(lesson -> ResponseEntity.ok(LessonResponseDTO.from(lesson)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar atividade do conteúdo", description = "Retorna a atividade associada ao conteúdo informado pelo seu identificador.")
    @GetMapping("/{id}/activity")
    public ResponseEntity<ActivityResponseDTO> findActivity(@PathVariable Long contentId) {

        return activityService.findByContentId(contentId)
                .map(activity -> ResponseEntity.ok(ActivityResponseDTO.from(activity)))
                .orElse(ResponseEntity.notFound().build());
    }

}
