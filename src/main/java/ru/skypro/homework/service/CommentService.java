package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {

    /**
     * Добавление комментария к объявлению. Права имеет только аутентифицированный пользователь.
     * @param id - ID объявления
     * @param createOrUpdateCommentDto - dto для создания комментария
     * @param authentication пользователь авторизованной сессии
     * @return CommentDto - dto комментария
     */
    CommentDto addComment(long id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    /**
     * Получение комментариев объявления. Права имеет только аутентифицированный пользователь.
     * @param id - ID объявления
     * @return CommentsDto - dto для передачи списка комментариев объявления.
     */
    CommentsDto getComments(long id);

    /**
     * Удаление комментария. Права имеют хозяин объявления и пользователь с ролью ADMIN.
     * @param idAd - ID объявления
     * @param idComment - ID комментария
     * @param authentication пользователь авторизованной сессии
     */
    void deleteComment(long idAd, long idComment, Authentication authentication);

    /**
     * Обновление комментария. Права имеют хозяин объявления и пользователь с ролью ADMIN.
     * @param idAd - ID объявления
     * @param idComment - ID комментария
     * @param createOrUpdateCommentDto  - dto для редактирования комментария
     * @param authentication пользователь авторизованной сессии
     * @return CommentDto - dto комментария
     */
    CommentDto updateComment(long idAd, long idComment, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

}
