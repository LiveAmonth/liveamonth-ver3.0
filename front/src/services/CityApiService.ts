import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  CityExtraType,
  CityIntroType,
} from "@/modules/types/city/CityType";

class CityApiService {
  async getCityNames(): Promise<EnumType[]> {
    return await http.get("categories/city/names").then((response) => {
      return response.data.data;
    });
  }

  async getCityIntro(cityName: string): Promise<CityIntroType> {
    return await http.get(`city/${cityName}`).then((response) => {
      return response.data.data;
    });
  }

  async getExtraCityInfo(cityName: string): Promise<CityExtraType> {
    return await http.get(`city/${cityName}/extra`).then((response) => {
      return response.data.data;
    });
  }

  async getCityGridInfo() {
    return await http.get("city/grid-infos").then((response) => {
      return response.data.data;
    });
  }
}

export default new CityApiService();
