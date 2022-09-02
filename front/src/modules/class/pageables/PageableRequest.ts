import type { PageableRequestType } from "@/modules/types/common/PageableType";

export default class PageableRequest implements PageableRequestType {
  page: number;
  size: number;
  sort: string;
  category: string;

  constructor(page = 1, size: number, sorts = "id,desc", category: string) {
    this.page = page;
    this.size = size;
    this.sort = sorts;
    this.category = category;
  }
}
