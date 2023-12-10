import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdMapper adMapper;

    @InjectMocks
    private AdServiceImpl adService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAds() {
        // Arrange
        List<Ad> adList = new ArrayList<>();
        Ad ad1 = new Ad();
        ad1.setId(1L);
        ad1.setTitle("Ad 1");
        adList.add(ad1);

        Ad ad2 = new Ad();
        ad2.setId(2L);
        ad2.setTitle("Ad 2");
        adList.add(ad2);

        when(adRepository.findAll()).thenReturn(adList);

        AdsDto expectedAdsDto = new AdsDto();
        expectedAdsDto.setCount(adList.size());

        // Act
        AdsDto actualAdsDto = adService.getAllAds();

        // Assert
        assertEquals(expectedAdsDto.getCount(), actualAdsDto.getCount());
        // Add more assertions if needed
    }
}