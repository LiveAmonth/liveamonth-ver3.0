import http, {
  getFilterTypes,
  getSearchTypes,
  getSortTypes,
} from "@/http-common";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/pagination/PaginationTypes";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleEditor,
  ScheduleContentEditor,
  ScheduleSearchCond,
  MyScheduleType,
  EditableScheduleType,
} from "@/modules/types/schedule/ScheduleTypes";
import type { SortType } from "@/modules/types/common/SearchEngineTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";

class ScheduleApiService {
  async getSearchTypes(): Promise<EnumType[]> {
    return await getSearchTypes("schedule")
      .then((response) => {
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

  async addSchedule(loginId: string, request: ScheduleEditor): Promise<string> {
    return await http
      .post(`/schedules/${loginId}`, JSON.stringify(request.getCreateData()))
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
      .patch(`/schedules/${scheduleId}`, JSON.stringify(request.getEditData()))
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
  ): Promise<MyScheduleType[]> {
    return await http
      .get(`/schedules/list/${loginId}`, {
        params: {
          size: size != null ? size : null,
          last_id: lastId != null ? lastId : null,
        },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getEditableSchedules(loginId: string): Promise<EditableScheduleType[]> {
    return await http
      .get(`/schedules/list/${loginId}/edit`)
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
      .get(`/schedules/list/${loginId}/followed`, {
        params: {
          size: size != null ? size : null,
          last_id: lastId != null ? lastId : null,
        },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getFollowedPostsCount(loginId: string): Promise<number> {
    return await http
      .get(`schedules/count/${loginId}/followed`)
      .then((response) => {
        return response.data.data.counts;
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
        JSON.stringify(request.getCreateData())
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
        JSON.stringify(request.getEditData())
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
      .get(`/schedules/${scheduleId}/detail`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  viewCountUp(scheduleId: number): Promise<void> {
    return http
      .patch(`/schedules/${scheduleId}/count-up`, {})
      .then((response) => {
        console.log(response.data.message);
      })
      .catch((error) => {
        console.log(error.response.data);
      });
  }
}

export default new ScheduleApiService();
