export interface PageableRequestType {
  page: number;
  size: number;
  sort: string;
  category: string;
}

export interface PageableResponseType {
  content: object[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    unpaged: boolean;
  };
  size: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  totalElements: number;
  totalPages: number;
}

export interface CustomPaginationType {
  numberOfPages: number;
  numberOfContents: number;
  contentLimit: number;
  pageLimit: number;
  currentPage: number;
  isFirst: boolean;
  isLast: boolean;
  category: string;
}
