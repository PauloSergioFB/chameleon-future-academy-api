package br.com.fiap.chameleonfutureacademy.service.Content;

import java.util.List;

public interface ContentService<T, ID> {

    public List<T> findByCourseId(Long courseId);

}
