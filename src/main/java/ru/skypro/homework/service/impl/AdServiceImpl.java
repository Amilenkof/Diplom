package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
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
        User user = SecurityUtils.getCurrentUser(authentication.getName());
        ad.setAuthor(user);
        ad.setImage(imageService.uploadImage(image));
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
        return adMapper.toExtendedAdDto(adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено")));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or @adRepository.findById(#id).get().getAuthor().getEmail() == #authentication.name")
    public void deleteAd(long id,
                         Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        commentRepository.deleteCommentsByAdId(id);
        imageRepository.deleteById(ad.getImage().getId());
        adRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or @adRepository.findById(#id).get().getAuthor().getEmail() == #authentication.name")
    public AdDto updateInfoAd(long id,
                              CreateOrUpdateAdDto createOrUpdateAdDto,
                              Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or @adRepository.findById(#id).get().getAuthor().getEmail() == #authentication.name")
    public void updateImageAd(long id,
                              MultipartFile imageFile,
                              Authentication authentication) {
        Ad ad = adRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Объявление с ID" + id + "не найдено"));
        Image image = ad.getImage();
        ad.setImage(imageService.uploadImage(imageFile));
        imageService.removeImage(image);
        adRepository.save(ad);
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
