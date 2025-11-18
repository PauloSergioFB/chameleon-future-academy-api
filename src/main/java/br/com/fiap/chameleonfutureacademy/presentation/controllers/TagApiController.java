package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.PageResponse;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
import br.com.fiap.chameleonfutureacademy.service.Tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Operações de listagem de tags")
public class TagApiController {

    private final TagService<Tag, Long> tagService;

    @Operation(summary = "Listar todas as tags", description = "Retorna uma lista paginada de tags.")
    @GetMapping
    public ResponseEntity<PageResponse<TagResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "tag_id", name = "order_by") String orderBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request) throws BadRequestException {

        return ResponseEntity.ok(PageResponse.from(tagService.findAll(page, size, orderBy, direction)));
    }

}
