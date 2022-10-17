import { useFormValidate } from "@/composables/common/formValidate";
import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import type { EnumType, FormType } from "@/modules/types/common/CommonTypes";
import type { SearchCondType } from "@/modules/types/common/SearchEngineTypes";
import type { FormRules } from "element-plus/es";

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
  tags: string[];
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

export interface ReviewEditType {
  title: string;
  content: string;
  category: string;
  addedTags: string[];
  removedTags: string[];
}

export interface ReviewChangedTagType {
  addedTags: string[];
  removedTags: string[];
}

/**
 * form & editor
 */
export interface ReviewFormType extends FormType<ReviewDetailType> {
  title: string;
  content: string;
  category: string;
  tags: string[];
  changedTag: ReviewChangedTagType;
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

export class ReviewEditor implements ReviewFormType {
  category: string;
  content: string;
  tags: string[];
  title: string;
  changedTag: ReviewChangedTagType;

  constructor(category = "", content = "", tags: string[] = [], title = "") {
    this.category = category;
    this.content = content;
    this.tags = tags;
    this.title = title;
    this.changedTag = { addedTags: [], removedTags: [] };
  }

  clear(): void {
    this.category = "";
    this.content = "";
    this.tags = [];
    this.title = "";
  }

  getCreateData(): ReviewCreateType {
    return {
      title: this.title,
      category: this.category,
      tags: this.tags,
      content: this.content,
    };
  }

  getEditData(): ReviewEditType {
    return {
      title: this.title,
      category: this.category,
      addedTags: this.changedTag.addedTags,
      removedTags: this.changedTag.removedTags,
      content: this.content,
    };
  }

  getRules(): FormRules {
    const { validateRequire, validateSelection } = useFormValidate();
    return {
      title: [validateRequire("title")],
      category: [validateSelection("category")],
      content: [validateRequire("content")],
    };
  }

  setForm(data: ReviewDetailType): void {
    this.title = data.title;
    this.category = data.category.code;
    this.tags = data.tags ? data.tags : [];
    this.content = data.content;
  }
}
