import { defineStore } from "pinia";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import type {
  CityFoodAndViewType,
  CityIntroType,
  CityTransportType,
  CityWeatherType,
} from "@/modules/types/city/CityIntroType";
import CityApiService from "@/services/CityApiService";

export const useCityStore = defineStore({
  id: "city",
  state: () => ({
    cityNames: {} as EnumType[],
    cityIntro: {} as CityIntroType,
    cityIntroCat: ["intro", "tTransport", "tWeather"],
    cityFoodAndView: {} as CityFoodAndViewType,
  }),
  getters: {
    introDetail: (state): ImageContentType => {
      return {
        content: state.cityIntro.content,
        image: state.cityIntro.image,
      };
    },
    transports: (state): CityTransportType[] => state.cityIntro.transports,
    weathers: (state): CityWeatherType[] => state.cityIntro.weathers,
    foods: (state): ImageContentType[] => state.cityFoodAndView.foodInfos,
    views: (state): ImageContentType[] => state.cityFoodAndView.viewInfos,
  },
  actions: {
    async getCityNames() {
      try {
        this.cityNames = await CityApiService.getCityNames();
      } catch (error) {
        console.log(error);
      }
    },
    async getTotalCityIntro(cityName: string) {
      try {
        this.cityIntro = await CityApiService.getTotalCityIntro(cityName);
      } catch (error) {
        console.log(error);
      }
    },
    async getCityFoodAndView(cityName: string) {
      try {
        this.cityFoodAndView = await CityApiService.getCityFoodAndView(
          cityName
        );
      } catch (error) {
        console.log(error);
      }
    },
  },
});
