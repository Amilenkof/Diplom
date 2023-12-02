package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public void setPassword(NewPasswordDto newPasswordDto,
                            Authentication authentication) {
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
            return;
        }
        throw new IncorrectPasswordException("некорректный пароль");
    }

    @Override
    public UserDto getInfoUser(Authentication authentication) {
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        return userMapper.toDto(user);
    }

    @Override
    public UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Authentication authentication) {
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return updateUserDto;
    }


}



