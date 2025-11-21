package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.infrastructure.config.JwtUserData;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Badge.BadgeResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Enrollment.EnrollmentResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.CreateUserDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.UserProfileResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.UserResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Enrollment.EnrollmentService;
import br.com.fiap.chameleonfutureacademy.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Usuários", description = "Endpoints dedicados ao gerenciamento de contas de usuário: acesso ao perfil, matrículas, badges e operações de criação, atualização e remoção.")
public class UserApiController {

    private final UserService<User, Long> userService;
    private final EnrollmentService<Enrollment, Long> enrollmentService;

    @Operation(summary = "Obter dados do usuário autenticado", description = "Retorna as informações completas do usuário atualmente autenticado com base no token JWT fornecido.")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> find(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findById(authUser.userId())
                .map(user -> ResponseEntity.ok(UserResponseDTO.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar inscrições do usuário autenticado", description = "Retorna todos os cursos nos quais o usuário autenticado está matriculado.")
    @GetMapping("/me/enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollments(
            @AuthenticationPrincipal JwtUserData authUser) {

        return ResponseEntity.ok(enrollmentService.findByUserId(authUser.userId())
                .stream()
                .map(EnrollmentResponseDTO::from)
                .toList());
    }

    @Operation(summary = "Listar badges do usuário autenticado", description = "Retorna todas as badges conquistadas pelo usuário autenticado ao longo dos cursos realizados.")
    @GetMapping("/me/badges")
    public ResponseEntity<List<BadgeResponseDTO>> findBadges(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findById(authUser.userId())
                .map(user -> ResponseEntity.ok(user.getBadges().stream().map(BadgeResponseDTO::from).toList()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obter perfil detalhado do usuário", description = "Retorna um resumo completo do perfil do usuário autenticado, incluindo dados complementares que não fazem parte do retorno padrão.")
    @GetMapping("/me/profile")
    public ResponseEntity<UserProfileResponseDTO> findProfile(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findProfileById(authUser.userId())
                .map(profile -> ResponseEntity.ok(profile))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário no sistema com os dados informados e retorna as informações do usuário registrado.")
    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User newUser = userService.create(CreateUserDTO.toEntity(createUserDTO));
        return new ResponseEntity<>(UserResponseDTO.from(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar usuário existente", description = "Atualiza todas as informações do usuário identificado pelo ID informado. Apenas o próprio usuário pode atualizar o seu próprio registro.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserDTO createUserDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        if (!id.equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode alterar o seu próprio usuário.");

        User user = CreateUserDTO.toEntity(createUserDTO);
        user.setUserId(id);

        User updatedUser = userService.update(user);
        return ResponseEntity.ok(UserResponseDTO.from(updatedUser));
    }

    @Operation(summary = "Remover usuário existente", description = "Remove permanentemente o usuário identificado pelo ID informado. Apenas o próprio usuário pode excluir sua própria conta.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal JwtUserData authUser) {
        if (!id.equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode remover o seu próprio usuário.");

        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
