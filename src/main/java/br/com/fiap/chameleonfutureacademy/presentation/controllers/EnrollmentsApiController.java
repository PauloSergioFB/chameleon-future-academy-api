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
@Tag(name = "Matrículas", description = "Endpoints para criação, atualização e remoção de matrículas de usuários.")
public class EnrollmentsApiController {

    private final EnrollmentService<Enrollment, Long> enrollmentService;

    @Operation(summary = "Criar uma nova matrícula", description = "Registra uma nova matrícula para o usuário autenticado com base nos dados informados.")
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

    @Operation(summary = "Atualizar uma matrícula existente", description = "Atualiza os dados de uma matrícula específica pertencente ao usuário autenticado.")
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

    @Operation(summary = "Excluir uma matrícula", description = "Remove uma matrícula específica pertencente ao usuário autenticado.")
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
