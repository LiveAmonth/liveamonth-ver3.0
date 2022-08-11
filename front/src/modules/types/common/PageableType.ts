import type { Ref } from "vue";

export interface PageableRequestType {
  page: number;
  size: number;
  sorts: string;
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
  numberOfPages: Ref<number>;
  numberOfContents: Ref<number>;
  contentLimit: number;
  pageLimit: number;
  pageGroup: Ref<number>;
  currentPage: Ref<number>;
  numberOfPageGroup: Ref<number>;
  isFirst: Ref<boolean>;
  isLast: Ref<boolean>;
}
