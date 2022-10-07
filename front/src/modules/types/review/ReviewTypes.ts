import type {
  SearchCondType,
  SearchEngineFormType,
} from "@/modules/types/common/SearchType";

/**
 * requests
 */
export interface ReviewSearchType {
  searchWord: string | null;
  tags: string[];
  type: string | null;
  category: string | null;
}

/**
 * responses
 */
export interface ReviewListType {
  id: number;
  writer: string;
  title: string;
  content: string;
  elapsedTime: string;
  viewCount: number;
}

/**
 * form & editor
 */
export class ReviewSearchCond implements SearchCondType<ReviewSearchType> {
  searchWord: string | null;
  tags: string[];
  type: string;
  category: string;

  constructor() {
    this.searchWord = "";
    this.tags = [];
    this.type = "REVIEW_LIVEAMONTH";
    this.category = "";
  }

  getSearchData(): ReviewSearchType {
    return {
      searchWord: this.searchWord,
      tags: this.tags,
      type: this.type,
      category: this.category,
    };
  }

  setAttr(form: SearchEngineFormType): void {
    console.log(form);
  }
}
