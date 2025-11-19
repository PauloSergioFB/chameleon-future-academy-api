package br.com.fiap.chameleonfutureacademy.service.Enrollment;

import java.util.List;

public interface EnrollmentService<T, ID> {

    public List<T> findByUserId(Long userId);

}
