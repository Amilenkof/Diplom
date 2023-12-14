

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;

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
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.AdServiceImpl;

class AdServiceImplTest {
    private AdServiceImpl adService;
    private AdRepository adRepository;
    private CommentRepository commentRepository;
    private ImageRepository imageRepository;
    private ImageService imageService;
    private AdMapper adMapper;
    private MockMultipartFile imageFile;
    private Authentication authentication;
    private User user;
    private Ad ad;
    private List<Ad> adList;

    @BeforeEach
    void setUp() {
        adRepository = mock(AdRepository.class);
        commentRepository = mock(CommentRepository.class);
        imageRepository = mock(ImageRepository.class);
        imageService = mock(ImageService.class);
        adMapper = mock(AdMapper.class);
        adService = new AdServiceImpl(adRepository, commentRepository, imageRepository, imageService, adMapper);
        imageFile = mock(MockMultipartFile.class);
        authentication = mock(Authentication.class);
        user = mock(User.class);
        ad = mock(Ad.class);
        adList = new ArrayList<>();
        adList.add(ad);
    }

    @Test
    void getAllAds() {
        when(adRepository.findAll()).thenReturn(adList);
        AdsDto expectedAdsDto = mock(AdsDto.class);
        when(adService.mapAdsDto(any())).thenReturn(expectedAdsDto);

        AdsDto actualAdsDto = adService.getAllAds();

        assertEquals(expectedAdsDto, actualAdsDto);
    }

    @Test
    void addAd() {
        CreateOrUpdateAdDto createOrUpdateAdDto = mock(CreateOrUpdateAdDto.class);
        when(adMapper.toEntity(createOrUpdateAdDto)).thenReturn(ad);
        when(authentication.getName()).thenReturn("test@test.com");
        SecurityUtils mock = mock(SecurityUtils.class);
        when(mock.getCurrentUser(authentication.getName())).thenReturn(user);
        when(imageService.uploadImage(imageFile)).thenReturn(mock(Image.class));
        when(adRepository.save(ad)).thenReturn(ad);
        AdDto expectedAdDto = mock(AdDto.class);
        when(adMapper.toDto(ad)).thenReturn(expectedAdDto);

        AdDto actualAdDto = adService.addAd(imageFile, createOrUpdateAdDto, authentication);

        assertEquals(expectedAdDto, actualAdDto);
    }

    @Test
    void getAdsMe() {
        when(authentication.getName()).thenReturn("test@test.com");
        when(new SecurityUtils().getCurrentUser(authentication.getName())).thenReturn(user);
        when(adRepository.findAdByAuthorId(user.getId())).thenReturn(adList);
        AdsDto expectedAdsDto = mock(AdsDto.class);
        when(adService.mapAdsDto(any())).thenReturn(expectedAdsDto);

        AdsDto actualAdsDto = adService.getAdsMe(authentication);

        assertEquals(expectedAdsDto, actualAdsDto);
    }

    @Test
    void getAd() {
        long id = 1L;
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        ExtendedAdDto expectedExtendedAdDto = mock(ExtendedAdDto.class);
        when(adMapper.toExtendedAdDto(ad)).thenReturn(expectedExtendedAdDto);

        ExtendedAdDto actualExtendedAdDto = adService.getAd(id);

        assertEquals(expectedExtendedAdDto, actualExtendedAdDto);
    }

    @Test
    void deleteAd() {
        long id = 1L;
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        when(ad.getImage()).thenReturn(mock(Image.class));

        adService.deleteAd(id, authentication);


         commentRepository.deleteCommentsByAdId(id);
         imageRepository.deleteById(ad.getImage().getId());
         adRepository.deleteById(id);
    }

    @Test
    void updateInfoAd() {
        long id = 1L;
        CreateOrUpdateAdDto createOrUpdateAdDto = mock(CreateOrUpdateAdDto.class);
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        AdDto expectedAdDto = mock(AdDto.class);
        when(adMapper.toDto(ad)).thenReturn(expectedAdDto);

        AdDto actualAdDto = adService.updateInfoAd(id, createOrUpdateAdDto, authentication);

        assertEquals(expectedAdDto, actualAdDto);
    }

    @Test
    void updateImageAd() {
        long id = 1L;
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
        when(ad.getImage()).thenReturn(mock(Image.class));
        when(imageService.uploadImage(imageFile)).thenReturn(mock(Image.class));

        adService.updateImageAd(id, imageFile, authentication);

    }

}