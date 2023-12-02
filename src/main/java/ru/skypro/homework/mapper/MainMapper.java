package ru.skypro.homework.mapper;

public interface MainMapper<E, D> {

    E toEntity(D dto);
    D toDto(E entity);

}
