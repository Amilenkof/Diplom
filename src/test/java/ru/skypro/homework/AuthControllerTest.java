package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.controller.AuthController;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Test
    public void testLogin_Successful() {
        AuthService authService = mock(AuthService.class);
        AuthController authController = new AuthController(authService);
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("testPassword");
        loginDto.setUsername("testUser");

        when(authService.login("testUser", "testPassword")).thenReturn(true);

        ResponseEntity<?> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authService, times(1)).login("testUser", "testPassword");
    }

    @Test
    public void testLogin_Unauthorized() {
        AuthService authService = mock(AuthService.class);
        AuthController authController = new AuthController(authService);
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("testPassword");
        loginDto.setUsername("testUser");

        when(authService.login("testUser", "testPassword")).thenReturn(false);

        ResponseEntity<?> response = authController.login(loginDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(authService, times(1)).login("testUser", "testPassword");
    }
}
