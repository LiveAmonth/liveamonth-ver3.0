import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";

export interface CityIntroType {
  [category: string]: ImageContentType[];
}

export interface CityExtraType {
  transports: CityTransportType[];
  weathers: CityWeatherType[];
}

export interface CityTransportType {
  id: number;
  name: EnumType;
  category: EnumType;
  station_count: number;
  score: number;
}

export interface CityWeatherType {
  id: number;
  name: EnumType;
  month: string;
  maxDegree: number;
  minDegree: number;
  averageDegree: number;
}
