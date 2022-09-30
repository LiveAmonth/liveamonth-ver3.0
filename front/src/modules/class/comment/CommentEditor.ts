import { useFormValidate } from "@/composables/common/formValidate";
import type { FormRules } from "element-plus/es";
import type { CommentFormType, FormType } from "@/modules/types/form/FormType";
import type {
  CommentCreateType,
  CommentEditType,
} from "@/modules/types/comment/CommentRequest";

export class CommentEditor implements FormType<CommentFormType> {
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

  setForm(data: CommentFormType): void {
    this.comment = data.comment;
    this.contentId = data.contentId;
    this.parentId = data.parentId;
  }

  getCreateData(): CommentCreateType {
    return {
      comment: this.comment,
      content_id: this.contentId,
      parent_id: this.parentId,
    };
  }

  getEditData(): CommentEditType {
    return {
      comment: this.comment,
    };
  }
}
