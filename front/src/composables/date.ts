import dayjs from "dayjs";

export const useDate = () => {
  const convertDateTime = (value: Date | any) => {
    return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
  };
  const convertDate = (value: Date | any) => {
    return dayjs(value).format("YYYY-MM-DD");
  };

  return {
    convertDateTime,
    convertDate,
  };
};
