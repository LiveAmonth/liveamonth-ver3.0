import type { PageableRequestType } from "@/modules/types/common/PageableType";

export class PageableRequest implements PageableRequestType {
  private _page: number;
  private _size: number;
  private _sort: string;

  get page(): number {
    return this._page;
  }

  set page(value: number) {
    this._page = value;
  }

  get size(): number {
    return this._size;
  }

  set size(value: number) {
    this._size = value;
  }

  get sort(): string {
    return this._sort;
  }
  set sort(value: string) {
    this._sort = value;
  }

  constructor(page = 1, size: number, sorts = "id,desc") {
    this._page = page;
    this._size = size;
    this._sort = sorts;
  }
}

export default PageableRequest;
