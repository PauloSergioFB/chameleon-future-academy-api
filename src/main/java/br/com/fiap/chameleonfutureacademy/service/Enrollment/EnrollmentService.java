package br.com.fiap.chameleonfutureacademy.service.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService<T, ID> {

    public Optional<T> findById(Long id);

    public List<T> findByUserId(Long userId);

    public T create(T o);

    public T update(T o);

    public void removeById(ID id);

    public boolean existsById(ID id);

}
