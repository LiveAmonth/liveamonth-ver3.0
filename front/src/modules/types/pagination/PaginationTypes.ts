/**
 * responses
 */
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

/**
 * request
 */
export interface PageableRequestType {
  page: number;
  size: number;
  sort: string;
}

/**
 * class
 */
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

export class Pagination implements CustomPageType {
  size: number;
  pageLimit: number;
  currentPage: number;
  isFirst: boolean;
  isLast: boolean;
  numberOfContents: number;
  numberOfPages: number;
  category: string;
  sort: string;

  constructor(size: number, category: string) {
    this.size = size;
    this.pageLimit = 5;
    this.currentPage = 1;
    this.isFirst = true;
    this.isLast = false;
    this.numberOfContents = 0;
    this.numberOfPages = 0;
    this.sort = "id,desc";
    this.category = category;
  }
}
