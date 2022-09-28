import { computed, ref } from "vue";
import { useCityStore } from "@/stores/city/city";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { CityCardType } from "@/modules/types/city/CityType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import type {
  CityTransportType,
  CityWeatherType,
} from "@/modules/types/city/CityType";

export const useCity = () => {
  const store = useCityStore();
  const error = ref();
  const isPending = ref(false);

  const cityNames = computed((): EnumType[] => store.cityNameList);
  const hasCityNames = computed((): boolean => store.cityNames.state);
  const cityGridInfos = computed((): CityCardType[] => store.gridInfo);
  const hasCityGridInfos = computed((): boolean => store.cityGridInfo.state);
  const cityIntroDetail = computed((): ImageContentType[] => store.introDetail);
  const cityTransport = computed((): CityTransportType[] => store.transports);
  const cityWeather = computed((): CityWeatherType[] => store.weathers);

  const carouselData = (dir: string) => {
    return dir === "food"
      ? computed((): ImageContentType[] => store.foods)
      : computed((): ImageContentType[] => store.views);
  };

  const getCityNames = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityNames();
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
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
      await store.getCityGridInfo();
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
    cityNames,
    hasCityNames,
    cityGridInfos,
    hasCityGridInfos,
    cityIntroDetail,
    cityTransport,
    cityWeather,
    carouselData,
    getCityNames,
    getCityGridInfo,
    getCityIntro,
    getExtraCityInfo,
    calcTransportScore,
  };
};
