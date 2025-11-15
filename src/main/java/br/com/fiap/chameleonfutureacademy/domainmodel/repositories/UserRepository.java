package br.com.fiap.chameleonfutureacademy.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
