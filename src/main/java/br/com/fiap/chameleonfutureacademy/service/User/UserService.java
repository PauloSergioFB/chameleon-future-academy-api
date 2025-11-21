package br.com.fiap.chameleonfutureacademy.service.User;

import java.util.Optional;

import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.User.UserProfileResponseDTO;

public interface UserService<T, ID> {

    public Optional<T> findById(ID id);

    public Optional<UserProfileResponseDTO> findProfileById(ID id);

    public T create(T o);

    public void prc_create(T o);

    public T update(T o);

    public void removeById(ID id);

    public boolean existsById(ID id);

}
