package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {

    CommentDto addComment(long id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);
    CommentsDto getComments(long id);
    void deleteComment(long idAd, long idComment, Authentication authentication);
    CommentDto updateComment(long idAd, long idComment, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

}
