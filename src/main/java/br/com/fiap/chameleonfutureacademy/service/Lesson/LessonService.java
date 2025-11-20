package br.com.fiap.chameleonfutureacademy.service.Lesson;

import java.util.Optional;

public interface LessonService<T, ID> {

    public Optional<T> findByContentId(Long contentId);

}
