export const useDate = () => {
  const leftPad = (value: number): string => {
    return value >= 10 ? String(value) : `0${value}`;
  };

  const convertDateToString = (value: any, delimiter = "-") => {
    return [
      value.getFullYear(),
      leftPad(value.getMonth() + 1),
      leftPad(value.getDate()),
    ].join(delimiter);
  };
  return {
    convertDateToString,
  };
};
