import http, {
  getSearchTypes,
  getSortTypes,
  getFilterTypes,
} from "@/http-common";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleSearchType,
  ScheduleSimpleCardType,
} from "@/modules/types/schedule/ScheduleType";
import type {
  PageableResponseType,
  PageableRequestType,
} from "@/modules/types/common/PageableType";
import ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";

class ScheduleApiService {
  async getSearchTypes() {
    return await getSearchTypes("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getFilterTypes() {
    return await getFilterTypes("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
  async getSortTypes() {
    return await getSortTypes("schedule")
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
      .get(
        `schedules/search?page=${pageable.page - 1}&size=${
          pageable.size
        }&sort=${pageable.sort}`,
        { params: request.fitToFormat() }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getScheduleContents(id: number): Promise<ScheduleContentType[]> {
    return await http
      .get(`/schedules/${id}/contents`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getMySchedules(loginId: string): Promise<ScheduleCardType[]> {
    return await http
      .get(`/schedules/${loginId}/list`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  editSchedule(form: ScheduleEditor) {
    return await http
      .patch(`/schedules/edit`)
  }
}

export default new ScheduleApiService();
