package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

public interface UserService {

    /**
     * Обновление пароля. Права имеет только аутентифицированный пользователь.
     * @param newPasswordDto - dto содержащий новый пароль
     * @param authentication пользователь авторизованной сессии
     */
    void setPassword(NewPasswordDto newPasswordDto,
                     Authentication authentication);

    /**
     * Получение информации об авторизованном пользователе. Права имеет только аутентифицированный пользователь.
     * @param authentication пользователь авторизованной сессии
     * @return UserDto - dto инфо о юзере
     */
    UserDto getInfoUser(Authentication authentication);

    /**
     * Обновление информации об авторизованном пользователе. Права имеет только аутентифицированный пользователь.
     * @param updateUserDto - dto для обновления юзера
     * @param authentication пользователь авторизованной сессии
     * @return UpdateUserDto - dto для обновления юзера
     */
    UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto,
                                 Authentication authentication);

    /**
     * Обновление аватара авторизованного пользователя. Права имеет только аутентифицированный пользователь.
     * @param image полученный MultipartFile от фронта
     * @param authentication пользователь авторизованной сессии
     */
    void updateAvatarUser(MultipartFile image,
                          Authentication authentication);

}
