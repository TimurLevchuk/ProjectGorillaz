package com.javarush.levchuk.service;

import com.javarush.levchuk.entity.User;
import com.javarush.levchuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long register(String username, String password) {
        if (username == null || password == null){
            return 0L;
        }
        User user = User.builder()
                .name(username)
                .password(password)
                .build();
        userRepository.create(user);
        return user.getId();
    }
    public Long loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.get(username);
        if (optionalUser.isEmpty()) {
            return 0L;
        }
        User user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            return user.getId();
        }
        return 0L;
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.get(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return optionalUser.get();
    }

    public void registerAdmin() {
        userRepository.getAdmin();
    }
}
