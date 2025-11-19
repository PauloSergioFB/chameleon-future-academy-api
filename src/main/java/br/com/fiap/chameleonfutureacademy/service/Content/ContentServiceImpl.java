package br.com.fiap.chameleonfutureacademy.service.Content;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Content.ContentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService<Content, Long> {

    private final ContentRepository contentRepository;

    @Override
    public List<Content> findByCourseId(Long courseId) {
        return contentRepository.findByCourseCourseId(courseId);
    }

}
