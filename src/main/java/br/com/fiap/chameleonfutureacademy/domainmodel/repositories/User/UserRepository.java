package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Procedure(procedureName = "prc_insert_user")
    void prcSave(
            String name,
            String email,
            String hashedPassword,
            String biography,
            String whatsapp,
            String profileImage);

    public Optional<User> findByEmail(String email);

}
