package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {

    private final UserService userService;

    @Operation(summary = "Обновление пароля", description = "setPassword", tags = {"Пользователи"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пароль успешно обновлен"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden"
            )
    })
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto,
                                            Authentication authentication) {
        userService.setPassword(newPasswordDto, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение информации об авторизованном пользователе", description = "getInfoUser", tags = {"Пользователи"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация получена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> getInfoUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getInfoUser(authentication));
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе", description = "updateInfoUser", tags = {"Пользователи"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация обновлена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateUserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            )
    })
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateInfoUser(@RequestBody UpdateUserDto updateUserDto,
                                                        Authentication authentication) {
        return ResponseEntity.ok(userService.updateInfoUser(updateUserDto, authentication));
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя", description = "updateAvatarUser", tags = {"Пользователи"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Аватар обновлен",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            )
    })
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAvatarUser(@RequestBody MultipartFile image,
                                                 Authentication authentication) {
        return ResponseEntity.ok().build();
    }

}
