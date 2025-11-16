package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.CreateUserDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.UserResponseDTO;
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
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários: cadastro, consulta, atualização e remoção de usuários")
public class UserApiController {

    private final UserService<User, Long> userService;

    @Operation(summary = "Buscar usuário por ID", description = "Retorna as informações de um usuário específico com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserResponseDTO.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo registro de usuário no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody CreateUserDTO createUserDTO) {
        System.out.println(createUserDTO.getName());
        User newUser = userService.create(CreateUserDTO.toEntity(createUserDTO));
        return new ResponseEntity<>(UserResponseDTO.fromEntity(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar usuário existente", description = "Atualiza completamente os dados de um usuário já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateUserDTO createUserDTO) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        User user = CreateUserDTO.toEntity(createUserDTO);
        user.setUserId(id);

        User updatedUser = userService.update(user);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(updatedUser));
    }

    @Operation(summary = "Remover usuário", description = "Exclui permanentemente um usuário do sistema com base no ID informado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
