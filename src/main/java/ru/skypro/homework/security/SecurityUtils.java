package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

@Slf4j
@RequiredArgsConstructor
public class SecurityUtils {

    public static User getCurrentUser(String username) throws UsernameNotFoundException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(username)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUser();
        }
        throw new UsernameNotFoundException("Неавторизованный пользователь");
    }

    public static void checkPermit(String username, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl)  authentication.getPrincipal();
        if (!userDetails.getUser().getRole().equals(Role.ADMIN) && !userDetails.getUser().getEmail().equals(username)){
            throw new AccessDeniedException("Нет прав на удаление");
        }
    }

}
