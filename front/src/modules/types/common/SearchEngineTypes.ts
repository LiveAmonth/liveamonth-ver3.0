export interface SearchEngineFormType {
  searchType: string | null;
  searchInput: string | null;
  filterType: string | null;
  filterInput: string | null;
  sortType: string | null;
}

export interface SearchCondType<T> {
  setAttr(form: SearchEngineFormType): void;
  getSearchData(): T;
}

export interface SortType {
  code: string;
  value: string;
  title: string;
  metaData: string;
}
