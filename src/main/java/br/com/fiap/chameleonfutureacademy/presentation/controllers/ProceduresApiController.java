package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.ActivityOptionProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.ActivityProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.AddTagForCourseProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.BadgeProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.ContentProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.CourseProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.EnrollmentProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.LessonProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.TagProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.UserBadgeProcedureDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.UserProcedureDTO;
import br.com.fiap.chameleonfutureacademy.service.Procedure.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/procedures")
@Tag(name = "Procedures", description = "Endpoints responsáveis por operações diretas via stored procedures no banco Oracle.")
public class ProceduresApiController {

    private final ProcedureService procedureService;

    @Operation(summary = "Cria um usuário via procedure", description = "Executa a procedure prc_insert_user para inserir um novo usuário no banco.")
    @PostMapping("/users")
    public ResponseEntity<Void> save(@Valid @RequestBody UserProcedureDTO user) {
        procedureService.prcSaveUser(UserProcedureDTO.toEntity(user));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria um curso via procedure", description = "Executa a procedure prc_insert_course para inserir um novo curso no banco.")
    @PostMapping("/courses")
    public ResponseEntity<Void> saveCourse(@Valid @RequestBody CourseProcedureDTO course) {
        procedureService.prcSaveCourse(CourseProcedureDTO.toEntity(course));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria uma tag via procedure", description = "Executa a procedure prc_insert_tag para inserir uma tag no banco.")
    @PostMapping("/tags")
    public ResponseEntity<Void> saveTag(@Valid @RequestBody TagProcedureDTO tag) {
        procedureService.prcSaveTag(TagProcedureDTO.toEntity(tag));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria um conteúdo via procedure", description = "Executa a procedure prc_insert_content para inserir um conteúdo no banco.")
    @PostMapping("/contents")
    public ResponseEntity<Void> saveContent(@Valid @RequestBody ContentProcedureDTO content) {
        procedureService.prcSaveContent(ContentProcedureDTO.toEntity(content));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria uma lição via procedure", description = "Executa a procedure prc_insert_lesson para inserir uma lição no banco.")
    @PostMapping("/lessons")
    public ResponseEntity<Void> saveLesson(@Valid @RequestBody LessonProcedureDTO lesson) {
        procedureService.prcSaveLesson(LessonProcedureDTO.toEntity(lesson));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria uma atividade via procedure", description = "Executa a procedure prc_insert_activity para inserir uma atividade no banco.")
    @PostMapping("/activities")
    public ResponseEntity<Void> saveActivity(@Valid @RequestBody ActivityProcedureDTO activity) {
        procedureService.prcSaveActivity(ActivityProcedureDTO.toEntity(activity));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria um badge via procedure", description = "Executa a procedure prc_insert_badge para inserir um badge no banco.")
    @PostMapping("/badges")
    public ResponseEntity<Void> saveBadge(@Valid @RequestBody BadgeProcedureDTO badge) {
        procedureService.prcSaveBadge(BadgeProcedureDTO.toEntity(badge));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Registra usuário em curso via procedure", description = "Executa a procedure prc_register_user_in_course para criar uma matrícula.")
    @PostMapping("/enrollments")
    public ResponseEntity<Void> saveEnrollment(@Valid @RequestBody EnrollmentProcedureDTO enrollment) {
        procedureService.prcRegisterUserInCourse(EnrollmentProcedureDTO.toEntity(enrollment));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Adiciona tag ao curso via procedure", description = "Executa a procedure prc_add_tag_for_course para vincular uma tag ao curso.")
    @PostMapping("/courses/tags")
    public ResponseEntity<Void> addTagForCourse(@Valid @RequestBody AddTagForCourseProcedureDTO dto) {
        procedureService.prcAddTagForCourse(dto.courseId(), dto.tagId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Adiciona opção à atividade via procedure", description = "Executa a procedure prc_add_option_for_activity para inserir uma opção de resposta.")
    @PostMapping("/activities/options")
    public ResponseEntity<Void> addOptionForActivity(@Valid @RequestBody ActivityOptionProcedureDTO activityOption) {
        procedureService.prcAddOptionForActivity(ActivityOptionProcedureDTO.toEntity(activityOption));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Concede badge ao usuário via procedure", description = "Executa a procedure prc_add_badge_for_user para conceder um badge.")
    @PostMapping("/users/badges")
    public ResponseEntity<Void> addBadgeForUser(@Valid @RequestBody UserBadgeProcedureDTO userBadge) {
        procedureService.prcAddBadgeForUser(UserBadgeProcedureDTO.toEntity(userBadge));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
