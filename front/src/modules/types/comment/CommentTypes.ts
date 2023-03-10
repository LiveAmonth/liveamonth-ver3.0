import type { FormType } from "@/modules/types/common/CommonTypes";
import type { FormRules } from "element-plus/es";
import { useFormValidate } from "@/composables/common/formValidate";

/**
 * requests
 */
export interface CommentCreateType {
  comment: string;
  parentId: number | null;
}

export interface CommentEditType {
  comment: string;
}

/**
 * reponses
 */
export interface CommentType {
  commentId: number;
  parentId: number | null;
  comment: string;
  profile: CommentProfileType;
  commentReplies: CommentType[] | null;
  elapsedTime: string;
  numberOfLikes: number;
  numberOfDislikes: number;
}

export interface CommentProfileType {
  image: string;
  nickname: string;
}

export interface BestCommentType {
  commentId: number;
  comment: string;
  profile: CommentProfileType;
  elapsedTime: string;
  numberOfLikes: number;
  numberOfDislikes: number;
}

/**
 * form & editor
 */
export interface CommentFormType extends FormType<CommentType> {
  comment: string;
  contentId: number;
  parentId: number | null;
}

export class CommentEditor implements CommentFormType {
  comment: string;
  contentId: number;
  parentId: number | null;

  constructor(contentId: number, parentId: number | null) {
    this.comment = "";
    this.contentId = contentId;
    this.parentId = parentId;
  }

  clear(): void {
    this.comment = "";
    this.parentId = null;
  }

  getRules(): FormRules {
    const { validateRequire, validateRange } = useFormValidate();
    return {
      comment: [
        validateRequire("comment.title"),
        validateRange("comment.title", 0, 1000),
      ],
    };
  }

  setForm(data: CommentType): void {
    this.comment = data.comment;
  }

  getCreateData(): CommentCreateType {
    return {
      comment: this.comment,
      parentId: this.parentId,
    };
  }

  getEditData(): CommentEditType {
    return {
      comment: this.comment,
    };
  }
}
