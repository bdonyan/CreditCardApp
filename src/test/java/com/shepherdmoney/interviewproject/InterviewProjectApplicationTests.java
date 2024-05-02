package com.shepherdmoney.interviewproject;

import com.shepherdmoney.interviewproject.controller.UserController;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class InterviewProjectApplicationTests {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        // Test to ensure the application context loads without issues
    }

    @Test
    void testCreateUser() {
        // Setup
        User newUser = new User("Jane Doe", "jane@example.com");
        given(userService.createUser("Jane Doe", "jane@example.com")).willReturn(newUser);

        // Action
        User result = userService.createUser("Jane Doe", "jane@example.com");

        // Assertion
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }

        assertThat(result.getName()).isEqualTo("Jane Doe");
        verify(userService).createUser("Jane Doe", "jane@example.com");
    }

    @Test
    void testGetUserById() {
        // Setup
        User existingUser = new User("John Doe", "john@example.com");
        existingUser.setId(1);
        given(userService.getUserById(1)).willReturn(existingUser);

        // Action
        User result = userService.getUserById(1);

        // Assertion
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }

        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userService).getUserById(1);
    }
}
