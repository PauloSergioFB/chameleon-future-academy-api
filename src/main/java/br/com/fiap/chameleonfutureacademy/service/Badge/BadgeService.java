package br.com.fiap.chameleonfutureacademy.service.Badge;

import java.util.List;

public interface BadgeService<T, ID> {

    public List<T> findByCourseId(Long courseId);

}
