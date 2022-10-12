import { useDate } from "@/composables/common/date";
import { useFormValidate } from "@/composables/common/formValidate";
import { reactive } from "vue";
import type { EnumType } from "@/modules/types/common/CommonTypes";
import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import type { SearchCondType } from "@/modules/types/common/SearchEngineTypes";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type { FormType } from "@/modules/types/common/CommonTypes";
import type { FormRules } from "element-plus/es";

/**
 * requests
 */
export interface ScheduleSearchType {
  memberNickname: string | null;
  cityName: string | null;
  startDate: string | null;
  title: string | null;
}

export interface ScheduleCreateType {
  title: string;
  city: string;
  period: DatePeriodType;
  publicFlag: boolean;
}

export interface ScheduleEditType {
  title: string;
  city: string;
  period: DatePeriodType;
  publicFlag: boolean;
}

export interface ContentCreateType {
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface ContentEditType {
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

/**
 * responses
 */
export interface ScheduleCardType {
  id: number;
  title: string;
  profile: SimpleProfileType;
  cost: number;
  city: EnumType;
  numberOfHits: number;
  numberOfLikes: number;
  numberOfComments: number;
  period: DatePeriodType;
  publicFlag: boolean;
  bestComment: CommentType | null;
}

export interface ScheduleContentType {
  id: number;
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface DatePeriodType {
  startDate: string;
  endDate: string;
}

export interface DateTimePeriodType {
  startDateTime: string;
  endDateTime: string;
}

/**
 * form & editor
 */
export interface ScheduleContentFormType extends FormType<ScheduleContentType> {
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface ScheduleFormType extends FormType<ScheduleCardType> {
  title: string;
  publicFlag: boolean;
  city: string;
  period: DatePeriodType;
}

export interface CalendarExtendedType {
  cost: number;
  content: string;
}

export interface ScheduleSearchFormType {
  searchType: string | null;
  searchInput: string | null;
  filterType: string | null;
  filterInput: string | null;
  sortType: string | null;
}

export class ScheduleSearchCond
  implements SearchCondType<ScheduleSearchType, ScheduleSearchFormType>
{
  memberNickname: string | null;
  cityName: string | null;
  startDate: string | null;
  title: string | null;

  constructor(
    cityName = null,
    title = null,
    memberNickname = null,
    startDate = null
  ) {
    this.cityName = cityName;
    this.title = title;
    this.memberNickname = memberNickname;
    this.startDate = startDate;
  }

  setAttr(form: ScheduleSearchFormType): void {
    if (form.searchType === "MEMBER_NICKNAME") {
      this.memberNickname = form.searchInput as string;
      this.title = null;
    } else {
      this.title = form.searchInput as string;
      this.memberNickname = null;
    }

    if (form.filterType === "START_DATE") {
      this.startDate = form.filterInput as string;
      this.cityName = null;
    } else {
      this.cityName = form.filterInput as string;
      this.startDate = null;
    }
  }

  getSearchData(): ScheduleSearchType {
    return {
      memberNickname: this.memberNickname,
      cityName: this.cityName,
      startDate: this.startDate,
      title: this.title,
    };
  }
}

export class ScheduleEditor implements ScheduleFormType {
  title: string;
  city: string;
  period: DatePeriodType;
  publicFlag: boolean;

  constructor() {
    this.title = "";
    this.publicFlag = false;
    this.city = "";
    this.period = { startDate: "", endDate: "" };
  }

  setForm(data: ScheduleCardType) {
    this.title = data.title;
    this.publicFlag = data.publicFlag;
    this.city = data.city.code;
    this.period.startDate = data.period.startDate;
    this.period.endDate = data.period.endDate;
  }

  clear(): void {
    this.title = "";
    this.publicFlag = false;
    this.city = "";
    this.period.startDate = "";
    this.period.endDate = "";
  }

  getRules(): FormRules {
    const { validateRequire, validateSelection, validateDatePeriod } =
      useFormValidate();

    return reactive<FormRules>({
      title: [validateRequire("common.title")],
      city: [validateSelection("city.title")],
      period: [validateDatePeriod(this.period)],
    });
  }

  getCreateData(): ScheduleCreateType {
    return {
      title: this.title,
      city: this.city,
      period: this.period,
      publicFlag: this.publicFlag,
    };
  }

  getEditData(): ScheduleEditType {
    return {
      title: this.title,
      city: this.city,
      period: this.period,
      publicFlag: this.publicFlag,
    };
  }
}

const { getDateTime } = useDate();

export class ScheduleContentEditor implements ScheduleContentFormType {
  content: string;
  cost: number;
  title: string;
  timePeriod: DateTimePeriodType;
  schedulePeriod: DatePeriodType;

  constructor(period: DatePeriodType) {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod = {
      startDateTime: getDateTime(period.startDate),
      endDateTime: getDateTime(period.startDate),
    };
    this.schedulePeriod = period;
  }

  setForm(data: ScheduleContentType) {
    this.title = data.title;
    this.content = data.content;
    this.cost = data.cost;
    this.timePeriod.startDateTime = data.timePeriod.startDateTime;
    this.timePeriod.endDateTime = data.timePeriod.endDateTime;
  }

  clear(): void {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod.startDateTime = getDateTime(this.schedulePeriod.startDate);
    this.timePeriod.endDateTime = getDateTime(this.schedulePeriod.startDate);
  }

  getRules(): FormRules {
    const {
      validateRequire,
      validateDateTimePeriod,
      validateCost,
      validatePeriodRange,
      validateSameDate,
    } = useFormValidate();

    return reactive<FormRules>({
      title: [validateRequire("common.title")],
      content: [validateRequire("schedule.form.content.content")],
      cost: [validateCost(this.cost, 0)],
      period: [
        validateSameDate(this.timePeriod),
        validatePeriodRange(this.timePeriod, this.schedulePeriod),
        validateDateTimePeriod(this.timePeriod),
      ],
    });
  }

  getCreateData(): ContentCreateType {
    return {
      title: this.title,
      content: this.content,
      timePeriod: this.timePeriod,
      cost: this.cost,
    };
  }

  getEditData(): ContentEditType {
    return {
      title: this.title,
      content: this.content,
      timePeriod: this.timePeriod,
      cost: this.cost,
    };
  }
}
