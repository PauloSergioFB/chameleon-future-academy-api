package br.com.fiap.chameleonfutureacademy.service.Tag;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;

public interface TagService<T, ID> {

    public Page<TagResponseDTO> findAll(
            int page, int size, String orderBy, String direction)
            throws BadRequestException;

}
