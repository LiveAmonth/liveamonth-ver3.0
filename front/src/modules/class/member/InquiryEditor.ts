import { useFormValidate } from "@/composables/common/formValidate";
import type { WriteInquiryFormType } from "@/modules/types/form/FormType";
import type { FormRules } from "element-plus/es";
import type { InquiryType } from "@/modules/types/member/MemberType";

export default class InquiryEditor implements WriteInquiryFormType {
  category: string;
  content: string;
  title: string;
  writer: string;

  constructor(writer: string) {
    this.category = "";
    this.content = "";
    this.title = "";
    this.writer = writer;
  }

  clear(): void {
    this.content = "";
    this.title = "";
    this.category = "";
  }

  getRules(): FormRules {
    const { validateRequire } = useFormValidate();

    return {
      title: [validateRequire("form.label.inquiry.title")],
      content: [validateRequire("form.label.inquiry.content")],
    };
  }

  setForm(data: InquiryType): void {
    this.title = data.title;
    this.category = data.category.code;
    this.content = data.content;
    this.writer = data.writer;
  }

  getRequest() {
    return {
      title: this.title,
      category: this.category,
      content: this.content,
    };
  }
}
