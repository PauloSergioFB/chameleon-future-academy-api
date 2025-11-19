package br.com.fiap.chameleonfutureacademy.service.Badge;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Badge.BadgeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService<Badge, Long> {

    private final BadgeRepository badgeRepository;

    @Override
    public List<Badge> findByCourseId(Long courseId) {
        return badgeRepository.findByCourseCourseId(courseId);
    }

}
