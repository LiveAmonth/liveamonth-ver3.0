import type { PageableRequestType } from "@/modules/types/common/PageableType";

export abstract class PageableRequest implements PageableRequestType {
  protected _page: number;
  protected _size: number;
  protected _sort: string;

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

  protected constructor(page = 1, size: number, sorts = "id,desc") {
    this._page = page;
    this._size = size;
    this._sort = sorts;
  }

  abstract getType(): string;
}
export default PageableRequest;
