package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Activity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    public Optional<Activity> findByContentContentId(Long courseId);

}
