package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserAlreadyExistException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.AuthService;

import javax.validation.ValidationException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (encoder.matches(password, userDetails.getPassword())) {
            return true;
        }
        throw new BadCredentialsException("Неверный логин или пароль");
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        User user = userMapper.toEntity(registerDto);

        if (userRepository.existsUserByEmailIgnoreCase(user.getEmail())) {
            throw new UserAlreadyExistException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }

        user.setPassword(encoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        return true;
    }

}
