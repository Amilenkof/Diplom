package ru.skypro.homework.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentDto {

    private long author;
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private long pk;
    private String text;

}
