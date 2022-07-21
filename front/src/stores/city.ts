import { defineStore } from "pinia";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import type {
  CityIntroType,
  CityTransportType,
  CityWeatherType,
  CityExtraType,
} from "@/modules/types/city/CityType";
import CityApiService from "@/services/CityApiService";

export const useCityStore = defineStore("city", {
  state: () => ({
    cityNames: {} as EnumType[],
    cityIntro: {} as CityIntroType,
    cityCategory: ["intro", "transport", "weather"],
    cityExtraInfo: {} as CityExtraType,
  }),
  getters: {
    introDetail: (state): ImageContentType[] => state.cityIntro["INTRO"],
    foods: (state): ImageContentType[] => state.cityIntro["FOOD"],
    views: (state): ImageContentType[] => state.cityIntro["VIEW"],
    transports: (state): CityTransportType[] => state.cityExtraInfo.transports,
    weathers: (state): CityWeatherType[] => state.cityExtraInfo.weathers,
  },
  actions: {
    async getCityNames() {
      try {
        this.cityNames = await CityApiService.getCityNames();
      } catch (error) {
        console.log(error);
      }
    },
    async getCityIntro(cityName: string) {
      try {
        const response = await CityApiService.getCityIntro(cityName);
        localStorage.setItem(`${cityName}-intro`, JSON.stringify(response));
      } catch (error) {
        console.log(error);
      }
    },
    async getExtraCityInfo(cityName: string) {
      try {
        const response = await CityApiService.getExtraCityInfo(cityName);
        localStorage.setItem(
          `${cityName}-extra-info`,
          JSON.stringify(response)
        );
      } catch (error) {
        console.log(error);
      }
    },
    setCity(cityName: string) {
      this.cityIntro = JSON.parse(
        localStorage.getItem(`${cityName}-intro`) || "{}"
      );
      this.cityExtraInfo = JSON.parse(
        localStorage.getItem(`${cityName}-extra-info`) || "{}"
      );
    },
  },
});
