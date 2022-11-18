import CityApiService from "@/services/city/CityApiService";
import { defineStore } from "pinia";
import type { ImageContentType } from "@/modules/types/common/CommonTypes";
import type {
  CityCardType,
  CityExtraType,
  CityIntroType,
  CityTransportType,
  CityWeatherType,
} from "@/modules/types/city/CityTypes";
import type { InitDataType } from "@/modules/types/common/CommonTypes";
import { CityType, StorageNames } from "@/modules/enums/constants";

const storageGridInfo: CityCardType[] = localStorage[
  StorageNames.CITY_GRID_INFO
]
  ? JSON.parse(localStorage[StorageNames.CITY_GRID_INFO])
  : null;
const initCityGridInfo: InitDataType = storageGridInfo
  ? { state: true, data: storageGridInfo }
  : { state: false, data: {} as CityCardType[] };

const storageIntro: CityIntroType = localStorage[StorageNames.INTRO]
  ? JSON.parse(localStorage[StorageNames.INTRO])
  : null;
const initCityIntro: InitDataType = storageIntro
  ? { state: true, data: storageIntro }
  : { state: false, data: {} as CityIntroType };

const storageExtraInfo: CityExtraType = localStorage[StorageNames.EXTRA_INTRO]
  ? JSON.parse(localStorage[StorageNames.EXTRA_INTRO])
  : null;
const initCityExtraInfo: InitDataType = storageExtraInfo
  ? { state: true, data: storageExtraInfo }
  : { state: false, data: {} as CityExtraType };

export const useCityStore = defineStore("city", {
  state: () => ({
    cityGridInfo: initCityGridInfo as InitDataType,
    cityIntro: initCityIntro as InitDataType,
    cityExtraInfo: initCityExtraInfo as InitDataType,
  }),
  getters: {
    gridInfo: (state): CityCardType[] =>
      state.cityGridInfo.data as CityCardType[],
    introDetail: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)[
        CityType.INTRO
      ] as ImageContentType[],
    foods: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)[
        CityType.FOOD
      ] as ImageContentType[],
    views: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)[
        CityType.VIEW
      ] as ImageContentType[],
    transports: (state): CityTransportType[] =>
      (state.cityExtraInfo.data as CityExtraType)
        .transports as CityTransportType[],
    weathers: (state): CityWeatherType[] =>
      (state.cityExtraInfo.data as CityExtraType).weathers as CityWeatherType[],
  },
  actions: {
    getCityIntro: async function (cityName: string) {
      try {
        const response = await CityApiService.getCityIntro(cityName);
        localStorage.setItem(StorageNames.INTRO, JSON.stringify(response));
        this.cityIntro.state = true;
        this.cityIntro.data = response;
      } catch (error) {
        console.log(error);
      }
    },

    getExtraCityInfo: async function (cityName: string) {
      try {
        const response = await CityApiService.getExtraCityInfo(cityName);
        localStorage.setItem(
          StorageNames.EXTRA_INTRO,
          JSON.stringify(response)
        );
        this.cityExtraInfo.state = true;
        this.cityExtraInfo.data = response;
      } catch (error) {
        console.log(error);
      }
    },

    getCityGridInfo: async function () {
      try {
        const response = await CityApiService.getCityGridInfo();
        localStorage.setItem(
          StorageNames.CITY_GRID_INFO,
          JSON.stringify(response)
        );
        this.cityGridInfo.state = true;
        this.cityGridInfo.data = response;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
