import http, {
  getFilterTypes,
  getSearchTypes,
  getSortTypes,
} from "@/http-common";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";
import type ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";

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

  async addSchedule(
    memberId: number,
    request: ScheduleEditor
  ): Promise<string> {
    return await http
      .post(`/schedules/${memberId}`, JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editSchedule(
    scheduleId: number,
    form: ScheduleEditor
  ): Promise<string> {
    return await http
      .patch(`/schedules/${scheduleId}`, JSON.stringify(form))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async deleteSchedule(scheduleId: number): Promise<string> {
    return await http
      .delete(`/schedules/${scheduleId}`)
      .then((response) => {
        return response.data;
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
        `/schedules/search?page=${pageable.page - 1}&size=${
          pageable.size
        }&sort=${pageable.sort}`,
        {
          params: request.fitToFormat(),
        }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getMySchedules(loginId: string): Promise<ScheduleCardType[]> {
    return await http
      .get(`/schedules/list`, { params: { login_id: loginId } })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async addScheduleContent(
    scheduleId: number,
    request: ScheduleContentEditor
  ): Promise<string> {
    return await http
      .post(`/schedules/${scheduleId}/contents`, JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editScheduleContent(
    contentId: number,
    request: ScheduleContentFormType
  ): Promise<string> {
    return await http
      .patch(`/schedules/contents/${contentId}`, JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async deleteScheduleContent(contentId: number): Promise<string> {
    return await http
      .delete(`/schedules/contents/${contentId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getScheduleContents(
    scheduleId: number
  ): Promise<ScheduleContentType[]> {
    return await http
      .get(`/schedules/${scheduleId}/contents`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new ScheduleApiService();
