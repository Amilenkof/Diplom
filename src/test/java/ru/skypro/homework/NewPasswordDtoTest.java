package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.NewPasswordDto;

import static org.junit.jupiter.api.Assertions.*;

public class NewPasswordDtoTest {

    @Test
    public void testGettersAndSetters() {
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword("oldPassword");
        newPasswordDto.setNewPassword("newPassword");

        assertEquals("oldPassword", newPasswordDto.getCurrentPassword());
        assertEquals("newPassword", newPasswordDto.getNewPassword());
    }
}
