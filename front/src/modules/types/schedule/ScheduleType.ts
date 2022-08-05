export interface ScheduleDataType {
  key: unknown;
  // Attribute type definitions
  highlight: boolean | HighlightType | DateRangeType; // Boolean, String, Object
  dot: boolean; // Boolean, String, Object
  bar: boolean; // Boolean, String, Object
  content: "red"; // Boolean, String, Object
  popover: object; // Only objects allowed
  // Your custom data object for later access, if needed
  customData: object;
  // We also need some dates to know where to display the attribute
  // We use a single date here, but it could also be an array of dates,
  //  a date range or a complex date pattern.
  dates: Date | DateRangeType;
  // You can optionally provide dates to exclude
  excludeDates: null;
  // Think of `order` like `z-index`
  order: number;
}

export interface HighlightType {
  color: string;
  fillMode: string;
  contentClass: string;
}

export interface DateRangeType {
  start: object;
  base: object;
  end: object;
}
