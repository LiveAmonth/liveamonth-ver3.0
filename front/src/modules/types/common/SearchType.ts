export interface SearchSortFormType {
  searchType: string | null;
  searchInput: string | null;
  filterType: string | null;
  filterInput: string | Date | null;
  sortType: string | null;
}

export interface SearchCondType {
  setAttr(form: SearchSortFormType): void;
  fitToFormat(): SearchCondType;
}
