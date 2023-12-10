package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.service.CommentService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(long id,
                                 CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                 Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID " + id + " не найдено"));
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDto.getText());
        comment.setAd(ad);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(SecurityUtils.getCurrentUser(authentication.getName()));
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentsDto getComments(long id) {
        List<Comment> commentList = commentRepository.findCommentsByAdId(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(commentList.size());
        commentsDto.setResults(commentList.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList()));
        return commentsDto;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or @commentRepository.findById(#idComment).get().getAuthor().getEmail() == #authentication.name")
    public void deleteComment(long idAd,
                              long idComment,
                              Authentication authentication) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(() ->
                new NotFoundException("Комментарий с ID" + idComment + "не найден"));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or @commentRepository.findById(#idComment).get().getAuthor().getEmail() == #authentication.name")
    public CommentDto updateComment(long idAd,
                                    long idComment,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                    Authentication authentication) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(() ->
                new NotFoundException("Комментарий с ID" + idComment + "не найден"));
        comment.setText(createOrUpdateCommentDto.getText());
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
