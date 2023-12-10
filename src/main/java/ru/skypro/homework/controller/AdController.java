package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {

    private final AdService adService;
    private final ImageService imageService;

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
    @Operation(summary = "Получить все объявления", description = "getAllAds", tags = {"Объявления"})
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

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
    @Operation(summary = "Добавление объявления", description = "addAd", tags = {"Объявления"})
    public ResponseEntity<AdDto> addAd(@RequestPart("image") MultipartFile image,
                                       @RequestPart("properties") CreateOrUpdateAdDto createOrUpdateAdDto,
                                       Authentication authentication) {
        return ResponseEntity.ok(adService.addAd(image, createOrUpdateAdDto, authentication));
    }

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
    @Operation(summary = "Получение информации об объявлении", description = "getAd", tags = {"Объявления"})
    public ResponseEntity<ExtendedAdDto> getAd(@Parameter(description = "ID объявления") @PathVariable long id) {
        return ResponseEntity.ok(adService.getAd(id));
    }

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
    @Operation(summary = "Удаление объявления", description = "deleteAd", tags = {"Объявления"})
    public ResponseEntity<Void> deleteAd(@Parameter(description = "ID объявления") @PathVariable long id,
                                         Authentication authentication) {
        adService.deleteAd(id, authentication);
        return ResponseEntity.ok().build();
    }

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
    @Operation(summary = "Обновление информации об объявлении", description = "updateInfoAd", tags = {"Объявления"})
    public ResponseEntity<AdDto> updateInfoAd(@Parameter(description = "ID объявления") @PathVariable long id,
                                              @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                              Authentication authentication) {
        return ResponseEntity.ok(adService.updateInfoAd(id, createOrUpdateAdDto, authentication));
    }

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
    @Operation(summary = "Получение объявлений авторизованного пользователя", description = "getAdsMe", tags = {"Объявления"})
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication));
    }

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
    @Operation(summary = "Обновление картинки объявления", description = "updateImageAd", tags = {"Объявления"})
    public ResponseEntity<?> updateImageAd(@Parameter(description = "ID объявления") @PathVariable("id") long id,
                                           @RequestPart("image") MultipartFile image,
                                           Authentication authentication) {
        adService.updateImageAd(id, image, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE})
    @Operation(summary = "Получение картинки объявления", description = "getAdsImage", tags = {"Объявления"})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable long id) {
        return ResponseEntity.ok(imageService.getImage(id).getData());
    }

}
