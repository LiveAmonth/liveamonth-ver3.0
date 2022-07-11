import http from "@/http-common";

class CityApiService {
  getCityNames(): Promise<any> {
    return http.get("/categories/city/names").then((response) => {
      return response.data.data;
    });
  }

  getCityIntroCategory(): Promise<any> {
    return http.get("/categories/city/intro").then((response) => {
      return response.data.data;
    });
  }

  getTotalCityIntro(cityName: any): Promise<any> {
    return http.get(`city/${cityName}/total-infos`).then((response) => {
      return response.data.data;
    });
  }
}

export default new CityApiService();
