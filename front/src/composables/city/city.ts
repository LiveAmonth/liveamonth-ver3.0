import { computed, ref } from "vue";
import { useCityStore } from "@/stores/city/city";
import type { CityCardType } from "@/modules/types/city/CityTypes";
import type { ImageContentType } from "@/modules/types/common/CommonTypes";
import type {
  CityTransportType,
  CityWeatherType,
} from "@/modules/types/city/CityTypes";
import { CityType } from "@/modules/enums/constants";

export const useCity = () => {
  const store = useCityStore();
  const error = ref();
  const isPending = ref(false);
  const food = CityType.FOOD;
  const view = CityType.VIEW;
  const intro = CityType.INTRO;

  const cityGridInfos = computed((): CityCardType[] => store.gridInfo);
  const cityIntroDetail = computed((): ImageContentType[] => store.introDetail);
  const cityTransport = computed((): CityTransportType[] => store.transports);
  const cityWeather = computed((): CityWeatherType[] => store.weathers);

  const hasCityIntro = computed(() => store.cityIntro.state);
  const hasCityExtraInfo = computed(() => store.cityExtraInfo.state);
  const hasCityGridInfos = computed(() => store.cityGridInfo.state);

  const carouselData = (dir: string) => {
    return dir === food
      ? computed((): ImageContentType[] => store.foods)
      : computed((): ImageContentType[] => store.views);
  };

  const getCityIntro = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityIntro(cityName);
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const getExtraCityInfo = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getExtraCityInfo(cityName);
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const getCityGridInfo = async () => {
    error.value = null;
    isPending.value = true;
    try {
      if (!hasCityGridInfos.value) {
        await store.getCityGridInfo();
      }
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const calcTransportScore = (score: number): number => {
    return Math.round((score / 6) * 10) / 10;
  };

  return {
    error,
    isPending,
    intro,
    food,
    view,
    cityGridInfos,
    cityIntroDetail,
    cityTransport,
    cityWeather,
    hasCityIntro,
    hasCityExtraInfo,
    hasCityGridInfos,
    carouselData,
    getCityGridInfo,
    getCityIntro,
    getExtraCityInfo,
    calcTransportScore,
  };
};
