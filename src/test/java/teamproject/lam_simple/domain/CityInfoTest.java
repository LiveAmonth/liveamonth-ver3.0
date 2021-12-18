package teamproject.lam_simple.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityInfoTest {

    private City city;

    @BeforeEach
    public void setCity() {
        this.city = City.builder().name(CityNames.SE).build();
    }

    @Test
    public void CityInfo_CityInfoCategory_비어있으면_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            CityInfo.builder()
                    .cityInfoCategory(null)
                    .content("서울입니다.")
                    .image("SE.png")
                    .city(city)
                    .build();
        });
    }

    @Test
    public void CityInfo_content_비어있으면_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            CityInfo.builder()
                    .cityInfoCategory(CityInfoCategory.INTRO)
                    .content(null)
                    .image("SE.png")
                    .city(city)
                    .build();
        });
    }

    @Test
    public void CityInfo_image_비어있으면_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            CityInfo.builder()
                    .cityInfoCategory(CityInfoCategory.INTRO)
                    .content("서울입니다.")
                    .image(null)
                    .city(city)
                    .build();
        });
    }

    @Test
    public void CityInfo_City_비어있으면_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            CityInfo.builder()
                    .cityInfoCategory(CityInfoCategory.INTRO)
                    .content("서울입니다.")
                    .image("SE.png")
                    .city(null)
                    .build();
        });
    }

    @Test
    public void CityInfo_test() {
        final CityInfo cityInfo = CityInfo.builder()
                .cityInfoCategory(CityInfoCategory.INTRO)
                .content("서울입니다.")
                .image("SE.png")
                .city(city)
                .build();
        assertThat(cityInfo.getCityInfoCategory()).isEqualTo(CityInfoCategory.INTRO);
        assertThat(cityInfo.getContent()).isEqualTo("서울입니다.");
        assertThat(cityInfo.getImage()).isEqualTo("SE.png");
        assertThat(cityInfo.getCity()).isEqualTo(city);
    }
}