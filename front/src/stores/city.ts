import { defineStore } from "pinia";
import CityApiService from "@/services/CityApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import type {
  CityIntroType,
  CityTransportType,
  CityWeatherType,
  CityExtraType,
} from "@/modules/types/city/CityType";
import type { initDataType } from "@/modules/types/common/initDataType";

const storageCityNames: EnumType[] = JSON.parse(
  localStorage.getItem("city-names") || "{}"
);
const initCityNames: initDataType =
  storageCityNames === null
    ? { state: true, data: storageCityNames }
    : { state: false, data: {} as EnumType[] };

const storageIntro: CityIntroType = JSON.parse(
  localStorage.getItem("intro") || "{}"
);
const initCityIntro: initDataType =
  storageIntro === null
    ? { state: true, data: storageIntro }
    : { state: false, data: {} as CityIntroType };

const storageExtraInfo: CityExtraType = JSON.parse(
  localStorage.getItem("extra-info") || "{}"
) as CityExtraType;
const initCityExtraInfo: initDataType =
  storageExtraInfo === null
    ? { state: true, data: storageExtraInfo }
    : { state: false, data: {} as CityExtraType };

export const useCityStore = defineStore("city", {
  state: () => ({
    cityNames: initCityNames as initDataType,
    cityCategory: ["intro", "transport", "weather"],
    cityIntro: initCityIntro as initDataType,
    cityExtraInfo: initCityExtraInfo as initDataType,
  }),
  getters: {
    introDetail: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["INTRO"] as ImageContentType[],
    foods: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["FOOD"] as ImageContentType[],
    views: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["VIEW"] as ImageContentType[],
    transports: (state): CityTransportType[] =>
      (state.cityExtraInfo.data as CityExtraType)
        .transports as CityTransportType[],
    weathers: (state): CityWeatherType[] =>
      (state.cityExtraInfo.data as CityExtraType).weathers as CityWeatherType[],
  },
  actions: {
    async getCityNames() {
      try {
        const response = await CityApiService.getCityNames();
        localStorage.setItem("city-names", JSON.stringify(response));
        this.cityNames.state = true;
        this.cityNames.data = response;
      } catch (error) {
        console.log(error);
      }
    },
    async getCityIntro(cityName: string) {
      try {
        const response = await CityApiService.getCityIntro(cityName);
        localStorage.setItem("intro", JSON.stringify(response));
        this.cityIntro.state = true;
        this.cityIntro.data = response;
      } catch (error) {
        console.log(error);
      }
    },
    async getExtraCityInfo(cityName: string) {
      try {
        const response = await CityApiService.getExtraCityInfo(cityName);
        localStorage.setItem("extra-info", JSON.stringify(response));
        this.cityExtraInfo.state = true;
        this.cityExtraInfo.data = response;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
