package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    String address = "/users/image/";

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "authorImage", source = "author.image", qualifiedByName = "imageToString")
    CommentDto toDto(Comment entity);

    @Named("imageToString")
    default String imageToString(Image image) {
        if (image == null) {
            return null;
        }
        return address + image.getId();
    }

}
