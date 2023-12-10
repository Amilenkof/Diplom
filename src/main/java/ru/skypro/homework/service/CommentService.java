package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {

    CommentsDto getComments(long id);
    CommentDto updateComment(long idAd, long idComment, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);
    CommentDto addComment(long id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);
    void deleteComment(long idAd, long idComment, Authentication authentication);

}
