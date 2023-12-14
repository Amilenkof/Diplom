package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;

    @Override
    public AdsDto getAllAds() {
        List<Ad> adList = adRepository.findAll();
        return mapAdsDto(adList);
    }

    @Override
    public AdDto addAd(MultipartFile image,
                       CreateOrUpdateAdDto createOrUpdateAdDto,
                       Authentication authentication) {
        Ad ad = adMapper.toEntity(createOrUpdateAdDto);
        User user = new SecurityUtils().getCurrentUser(authentication.getName());
        ad.setAuthor(user);
        ad.setImage(imageService.uploadImage(image));
        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    public AdsDto getAdsMe(Authentication authentication) {
        User user = new SecurityUtils().getCurrentUser(authentication.getName());
        List<Ad> adList = adRepository.findAdByAuthorId(user.getId());
        return mapAdsDto(adList);
    }

    @Override
    public ExtendedAdDto getAd(long id) {
        return adMapper.toExtendedAdDto(adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено")));
    }

    @Override
    @Transactional
    public void deleteAd(long id,
                         Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        checkPermit(ad, authentication);
        commentRepository.deleteCommentsByAdId(id);
        imageRepository.deleteById(ad.getImage().getId());
        adRepository.deleteById(id);
    }

    @Override
    public AdDto updateInfoAd(long id,
                              CreateOrUpdateAdDto createOrUpdateAdDto,
                              Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        checkPermit(ad, authentication);
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    @Transactional
    public void updateImageAd(long id,
                              MultipartFile imageFile,
                              Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        checkPermit(ad, authentication);
        Image image = ad.getImage();
        ad.setImage(imageService.uploadImage(imageFile));
        imageService.removeImage(image);
        adRepository.save(ad);
    }

    public void checkPermit(Ad ad, Authentication authentication) {
        if (!ad.getAuthor().getEmail().equals(authentication.getName()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw new AccessDeniedException("Вы не можете редактировать или удалять чужое объявление");
        }
    }


    public AdsDto mapAdsDto(List<Ad> adList) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
        adsDto.setResults(adList.stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList()));
        return adsDto;
    }

}
