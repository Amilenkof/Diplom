package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAdByAuthorId(long id);
    List<Ad> findAll();
    Ad findAdById(long id);
    void deleteAdById(long id);

}
