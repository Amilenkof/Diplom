package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.service.AdService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final AdMapper adMapper;

    @Override
    public AdsDto getAllAds() {
        List<Ad> adList = adRepository.findAll();
        return mapAdsDto(adList);
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication) {
        Ad ad = adMapper.toEntity(createOrUpdateAdDto);
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        ad.setAuthor(user);
        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    public AdsDto getAdsMe(Authentication authentication) {
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        List<Ad> adList = adRepository.findAdByAuthorId(user.getId());
        return mapAdsDto(adList);
    }

    @Override
    public ExtendedAdDto getAd(long id) {
        return adMapper.toExtendedAdDto(adRepository.findAdById(id));
    }

    @Override
    @Transactional
    public void deleteAd(long id, Authentication authentication) {
        Ad ad = adRepository.findAdById(id);
        SecurityUtils.checkPermit(ad.getAuthor().getEmail(), authentication);
        commentRepository.deleteCommentsByAd_Id(id);
        adRepository.deleteAdById(id);
    }

    @Override
    public AdDto updateInfoAd(long id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication) {
        Ad ad = adRepository.findAdById(id);
        SecurityUtils.checkPermit(ad.getAuthor().getEmail(), authentication);
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    private AdsDto mapAdsDto(List<Ad> adList) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
         adsDto.setResults(adList.stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList()));
        return adsDto;
    }
}
