import type {
  ChangePasswordType,
  FormType,
} from "@/modules/types/form/FormType";
import type { FormRules } from "element-plus/es";
import { useFormValidate } from "@/composables/common/formValidate";

export default class ChangePasswordEditor
  implements FormType<ChangePasswordType>
{
  password: string;
  passwordCheck: string;

  constructor(password = "", passwordCheck = "") {
    this.password = password;
    this.passwordCheck = passwordCheck;
  }

  clear(): void {
    this.password = "";
    this.passwordCheck = "";
  }

  getRules(): FormRules {
    const { validateRequire, validatePattern, validatePassword } =
      useFormValidate();

    return {
      password: [
        validateRequire("member.password"),
        validatePattern(
          "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}",
          "validation.pattern.password"
        ),
      ],
      passwordCheck: [
        validateRequire("member.passwordCheck"),
        validatePassword(this.password),
      ],
    };
  }

  setForm(data: ChangePasswordType): void {
    this.password = data.password;
    this.passwordCheck = data.passwordCheck;
  }

  getRequest() {
    return {
      password: this.password,
    };
  }
}
