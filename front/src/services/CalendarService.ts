import type {
  ScheduleContentType,
  ScheduleDetailType,
} from "@/modules/types/schedule/ScheduleType";
import http from "@/http-common";

class CalendarService {
  async getSchedule(id: number): Promise<ScheduleDetailType> {
    return await http
      .get(`schedules/${id}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async storeEvent(request: any): Promise<any> {
    return await http
      .post(`schedules/contents/${request}`)
      .then((response) => {
        console.log(response.data.data);
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new CalendarService();
