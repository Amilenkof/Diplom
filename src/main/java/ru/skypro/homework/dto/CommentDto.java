package ru.skypro.homework.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private long author;
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private long pk;
    private String text;

}
