import type {
  CustomPaginationType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";

export class Pagination implements CustomPaginationType {
  private _contentLimit: number;
  private _pageLimit: number;
  private _currentPage: number;
  private _isFirst: boolean;
  private _isLast: boolean;
  private _numberOfContents: number;
  private _numberOfPages: number;

  constructor(
    contentLimit = 2,
    pageLimit = 5,
    currentPage = 1,
    isFirst = true,
    isLast = false,
    numberOfContents = 0,
    numberOfPages = 0
  ) {
    this._contentLimit = contentLimit;
    this._pageLimit = pageLimit;
    this._currentPage = currentPage;
    this._isFirst = isFirst;
    this._isLast = isLast;
    this._numberOfContents = numberOfContents;
    this._numberOfPages = numberOfPages;
  }

  get contentLimit(): number {
    return this._contentLimit;
  }

  set contentLimit(value: number) {
    this._contentLimit = value;
  }

  get pageLimit(): number {
    return this._pageLimit;
  }

  set pageLimit(value: number) {
    this._pageLimit = value;
  }

  get currentPage(): number {
    return this._currentPage;
  }

  set currentPage(value: number) {
    this._currentPage = value;
  }

  get isFirst(): boolean {
    return this._isFirst;
  }

  set isFirst(value: boolean) {
    this._isFirst = value;
  }

  get isLast(): boolean {
    return this._isLast;
  }

  set isLast(value: boolean) {
    this._isLast = value;
  }

  get numberOfContents(): number {
    return this._numberOfContents;
  }

  set numberOfContents(value: number) {
    this._numberOfContents = value;
  }

  get numberOfPages(): number {
    return this._numberOfPages;
  }

  set numberOfPages(value: number) {
    this._numberOfPages = value;
  }

  currentPageGroup() {
    return Math.ceil(this._currentPage / this._pageLimit) - 1;
  }

  numberOfPageGroup(): number {
    return Math.ceil(this._numberOfPages / this._pageLimit);
  }
  getCurrentPageGroupPages(): number {
    if (this._numberOfPages < this._pageLimit) {
      return this._numberOfPages;
    } else if (this.currentPageGroup() === this.numberOfPageGroup() - 1) {
      return this._numberOfPages - this._pageLimit * this.currentPageGroup();
    } else {
      return this._pageLimit;
    }
  }

  isCurrentPage(index: number): boolean {
    return (
      this.currentPageGroup() * this._pageLimit + index === this._currentPage
    );
  }
  getCurrentPageNumber(index: number): number {
    return index + this.currentPageGroup() * this._pageLimit;
  }

  mappingPagination(data: PageableResponseType): void {
    this._numberOfContents = data.totalElements;
    this._numberOfPages = data.totalPages;
    this._isFirst = data.first;
    this._isLast = data.last;
  }
}

export default Pagination;
