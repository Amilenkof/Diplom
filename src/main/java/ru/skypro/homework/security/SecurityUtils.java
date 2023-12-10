package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.entity.User;

/**
 * Утилитарный класс для работы с Security
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityUtils {

    /**
     * Метод для получения сущности User аутентифицированного пользователя
     * @param username - логин, передается из объекта Authentication
     * @return объект User
     * @throws UsernameNotFoundException - Неавторизованный пользователь
     */
    public static User getCurrentUser(String username) throws UsernameNotFoundException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(username)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUser();
        }
        log.debug("Пользователь " + username + "пытается работать как " + authentication.getName());
        throw new UsernameNotFoundException("Неавторизованный пользователь");
    }

}
