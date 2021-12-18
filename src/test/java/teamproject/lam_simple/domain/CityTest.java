package teamproject.lam_simple.domain;

import org.junit.jupiter.api.Test;
import teamproject.lam_simple.constants.CategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityTest {

    @Test
    public void City_CityName_비어있으면_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            City.builder()
                    .name(null)
                    .build();
        });
    }
    @Test
    public void City_test() {
        final City city = City.builder()
                .name(CategoryConstants.CityNames.SE)
                .build();
        assertThat(city.getName()).isEqualTo(CategoryConstants.CityNames.SE);
    }

}