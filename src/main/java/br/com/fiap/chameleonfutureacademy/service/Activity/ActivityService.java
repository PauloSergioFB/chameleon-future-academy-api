package br.com.fiap.chameleonfutureacademy.service.Activity;

import java.util.Optional;

public interface ActivityService<T, ID> {

    public Optional<T> findByContentId(Long contentId);

}
