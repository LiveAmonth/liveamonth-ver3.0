import dayjs from "dayjs";
import "dayjs/locale/ko";
import timezone from "dayjs/plugin/timezone";
import utc from "dayjs/plugin/utc";
import isBetween from "dayjs/plugin/isBetween";
import isSameOrAfter from "dayjs/plugin/isSameOrAfter";
import isSameOrBefore from "dayjs/plugin/isSameOrBefore";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

dayjs.extend(isBetween);
dayjs.extend(isSameOrAfter);
dayjs.extend(isSameOrBefore);
dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.locale("ko");

export const useDate = () => {
  const getDateTime = (value: Date | string | null) => {
    return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
  };

  const getDate = (value: Date | string | null) => {
    return dayjs(value).format("YYYY-MM-DD");
  };

  const isBetween = (dateTime: Date | string, range: DatePeriodType) => {
    return dayjs(dateTime).isBetween(
      getDateTime(range.startDate),
      getDateTime(range.endDate),
      "day",
      "[]"
    );
  };

  const isSameDate = (compA: Date | string, compB: Date | string) => {
    return dayjs(compA).isSame(dayjs(compB), "date");
  };

  const isAfter = (compA: string, compB: string) => {
    return dayjs(compA).isAfter(dayjs(compB));
  };

  const isBefore = (compA: string, compB: string) => {
    return dayjs(compA).isBefore(dayjs(compB));
  };

  const isSameAfter = (compA: string, compB: string) => {
    return dayjs(compA).isSameOrAfter(dayjs(compB));
  };

  const isSameBefore = (compA: string, compB: string) => {
    return dayjs(compA).isSameOrBefore(dayjs(compB));
  };

  return {
    getDate,
    getDateTime,
    isBetween,
    isSameDate,
    isAfter,
    isSameAfter,
    isBefore,
    isSameBefore,
  };
};
