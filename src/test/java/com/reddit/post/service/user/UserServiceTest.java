package com.reddit.post.service.user;

import com.reddit.post.exception.BadRequestException;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;

    @BeforeEach
    public void beforeEachSetup() {
        user = new User(1L, "admir", "email@email.com","imageUrl", new HashSet<>());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
    }

    @Test
    void saveUserWhenUserDoesNotExists() {
        var value = userService.saveUser(user);

        Assertions.assertEquals(value, user);
    }

    @Test
    void throwBadRequestIfUserIsNull() {
        Assertions.assertThrows(BadRequestException.class, () -> userService.saveUser(null));
    }

    @Test
    void returnUserIfExists() {
        var value = userService.saveUser(user);

        Assertions.assertEquals(value, user);
    }

}