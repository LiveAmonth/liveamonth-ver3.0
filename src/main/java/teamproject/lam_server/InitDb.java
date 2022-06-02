package teamproject.lam_server;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityInfo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static teamproject.lam_server.domain.city.constants.CityInfoCategory.INTRO;
import static teamproject.lam_server.domain.city.constants.CityName.SE;


//@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.cityInfoDbInit();
        initService.cityTansportDbInit();
        initService.cityWeatherDbInit();
    }

//    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void cityInfoDbInit() {
            for (CityName city : CityName.values()) {
                CityInfo introTemp = CityInfo.builder()
                        .name(city)
                        .category(INTRO)
                        .image("INTRO" + city.getCode() + ".png")
                        .build();
                CityInfo foodTemp = CityInfo.builder()
                        .name(city)
                        .image("INTRO" + city.getCode() + ".png")
                        .build();
            }
            CityInfo.builder()
                    .name(SE)
                    .category(INTRO)
                    .content("상쾌한 바다향과 솔향, 은은한 커피향이 풍기는 강릉으로 놀러오세요~! 아름다운 동해바다와 소나무 숲의 풍경을 보며 즐기는 드라이브코스는 강릉만이 가진 천혜의 관광 자원이에요. 다양한 문화유산이 있고, 최근에는 커피의 도시로 자리매김하면서 사계절 여행 마니아들의 사랑을 받는 곳이 되었어요. 또한 싱싱한 해산물과 다양한 향토 음식이 여행객을 입을 즐겁게 해주는 도시이죠. 강릉으로 떠난다면 둘러볼 만한 곳을 소개합니다~~!")
                    .image("INTRO_SE.png")
                    .build();
            CityInfo.builder()
                    .name(SE)
                    .category(INTRO)
                    .content("상쾌한 바다향과 솔향, 은은한 커피향이 풍기는 강릉으로 놀러오세요~! 아름다운 동해바다와 소나무 숲의 풍경을 보며 즐기는 드라이브코스는 강릉만이 가진 천혜의 관광 자원이에요. 다양한 문화유산이 있고, 최근에는 커피의 도시로 자리매김하면서 사계절 여행 마니아들의 사랑을 받는 곳이 되었어요. 또한 싱싱한 해산물과 다양한 향토 음식이 여행객을 입을 즐겁게 해주는 도시이죠. 강릉으로 떠난다면 둘러볼 만한 곳을 소개합니다~~!")
                    .image("INTRO_SE.png")
                    .build();
            CityInfo.builder()
                    .name(SE)
                    .category(INTRO)
                    .content("상쾌한 바다향과 솔향, 은은한 커피향이 풍기는 강릉으로 놀러오세요~! 아름다운 동해바다와 소나무 숲의 풍경을 보며 즐기는 드라이브코스는 강릉만이 가진 천혜의 관광 자원이에요. 다양한 문화유산이 있고, 최근에는 커피의 도시로 자리매김하면서 사계절 여행 마니아들의 사랑을 받는 곳이 되었어요. 또한 싱싱한 해산물과 다양한 향토 음식이 여행객을 입을 즐겁게 해주는 도시이죠. 강릉으로 떠난다면 둘러볼 만한 곳을 소개합니다~~!")
                    .image("INTRO_SE.png")
                    .build();
            CityInfo.builder()
                    .name(SE)
                    .category(INTRO)
                    .content("상쾌한 바다향과 솔향, 은은한 커피향이 풍기는 강릉으로 놀러오세요~! 아름다운 동해바다와 소나무 숲의 풍경을 보며 즐기는 드라이브코스는 강릉만이 가진 천혜의 관광 자원이에요. 다양한 문화유산이 있고, 최근에는 커피의 도시로 자리매김하면서 사계절 여행 마니아들의 사랑을 받는 곳이 되었어요. 또한 싱싱한 해산물과 다양한 향토 음식이 여행객을 입을 즐겁게 해주는 도시이죠. 강릉으로 떠난다면 둘러볼 만한 곳을 소개합니다~~!")
                    .image("INTRO_SE.png")
                    .build();
            CityInfo.builder()
                    .name(SE)
                    .category(INTRO)
                    .content("상쾌한 바다향과 솔향, 은은한 커피향이 풍기는 강릉으로 놀러오세요~! 아름다운 동해바다와 소나무 숲의 풍경을 보며 즐기는 드라이브코스는 강릉만이 가진 천혜의 관광 자원이에요. 다양한 문화유산이 있고, 최근에는 커피의 도시로 자리매김하면서 사계절 여행 마니아들의 사랑을 받는 곳이 되었어요. 또한 싱싱한 해산물과 다양한 향토 음식이 여행객을 입을 즐겁게 해주는 도시이죠. 강릉으로 떠난다면 둘러볼 만한 곳을 소개합니다~~!")
                    .image("INTRO_SE.png")
                    .build();
        }

        public void cityTansportDbInit() {

        }

        public void cityWeatherDbInit() {

        }
    }
}
