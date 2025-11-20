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
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários, incluindo criação, consulta, atualização e exclusão.")
public class UserApiController {

    private final UserService<User, Long> userService;
    private final EnrollmentService<Enrollment, Long> enrollmentService;

    @Operation(summary = "Obter dados do usuário autenticado", description = "Retorna as informações completas do usuário atualmente autenticado.")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> find(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findById(authUser.userId())
                .map(user -> ResponseEntity.ok(UserResponseDTO.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar cursos inscritos", description = "Retorna todos os cursos nos quais o usuário autenticado está inscrito, incluindo informações como progresso, status e datas de início e conclusão.")
    @GetMapping("/me/enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> findEnrollments(
            @AuthenticationPrincipal JwtUserData authUser) {

        return ResponseEntity.ok(enrollmentService.findByUserId(authUser.userId())
                .stream()
                .map(EnrollmentResponseDTO::from)
                .toList());
    }

    @Operation(summary = "Listar badges do usuário", description = "Retorna todas as badges conquistadas pelo usuário autenticado, incluindo título, ícone e informações de associação com cursos.")
    @GetMapping("/me/badges")
    public ResponseEntity<List<BadgeResponseDTO>> findBadges(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findById(authUser.userId())
                .map(user -> ResponseEntity.ok(user.getBadges().stream().map(BadgeResponseDTO::from).toList()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obter perfil completo do usuário", description = "Retorna o perfil completo do usuário autenticado, incluindo dados pessoais, cursos inscritos, badges conquistadas e demais informações associadas ao seu progresso na plataforma.")
    @GetMapping("/me/profile")
    public ResponseEntity<UserProfileResponseDTO> findProfile(@AuthenticationPrincipal JwtUserData authUser) {
        return userService.findProfileById(authUser.userId())
                .map(profile -> ResponseEntity.ok(profile))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário no sistema a partir dos dados fornecidos.")
    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User newUser = userService.create(CreateUserDTO.to(createUserDTO));
        return new ResponseEntity<>(UserResponseDTO.from(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar usuário existente", description = "Atualiza integralmente as informações de um usuário já registrado, identificado pelo seu ID.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserDTO createUserDTO,
            @AuthenticationPrincipal JwtUserData authUser) {

        if (!id.equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode alterar o seu próprio usuário.");

        User user = CreateUserDTO.to(createUserDTO);
        user.setUserId(id);

        User updatedUser = userService.update(user);
        return ResponseEntity.ok(UserResponseDTO.from(updatedUser));
    }

    @Operation(summary = "Excluir usuário", description = "Remove definitivamente um usuário do sistema com base no seu ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal JwtUserData authUser) {
        if (!id.equals(authUser.userId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você só pode remover o seu próprio usuário.");

        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
