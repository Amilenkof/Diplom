package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.model.Register;
import ru.skypro.homework.model.Role;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {

    @Test
    public void testGettersAndSetters() {
        Register register = new Register();
        register.setUsername("testUser");
        register.setPassword("testPassword");
        register.setFirstName("Ivan");
        register.setLastName("Ivanov");
        register.setPhone("1234567890");
        register.setRole(Role.USER);

        assertEquals("testUser", register.getUsername());
        assertEquals("testPassword", register.getPassword());
        assertEquals("Ivan", register.getFirstName());
        assertEquals("Ivanov", register.getLastName());
        assertEquals("1234567890", register.getPhone());
        assertEquals(Role.USER, register.getRole());
    }
}