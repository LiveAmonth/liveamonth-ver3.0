import { useFormValidate } from "@/composables/common/formValidate";
import type {
  FormType,
  SignUpCheckType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type { FormRules } from "element-plus/es";

export default class MemberEditor implements FormType<SignUpType> {
  loginId: string;
  name: string;
  password: string;
  passwordCheck: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
  checkForm: SignUpCheckType;

  [key: string]: any;

  constructor() {
    this.loginId = "";
    this.name = "";
    this.password = "";
    this.passwordCheck = "";
    this.nickname = "";
    this.email = "";
    this.birth = "";
    this.gender = "";
    this.checkForm = {
      loginId: false,
      nickname: false,
      email: false,
    };
  }

  clear(): void {
    this.loginId = "";
    this.name = "";
    this.password = "";
    this.passwordCheck = "";
    this.nickname = "";
    this.email = "";
    this.birth = "";
    this.gender = "";
  }

  getRules(): FormRules {
    const {
      validateRequire,
      validateSelection,
      validatePattern,
      validateRange,
      validatePassword,
      validateBirth,
    } = useFormValidate();

    return {
      loginId: [
        validateRequire("member.loginId"),
        validatePattern("[a-zA-Z0-9]{3,20}", "validation.pattern.loginId"),
      ],
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
      name: [
        validateRequire("member.name"),
        validatePattern("[a-zA-Z가-힣]{2,20}", "validation.pattern.name"),
      ],
      nickname: [
        validateRequire("member.nickname"),
        validateRange("member.nickname", 1, 20),
      ],
      email: [
        validateRequire("member.email"),
        validatePattern(
          "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}",
          "validation.pattern.email"
        ),
      ],
      birth: [validateSelection("member.birth"), validateBirth(this)],
      gender: [validateSelection("member.gender.title")],
    };
  }

  setForm(data: SignUpType): void {
    this.loginId = data.loginId;
    this.name = data.name;
    this.password = data.password;
    this.passwordCheck = data.passwordCheck;
    this.nickname = data.nickname;
    this.email = data.email;
    this.birth = data.birth;
    this.gender = data.gender;
  }
}
