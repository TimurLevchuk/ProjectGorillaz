package com.javarush.levchuk.service;

import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.exception.ProjectException;
import com.javarush.levchuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;


import java.util.Optional;

import static com.javarush.levchuk.config.Config.NON_EXISTENT_ID;
import static com.javarush.levchuk.config.Config.USER_NOT_FOUND_EXCEPTION;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long registerUser(String name, String password) {
        if (isNull(name) || name.isBlank() || isNull(password) || password.isBlank()) {
            return NON_EXISTENT_ID;
        }
        User user = User.builder()
                .name(name)
                .password(password)
                .build();
        userRepository.create(user);
        return user.getId();
    }

    public Long loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.get(username);
        if (optionalUser.isEmpty()) {
            return NON_EXISTENT_ID;
        }
        User user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            return user.getId();
        }
        return NON_EXISTENT_ID;
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.get(id);
        if (optionalUser.isEmpty()) {
            throw new ProjectException(USER_NOT_FOUND_EXCEPTION);
        }
        return optionalUser.get();
    }

    public void registerAdmin() {
        userRepository.getAdmin();
    }
}
