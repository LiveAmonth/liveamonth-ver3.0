import http, { getSearchCond, getSort } from "@/http-common";
import type {
  ScheduleDetailType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type {
  PageableResponseType,
  PageableRequestType,
} from "@/modules/types/common/PageableType";

class ScheduleApiService {
  async getScheduleSearchCond() {
    return await getSearchCond("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSortTypes() {
    return await getSort("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getOtherSchedules(
    request: ScheduleSearchType,
    pageable: PageableRequestType
  ): Promise<PageableResponseType> {
    return await http
      .post(
        `/schedules/search?page=${pageable.page - 1}&size=${
          pageable.size
        }&sort=${pageable.sorts}`,
        JSON.stringify(request)
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new ScheduleApiService();
