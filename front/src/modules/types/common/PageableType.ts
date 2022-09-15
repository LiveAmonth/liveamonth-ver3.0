export interface PageableRequestType {
  page: number;
  size: number;
  sort: string;
}

export interface PageableResponseType {
  content: object[];
  pageable: PageableType;
}

export interface PageableType {
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}

export interface CustomPageType {
  numberOfPages: number;
  numberOfContents: number;
  size: number;
  pageLimit: number;
  currentPage: number;
  isFirst: boolean;
  isLast: boolean;
  sort: string;
  category: string;
}
