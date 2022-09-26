import type {
  EditProfileType,
  FormType,
  SignUpCheckType,
} from "@/modules/types/form/FormType";
import type { ProfileType } from "@/modules/types/member/MemberType";
import type { FormRules } from "element-plus/es";
import { useFormValidate } from "@/composables/common/formValidate";

export default class ProfileEditor implements FormType<EditProfileType> {
  memberId: number;
  birth: string;
  email: string;
  gender: string;
  image: string;
  loginId: string;
  name: string;
  nickname: string;
  checkForm: SignUpCheckType;

  [key: string]: any;

  constructor(profile: ProfileType) {
    this.memberId = profile.id;
    this.loginId = profile.loginId;
    this.name = profile.name;
    this.nickname = profile.nickname;
    this.email = profile.email;
    this.birth = profile.birth;
    this.gender = profile.gender.code;
    this.image = profile.image;
    this.checkForm = {
      loginId: true,
      nickname: true,
      email: true,
    };
  }

  clear(): void {
    this.image = "";
    this.nickname = "";
    this.email = "";
  }

  getRules(): FormRules {
    const { validateRequire, validatePattern, validateRange } =
      useFormValidate();

    return {
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
    };
  }

  setForm(data: EditProfileType): void {
    this.nickname = data.nickname;
    this.email = data.email;
    this.image = data.image;
  }

  getRequest() {
    return {
      nickname: this.nickname,
      email: this.email,
      image: this.image,
    };
  }
}
