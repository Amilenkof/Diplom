package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

public interface AdService {

    /**
     * Получает все объявления. Права имеют все пользователи.
     * @return AdsDto - dto для передачи полного списка объявлений
     */
    AdsDto getAllAds();

    /**
     * Добавляет объявление в базу. Права имеет только аутентифицированный пользователь.
     * @param image изображение товара
     * @param createOrUpdateAdDto dto для создания объявления
     * @param authentication пользователь авторизованной сессии
     * @return AdDto - dto объявления
     */
    AdDto addAd(MultipartFile image,
                CreateOrUpdateAdDto createOrUpdateAdDto,
                Authentication authentication);

    /**
     * Получение объявлений авторизованного пользователя. Права имеет только аутентифицированный пользователь.
     * @param authentication пользователь авторизованной сессии
     * @return AdsDto - dto для передачи полного списка объявлений
     */
    AdsDto getAdsMe(Authentication authentication);

    /**
     * Получение информации об объявлении. Права имеет только аутентифицированный пользователь.
     * @param id ID объявления
     * @return ExtendedAdDto - dto для получении
     */
    ExtendedAdDto getAd(long id);

    /**
     * Удаление объявления. Права имеют хозяин объявления и пользователь с ролью ADMIN.
     * @param id ID объявления
     * @param authentication пользователь авторизованной сессии
     */
    void deleteAd(long id,
                  Authentication authentication);

    /**
     * Обновление информации об объявлении. Права имеют хозяин объявления и пользователь с ролью ADMIN.
     * @param id ID объявления
     * @param createOrUpdateAdDto - dto для обновления объявления
     * @param authentication пользователь авторизованной сессии
     * @return AdDto - dto с обновленным объявлением
     */
    AdDto updateInfoAd(long id,
                       CreateOrUpdateAdDto createOrUpdateAdDto,
                       Authentication authentication);

    /**
     * Обновление изображения объявления. Права имеют хозяин объявления и пользователь с ролью ADMIN.
     * @param id ID объявления
     * @param image изображение объявления
     * @param authentication пользователь авторизованной сессии
     */
    void updateImageAd(long id, MultipartFile image,
                       Authentication authentication);

}
