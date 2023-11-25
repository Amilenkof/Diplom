package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    Ad toEntity(AdDto dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    AdDto toDto(Ad entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad toEntity(CreateOrUpdateAdDto dto);

}
