import type { CustomPaginationType } from "@/modules/types/common/PageableType";

export default class Pagination implements CustomPaginationType {
  contentLimit: number;
  pageLimit: number;
  currentPage: number;
  isFirst: boolean;
  isLast: boolean;
  numberOfContents: number;
  numberOfPages: number;
  category: string;

  constructor(
    contentLimit = 2,
    pageLimit = 5,
    currentPage = 1,
    isFirst = true,
    isLast = false,
    numberOfContents = 0,
    numberOfPages = 0,
    category: string
  ) {
    this.contentLimit = contentLimit;
    this.pageLimit = pageLimit;
    this.currentPage = currentPage;
    this.isFirst = isFirst;
    this.isLast = isLast;
    this.numberOfContents = numberOfContents;
    this.numberOfPages = numberOfPages;
    this.category = category;
  }
}
