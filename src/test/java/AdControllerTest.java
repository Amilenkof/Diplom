import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.AdController;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

@RunWith(MockitoJUnitRunner.class)
public class AdControllerTest {

    @Mock
    private AdService adService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdController adController;

    private MockMvc mockMvc;



    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adController).build();
    }
    @Test
    public void contextLoads() {
        assertThat(adController).isNotNull();
    }

    @Test
    public void testGetAllAds() throws Exception {
        // Arrange
        List<AdDto> ads = new ArrayList<>();
        ads.add(new AdDto());
        ads.add(new AdDto());
        AdsDto adsDto = new AdsDto();
        adsDto.setResults(ads);
        adsDto.setCount(ads.size());

        when(adService.getAllAds()).thenReturn(adsDto);

        // Act
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        // Assert
        verify(adService, times(1)).getAllAds();
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
