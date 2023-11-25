package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "createdAt")
    CommentDto toDto(Comment entity);

}
