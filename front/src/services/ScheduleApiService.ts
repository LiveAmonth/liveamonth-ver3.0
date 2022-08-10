import http from "@/http-common";
import type { ScheduleType } from "@/modules/types/schedule/ScheduleType";

class ScheduleApiService {
  async getOtherSchedules(size: number): Promise<ScheduleType[]> {
    return await http
      .get(`/schedules/other?size=${size}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getScheduleSearchCond() {
    return await http
      .get("/categories/search-conditions/schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new ScheduleApiService();
