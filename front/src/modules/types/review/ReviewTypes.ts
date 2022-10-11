import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";
import type { CommentType } from "@/modules/types/comment/CommentTypes";

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
  comments: CommentType | null;
}

/**
 * form & editor
 */
export class ReviewSearchCond {
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

  setAttr(input: string, tags: string[], type: string): void {
    this.category = type != "TOTAL" ? type : "";
    this.searchWord = input;
    this.tags = tags;
  }
}
