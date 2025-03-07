package com.example.employee_management_system;

import com.example.employee_management_system.model.User;
import com.example.employee_management_system.repo.UserRepo;
import com.example.employee_management_system.service.JwtService;
import com.example.employee_management_system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
    }

    @Test
    void testAdUser() {
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertNotEquals("password123", savedUser.getPassword()); // Ensure password is encoded
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testVerify_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(user.getUsername())).thenReturn("mockedToken");

        String result = userService.verify(user);

        assertEquals("mockedToken", result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(user.getUsername());
    }

    @Test
    void testVerify_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        try {
            String result = userService.verify(user);
            System.out.println("Test Result: " + result);
            assertEquals("Fail", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed due to unexpected exception: " + e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }
}
