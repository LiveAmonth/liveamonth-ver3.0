import type { CustomPageType } from "@/modules/types/common/PageableType";

export default class Pagination implements CustomPageType {
  size: number;
  pageLimit: number;
  currentPage: number;
  isFirst: boolean;
  isLast: boolean;
  numberOfContents: number;
  numberOfPages: number;
  category: string;
  sort: string;

  constructor(size = 2, category: string) {
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
