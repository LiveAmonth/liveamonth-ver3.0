import type { CommentFormType, FormType } from "@/modules/types/form/FormType";
import type { FormRules } from "element-plus/es";
import { useFormValidate } from "@/composables/formValidate";

export class CommentEditor implements FormType<CommentFormType> {
  comment: string;

  constructor(comment = "") {
    this.comment = comment;
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
  }
}
