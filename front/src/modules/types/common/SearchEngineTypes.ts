export interface SearchCondType<T, D> {
  setAttr(form: D): void;
  getSearchData(): T;
}

export interface SortType {
  code: string;
  value: string;
  title: string;
  metaData: string;
}
