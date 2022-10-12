import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import type { EnumType, FormType } from "@/modules/types/common/CommonTypes";
import type { SearchCondType } from "@/modules/types/common/SearchEngineTypes";

/**
 * responses
 */
export interface ReviewListType {
  id: number;
  writer: string;
  title: string;
  content: string;
  elapsedTime: string;
  numberOfHits: number;
  numberOfLikes: number;
  numberOfComments: number;
}

export interface ReviewDetailType {
  id: number;
  title: string;
  profile: SimpleProfileType;
  content: string;
  category: EnumType;
  createDateTime: string;
  numberOfHits: number;
  numberOfLikes: number;
  numberOfComments: number;
}

/**
 * request
 */
export interface ReviewSearchType {
  searchWord: string | null;
  tags: string[];
  type: string | null;
  category: string | null;
}

export interface ReviewCreateType {
  title: string;
  content: string;
  category: string;
  tags: string[];
}

/**
 * form & editor
 */
export interface ReviewWriteType extends FormType<ReviewDetailType> {
  title: string;
  content: string;
  category: string;
  tags: string[];
}

export class ReviewSearchCond
  implements SearchCondType<ReviewSearchType, ReviewSearchType>
{
  searchWord: string | null;
  tags: string[];
  type: string | null;
  category: string | null;

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

  setAttr(form: ReviewSearchType): void {
    this.category = form.type != "TOTAL" ? form.type : "";
    this.searchWord = form.searchWord;
    this.tags = form.tags;
  }
}
