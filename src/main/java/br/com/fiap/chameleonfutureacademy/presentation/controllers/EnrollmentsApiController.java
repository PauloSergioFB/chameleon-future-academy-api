package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.infrastructure.config.JwtUserData;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment.CreateEnrollmentDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment.EnrollmentResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Enrollment.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enrollments")
@Tag(name = "Matrículas", description = "Endpoints para gerenciamento de matrículas dos usuários em cursos, incluindo criação, atualização e remoção.")
public class EnrollmentsApiController {

    private final EnrollmentService<Enrollment, Long> enrollmentService;

    @Operation(summary = "Registrar nova matrícula", description = "Cria uma nova matrícula associando um usuário a um curso. Este endpoint deve ser utilizado quando um aluno inicia sua participação em um curso.")
    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> save(
            @Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        if (!createEnrollmentDTO.getUserId().equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode adicionar matriculas ao seu próprio usuário.");

        Enrollment newEnrollment = enrollmentService.create(CreateEnrollmentDTO.to(createEnrollmentDTO));
        return new ResponseEntity<>(EnrollmentResponseDTO.from(newEnrollment), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar matrícula existente", description = "Atualiza completamente os dados de uma matrícula já registrada, permitindo alterar progresso, status ou curso associado.")
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        if (!createEnrollmentDTO.getUserId().equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode alterar suas próprias matriculas.");

        Enrollment enrollment = CreateEnrollmentDTO.to(createEnrollmentDTO);
        enrollment.setEnrollmentId(id);

        Enrollment updatedEnrollment = enrollmentService.update(enrollment);
        return ResponseEntity.ok(EnrollmentResponseDTO.from(updatedEnrollment));
    }

    @Operation(summary = "Excluir matrícula", description = "Remove definitivamente uma matrícula do sistema, encerrando a participação do usuário em um curso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal JwtUserData authUser) {

        Enrollment enrollment = enrollmentService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Matrícula não encontrada."));

        if (!enrollment.getUser().getUserId().equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode alterar suas próprias matriculas.");

        enrollmentService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
