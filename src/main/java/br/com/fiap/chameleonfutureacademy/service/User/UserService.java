package br.com.fiap.chameleonfutureacademy.service.User;

import java.util.Optional;

public interface UserService<T, ID> {

    public Optional<T> findById(ID id);

    public T create(T o);

    public T update(T o);

    public void removeById(ID id);

    public boolean existsById(ID id);

}
