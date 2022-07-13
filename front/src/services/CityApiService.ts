import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  CityFoodAndViewType,
  CityIntroType,
} from "@/modules/types/city/CityIntroType";

class CityApiService {
  async getCityNames(): Promise<EnumType[]> {
    return await http.get("/categories/city/names").then((response) => {
      return response.data.data;
    });
  }

  async getCityIntroCategory(): Promise<EnumType[]> {
    return await http.get("/categories/city/intro").then((response) => {
      return response.data.data;
    });
  }

  async getTotalCityIntro(cityName: string): Promise<CityIntroType> {
    return await http.get(`city/${cityName}/total-infos`).then((response) => {
      return response.data.data;
    });
  }

  async getCityFoodAndView(cityName: string): Promise<CityFoodAndViewType> {
    return await http.get(`city/${cityName}/food-and-view`).then((response) => {
      return response.data.data;
    });
  }
}

export default new CityApiService();
