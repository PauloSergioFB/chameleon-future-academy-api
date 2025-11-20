package br.com.fiap.chameleonfutureacademy.service.Activity;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Activity.ActivityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService<Activity, Long> {

    private final ActivityRepository activityRepository;

    @Override
    public Optional<Activity> findByContentId(Long contentId) {
        return activityRepository.findByContentContentId(contentId);
    }

}
