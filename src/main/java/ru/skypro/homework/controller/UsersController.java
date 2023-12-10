package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {

    private final UserService userService;
    private final ImageService imageService;

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
    @Operation(summary = "Обновление пароля", description = "setPassword", tags = {"Пользователи"})
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto,
                                            Authentication authentication) {
        userService.setPassword(newPasswordDto, authentication);
        return ResponseEntity.ok().build();
    }

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
    @Operation(summary = "Получение информации об авторизованном пользователе", description = "getInfoUser", tags = {"Пользователи"})
    public ResponseEntity<UserDto> getInfoUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getInfoUser(authentication));
    }

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
    @Operation(summary = "Обновление информации об авторизованном пользователе", description = "updateInfoUser", tags = {"Пользователи"})
    public ResponseEntity<UpdateUserDto> updateInfoUser(@RequestBody UpdateUserDto updateUserDto,
                                                        Authentication authentication) {
        return ResponseEntity.ok(userService.updateInfoUser(updateUserDto, authentication));
    }

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
    @Operation(summary = "Обновление аватара авторизованного пользователя", description = "updateAvatarUser", tags = {"Пользователи"})
    public ResponseEntity<Void> updateAvatarUser(@RequestPart MultipartFile image,
                                                 Authentication authentication) {
        userService.updateAvatarUser(image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение аватара пользователя", description = "getImage", tags = {"Пользователи"})
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable long id) {
        return ResponseEntity.ok(imageService.getImage(id).getData());
    }

}
