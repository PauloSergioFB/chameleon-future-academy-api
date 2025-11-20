package br.com.fiap.chameleonfutureacademy.service.Lesson;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Lesson.LessonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService<Lesson, Long> {

    private final LessonRepository lessonRepository;

    @Override
    public Optional<Lesson> findByContentId(Long contentId) {
        return lessonRepository.findByContentContentId(contentId);
    }

}
