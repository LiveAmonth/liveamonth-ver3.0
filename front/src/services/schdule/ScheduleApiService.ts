import http, {
  getFilterTypes,
  getSearchTypes,
  getSortTypes,
} from "@/http-common";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleEditor,
  ScheduleContentEditor,
  ScheduleSearchCond,
} from "@/modules/types/schedule/ScheduleTypes";
import type { SortType } from "@/modules/types/common/SortType";
import type { EnumType } from "@/modules/types/common/EnumType";

class ScheduleApiService {
  async getSearchTypes(): Promise<EnumType[]> {
    return await getSearchTypes("schedule")
      .then((response) => {
        console.log("여기까지는 들어오냐?");
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getFilterTypes(): Promise<EnumType[]> {
    return await getFilterTypes("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSortTypes(): Promise<SortType[]> {
    return await getSortTypes("schedule")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async addSchedule(request: ScheduleEditor): Promise<string> {
    return await http
      .post(`/schedules`, JSON.stringify(request.getCreateDate()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editSchedule(
    scheduleId: number,
    request: ScheduleEditor
  ): Promise<string> {
    return await http
      .patch(`/schedules/${scheduleId}`, JSON.stringify(request.getEditDate()))
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

  async getSchedule(scheduleId: number): Promise<any> {
    return await http
      .get(`/schedules/${scheduleId}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getOtherSchedules(
    request: ScheduleSearchCond,
    pageable: PageableRequestType
  ): Promise<PageableResponseType> {
    return await http
      .get(
        `/schedules/search?page=${pageable.page - 1}&size=${
          pageable.size
        }&sort=${pageable.sort}`,
        {
          params: request.getSearchData(),
        }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getMySchedules(
    loginId: string,
    size: number | null = null,
    lastId: number | null = null
  ): Promise<ScheduleCardType[]> {
    return await http
      .get(
        `/schedules/list?login_id=${loginId}` +
          `${size != null ? `&size=${size}` : ""}` +
          `${lastId != null ? `&last_id=${lastId}` : ""}`
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getFollowedSchedules(
    loginId: string,
    size: number | null = null,
    lastId: number | null = null
  ): Promise<ScheduleCardType[]> {
    return await http
      .get(
        `/schedules/list/followed?login_id=${loginId}` +
          `${size != null ? `&size=${size}` : ""}` +
          `${lastId != null ? `&last_id=${lastId}` : ""}`
      )
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
      .post(
        `/schedules/${scheduleId}/contents`,
        JSON.stringify(request.getCreateDate())
      )
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editScheduleContent(
    contentId: number,
    request: ScheduleContentEditor
  ): Promise<string> {
    return await http
      .patch(
        `/schedules/contents/${contentId}`,
        JSON.stringify(request.getEditDate())
      )
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
