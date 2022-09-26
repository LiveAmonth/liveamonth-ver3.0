import dayjs from "dayjs";
import "dayjs/locale/ko";
import timezone from "dayjs/plugin/timezone";
import utc from "dayjs/plugin/utc";
import isBetween from "dayjs/plugin/isBetween";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

dayjs.extend(isBetween);
dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.locale("ko");

export const useDate = () => {
  const now = () => {
    return dayjs().format("YYYY-MM-DD");
  };

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

  return {
    now,
    getDate,
    getDateTime,
    isBetween,
    isSameDate,
    isAfter,
    isBefore,
  };
};
