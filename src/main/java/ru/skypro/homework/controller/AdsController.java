package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@RestController
@RequestMapping("ads")
public class AdsController {

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
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(new AdsDto());
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
    public ResponseEntity<AdDto> addAd(@RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                       @RequestBody MultipartFile image) {
        return ResponseEntity.ok(new AdDto());
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
        return ResponseEntity.ok(new ExtendedAdDto());
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
    public ResponseEntity<Void> deleteAd(@Parameter(description = "ID объявления") @PathVariable long id) {
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
                                              @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return ResponseEntity.ok(new AdDto());
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
    public ResponseEntity<AdsDto> getAdsMe() {
        return ResponseEntity.ok(new AdsDto());
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

    @Operation(summary = "Получение комментариев объявления", description = "getComments", tags = {"Комментарии"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарии получены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentsDto.class)
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
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@Parameter(description = "ID объявления") @PathVariable long id) {
        return ResponseEntity.ok(new CommentsDto());
    }

    @Operation(summary = "Добавление комментария к объявлению", description = "addComment", tags = {"Комментарии"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарий добавлен",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class)
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
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@Parameter(description = "ID объявления") @PathVariable long id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok(new CommentDto());
    }

    @Operation(summary = "Удаление комментария", description = "deleteComment", tags = {"Комментарии"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарии удален",
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
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@Parameter(description = "ID объявления") @PathVariable long adId,
                                              @Parameter(description = "ID комментария") @PathVariable long commentId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария", description = "updateComment", tags = {"Комментарии"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарии обновлен",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class)
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
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@Parameter(description = "ID объявления") @PathVariable long adId,
                                                    @Parameter(description = "ID комментария") @PathVariable long commentId,
                                                    @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok(new CommentDto());
    }


}