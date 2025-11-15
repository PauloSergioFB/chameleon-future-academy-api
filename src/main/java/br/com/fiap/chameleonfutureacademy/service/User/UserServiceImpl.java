package br.com.fiap.chameleonfutureacademy.service.User;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<User, Long> {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User partialUpdate(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        User userFromDatabase = userRepository.findById(id).orElse(null);

        if (user.getFullName() != null)
            userFromDatabase.setFullName(user.getFullName());

        if (user.getEmail() != null)
            userFromDatabase.setFullName(user.getEmail());

        if (user.getHashedPassword() != null)
            userFromDatabase.setFullName(user.getHashedPassword());

        if (user.getBiography() != null)
            userFromDatabase.setFullName(user.getBiography());

        if (user.getWhatsapp() != null)
            userFromDatabase.setFullName(user.getWhatsapp());

        if (user.getProfileImage() != null)
            userFromDatabase.setFullName(user.getProfileImage());

        return create(userFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
