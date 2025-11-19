package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User;

import java.util.List;

import br.com.fiap.chameleonfutureacademy.infrastructure.queries.User.UserProfileRow;

public interface UserRepositoryCustom {

    public List<UserProfileRow> findProfile(Long userId);

}
