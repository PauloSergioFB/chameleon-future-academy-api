package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Procedures.UserProcedureDTO;
import br.com.fiap.chameleonfutureacademy.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/procedures")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Procedures", description = "")
public class ProceduresApiController {

    private final UserService<User, Long> userService;

    @Operation(summary = "", description = "")
    @PostMapping("/users")
    public ResponseEntity<Void> save(@Valid @RequestBody UserProcedureDTO user) {
        userService.prc_create(UserProcedureDTO.to(user));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
