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
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

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
    @Operation(summary = "Получение комментариев объявления", description = "getComments", tags = {"Комментарии"})
    public ResponseEntity<CommentsDto> getComments(@Parameter(description = "ID объявления") @PathVariable long id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

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
    @Operation(summary = "Добавление комментария к объявлению", description = "addComment", tags = {"Комментарии"})
    public ResponseEntity<CommentDto> addComment(@Parameter(description = "ID объявления") @PathVariable long id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                 Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateCommentDto, authentication));
    }

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
    @Operation(summary = "Удаление комментария", description = "deleteComment", tags = {"Комментарии"})
    public ResponseEntity<Void> deleteComment(@Parameter(description = "ID объявления") @PathVariable long adId,
                                              @Parameter(description = "ID комментария") @PathVariable long commentId,
                                              Authentication authentication) {
        commentService.deleteComment(adId, commentId, authentication);
        return ResponseEntity.ok().build();
    }

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
    @Operation(summary = "Обновление комментария", description = "updateComment", tags = {"Комментарии"})
    public ResponseEntity<CommentDto> updateComment(@Parameter(description = "ID объявления") @PathVariable long adId,
                                                    @Parameter(description = "ID комментария") @PathVariable long commentId,
                                                    @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                    Authentication authentication) {
        CommentDto commentDto = commentService.updateComment(adId, commentId, createOrUpdateCommentDto, authentication);
        return ResponseEntity.ok(commentDto);
    }

}
