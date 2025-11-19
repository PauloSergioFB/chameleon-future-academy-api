package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

}
