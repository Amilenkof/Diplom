package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {

    private final AdService adService;

    @Operation(summary = "Получить все объявления", description = "getAllAds", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Объявления успешно получены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsDto.class)
                    )
            )
    })
    @GetMapping
    @Secured("")
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(summary = "Добавление объявления", description = "addAd", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Объявление добавлено",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("image") MultipartFile image,
                                       @RequestPart("properties") CreateOrUpdateAdDto createOrUpdateAdDto,
                                       Authentication authentication) {
        return ResponseEntity.ok(adService.addAd(createOrUpdateAdDto, authentication));
    }

    @Operation(summary = "Получение информации об объявлении", description = "getAd", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация об объявлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExtendedAdDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAd(@Parameter(description = "ID объявления") @PathVariable long id) {
        return ResponseEntity.ok(adService.getAd(id));
    }

    @Operation(summary = "Удаление объявления", description = "deleteAd", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалено",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@Parameter(description = "ID объявления") @PathVariable long id,
                                         Authentication authentication) {
        adService.deleteAd(id, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление информации об объявлении", description = "updateInfoAd", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Объявление обновлено",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateInfoAd(@Parameter(description = "ID объявления") @PathVariable long id,
                                              @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                              Authentication authentication) {
        return ResponseEntity.ok(adService.updateInfoAd(id, createOrUpdateAdDto, authentication));
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя", description = "getAdsMe", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получено",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsDto.class)
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
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication));
    }

    @Operation(summary = "Обновление картинки объявления", description = "updateImageAd", tags = {"Объявления"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Картинка успешно обновлена",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                    )
            )
    })
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImageAd(@Parameter(description = "ID объявления") @PathVariable long id,
                                              @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok().build();
    }

}
