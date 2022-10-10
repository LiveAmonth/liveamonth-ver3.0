import type { EnumType } from "@/modules/types/common/CommonTypes";
import type { ImageContentType } from "@/modules/types/common/CommonTypes";

export interface CityCardType {
  name: EnumType;
  image: string;
  averageDegree: number;
  transportScore: number;
}

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
  stationCount: number;
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
