import dayjs from "dayjs";
import "dayjs/locale/ko";
import isBetween from "dayjs/plugin/isBetween";
import isSameOrAfter from "dayjs/plugin/isSameOrAfter";
import isSameOrBefore from "dayjs/plugin/isSameOrBefore";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

dayjs.extend(isBetween);
dayjs.extend(isSameOrAfter);
dayjs.extend(isSameOrBefore);
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

  const isAfter = (compA: Date | string, compB: Date | string) => {
    return dayjs(compA).isAfter(compB);
  };

  const isBefore = (compA: Date | string, compB: Date | string) => {
    return dayjs(compA).isBefore(compB);
  };

  const isSameAfter = (compA: Date | string, compB: Date | string) => {
    return dayjs(compA).isSameOrAfter(compB);
  };

  const isSameBefore = (compA: Date | string, compB: Date | string) => {
    return dayjs(compA).isSameOrBefore(compB);
  };

  const translateDateRange = (period: DatePeriodType) => {
    return [new Date(2000, 1, 1, 12, 0, 0)];
  };

  return {
    getDate,
    getDateTime,
    translateDateRange,
    isBetween,
    isAfter,
    isSameAfter,
    isBefore,
    isSameBefore,
  };
};
