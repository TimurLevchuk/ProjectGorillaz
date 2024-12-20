package com.javarush.levchuk.repository;

import com.javarush.levchuk.entity.User;

import java.util.Optional;

public class UserRepository extends AbstractRepository<User> implements Repository<User> {
    public User getAdmin() {
        Optional<User> optionalUser = get(1L);
        if (optionalUser.isEmpty()) {
            User admin = User.builder()
                    .name("admin")
                    .password("admin")
                    .build();
            create(admin);
            return admin;
        }
        return optionalUser.get();
    }

    public Optional<User> get(String username) {
        return map.values()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(username))
                .findFirst();
    }
}
