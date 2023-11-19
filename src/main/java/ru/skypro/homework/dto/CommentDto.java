package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {

    private long author;
    private String authorImage;
    private String authorFirstName;
    private int createAt;
    private long pk;
    private String text;

}