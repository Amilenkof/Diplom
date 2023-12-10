package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    String address = "/users/image/";

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToString")
    UserDto toDto(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User toEntity(RegisterDto dto);

    @Named("imageToString")
    default String imageToString(Image image) {
        if (image == null) {
            return null;
        }
        return address + image.getId();
    }

}
