package com.javarush.levchuk.repository;

import com.javarush.levchuk.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.javarush.levchuk.config.Config.ADMIN_ID;
import static com.javarush.levchuk.config.Config.ADMIN_USERNAME;


public class UserRepository extends AbstractRepository<User> {

    public User getAdmin() {
        Optional<User> optionalUser = get(ADMIN_ID);
        if (optionalUser.isEmpty()) {
            User admin = User.builder()
                    .name(ADMIN_USERNAME)
                    .password(ADMIN_USERNAME)
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