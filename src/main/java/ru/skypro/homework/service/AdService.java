package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

public interface AdService {

    AdsDto getAllAds();

    AdDto addAd(CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication);

    AdsDto getAdsMe(Authentication authentication);

    ExtendedAdDto getAd(long id);

    void deleteAd(long id, Authentication authentication);

    AdDto updateInfoAd(long id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication);

}
