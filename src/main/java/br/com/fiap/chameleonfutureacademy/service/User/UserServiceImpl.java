package br.com.fiap.chameleonfutureacademy.service.User;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.domainmodel.exceptions.FieldValidationException;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<User, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (existsById(user.getUserId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");

        userRepository.findByEmail(user.getEmail()).ifPresent(other -> {
            if (!other.getUserId().equals(user.getUserId())) {
                throw new FieldValidationException("email", "Este e-mail já está em uso.");
            }
        });

        return userRepository.save(user);
    }

    @Override
    public void removeById(Long id) {
        if (existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");

        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
