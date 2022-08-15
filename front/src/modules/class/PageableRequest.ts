import type { PageableRequestType } from "@/modules/types/common/PageableType";

export class PageableRequest implements PageableRequestType {
  page: number;
  size: number;
  sort: string;

  constructor(page = 1, size = 5, sorts = "id,desc") {
    this.page = page;
    this.size = size;
    this.sort = sorts;
  }
}

export default PageableRequest;
