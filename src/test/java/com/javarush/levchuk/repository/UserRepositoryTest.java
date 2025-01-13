package com.javarush.levchuk.repository;

import com.javarush.levchuk.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.spy(new UserRepository());
    }

    @Test
    void whenAdminNotExists_thenCreateAdmin() {
        userRepository.getAdmin();
        Mockito.verify(userRepository).create(Mockito.any(User.class));
    }

    @Test
    void whenAdminExists_thenDontCreateAdmin() {
        User admin = User.builder().build();
        Mockito.doReturn(Optional.of(admin)).when(userRepository).get(Mockito.anyLong());
        userRepository.getAdmin();
        Mockito.verify(userRepository, Mockito.never()).create(Mockito.any(User.class));
    }

    @Test
    void whenGetUserByName_thenReturnsUserIgnoreCase() {
        User user = User.builder()
                .name("user")
                .build();
        userRepository.create(user);
        User userFromDb = userRepository.get("USER").orElseThrow();
        assertEquals(user, userFromDb);
    }
}