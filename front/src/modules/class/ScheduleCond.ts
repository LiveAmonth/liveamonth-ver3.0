import type { ScheduleSearchType } from "@/modules/types/schedule/ScheduleType";
import type {
  SearchCondType,
  SearchSortFormType,
} from "@/modules/types/common/SearchType";

export class ScheduleCond implements ScheduleSearchType {
  private _memberNickname: string | null;
  private _cityName: string | null;
  private _startDate: string | null;
  private _title: string | null;

  constructor(
    cityName = null,
    title = null,
    memberNickname = null,
    startDate = null
  ) {
    this._cityName = cityName;
    this._title = title;
    this._memberNickname = memberNickname;
    this._startDate = startDate;
  }
  get memberNickname(): string | null {
    return this._memberNickname;
  }

  set memberNickname(value: string | null) {
    this._memberNickname = value;
  }

  get cityName(): string | null {
    return this._cityName;
  }

  set cityName(value: string | null) {
    this._cityName = value;
  }

  get startDate(): string | null {
    return this._startDate;
  }

  set startDate(value: string | null) {
    this._startDate = value;
  }
  get title(): string | null {
    return this._title;
  }

  set title(value: string | null) {
    this._title = value;
  }

  setAttr(form: SearchSortFormType): void {
    if (form.searchType === "MEMBER_NICKNAME") {
      this._memberNickname = form.searchInput as string;
      this._title = null;
    } else {
      this._title = form.searchInput as string;
      this._memberNickname = null;
    }

    if (form.filterType === "START_DATE") {
      this._startDate = form.filterInput as string;
      this._cityName = null;
    } else {
      this._cityName = form.filterInput as string;
      this._startDate = null;
    }
  }

  fitToFormat(): SearchCondType {
    return {
      memberNickname: this._memberNickname,
      cityName: this._cityName,
      startDate: this._startDate,
      title: this._title,
    } as ScheduleSearchType;
  }
}

export default ScheduleCond;
