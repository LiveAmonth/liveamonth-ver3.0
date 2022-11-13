import { useFormValidate } from "@/composables/common/formValidate";
import type { EnumType } from "@/modules/types/common/CommonTypes";
import type { FormType } from "@/modules/types/common/CommonTypes";
import type { FormRules } from "element-plus/es";

/**
 * responses
 */
export interface ProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  name: string;
  email: string;
  gender: EnumType;
  birth: string;
  numberOfReviews: number;
  numberOfSchedules: number;
  numberOfFollowers: number;
  numberOfFollows: number;
}

export interface SimpleProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  numberOfReviews: number;
  numberOfSchedules: number;
  numberOfFollowers: number;
  numberOfFollows: number;
}

export interface FoundIdType {
  loginId: string;
  created: string;
}

export interface InquiryListType {
  id: number;
  title: string;
  writer: string;
  category: EnumType;
  isAnswered: boolean;
  date: string;
}

export interface InquiryType {
  id: number;
  title: string;
  writer: string;
  content: string;
  category: EnumType;
  isAnswered: boolean;
  dateTime: string;
  answer: InquiryAnswerType;
}

export interface InquiryTableType {
  id: number;
  title: string;
  writer: string;
  category: string;
  date: string;
  state: string;
}

export interface InquiryAnswerType {
  title: string;
  writer: string;
  content: string;
  dateTime: string;
}

/**
 * request
 */
export interface LoginType {
  loginId: string;
  password: string;

  [key: string]: string;
}

export interface ReconfirmType {
  password: string;
}

export interface FindIdType {
  name: string;
  email: string;

  [key: string]: string;
}

export interface FindPwType {
  loginId: string;
  email: string;

  [key: string]: string;
}

export interface SignUpCheckType {
  loginId: boolean;
  nickname: boolean;
  email: boolean;

  [key: string]: boolean;
}

export interface MemberCreateType {
  loginId: string;
  password: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
}

export interface ProfileEditType {
  nickname: string;
  email: string;
}

export interface PasswordChangeType {
  password: string;
}

export interface InquiryRequestType {
  title: string;
  category: string;
  content: string;
}

/**
 * form & editors
 */
export interface MemberCreateFormType extends FormType<MemberCreateType> {
  loginId: string;
  password: string;
  passwordCheck: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
  checkForm: SignUpCheckType;
}

export interface EditProfileFormType extends FormType<ProfileEditType> {
  loginId: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
  checkForm: SignUpCheckType;
}

export interface ChangePasswordFormType extends FormType<PasswordChangeType> {
  password: string;
  passwordCheck: string;
}

export interface InquiryWriteFormType extends FormType<InquiryType> {
  title: string;
  category: string;
  content: string;
  writer: string;
}

export class MemberCreate implements MemberCreateFormType {
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

  constructor(
    loginId = "",
    name = "",
    password = "",
    passwordCheck = "",
    nickname = "",
    email = "",
    birth = "",
    gender = ""
  ) {
    this.loginId = loginId;
    this.name = name;
    this.password = password;
    this.passwordCheck = passwordCheck;
    this.nickname = nickname;
    this.email = email;
    this.birth = birth;
    this.gender = gender;
    this.checkForm = {
      loginId: false,
      nickname: false,
      email: false,
    };
  }

  setForm(data: MemberCreateType): void {
    this.loginId = data.loginId;
    this.name = data.name;
    this.password = data.password;
    this.nickname = data.nickname;
    this.email = data.email;
    this.birth = data.birth;
    this.gender = data.gender;
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
        validatePattern("[a-zA-Z0-9]{5,20}", "pattern.loginId"),
      ],
      password: [
        validateRequire("member.password"),
        validatePattern(
          "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}",
          "pattern.password"
        ),
      ],
      passwordCheck: [
        validateRequire("member.passwordCheck"),
        validatePassword(this),
      ],
      name: [
        validateRequire("member.name"),
        validatePattern("[a-zA-Z가-힣]{2,20}", "pattern.name"),
      ],
      nickname: [
        validateRequire("member.nickname"),
        validateRange("member.nickname", 1, 20),
      ],
      email: [
        validateRequire("member.email"),
        validatePattern(
          "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}",
          "pattern.email"
        ),
      ],
      birth: [validateSelection("member.birth"), validateBirth(this)],
      gender: [validateSelection("member.gender.title")],
    };
  }

  getCreateData(): MemberCreateType {
    return {
      loginId: this.loginId,
      name: this.name,
      password: this.password,
      nickname: this.nickname,
      email: this.email,
      birth: this.birth,
      gender: this.gender,
    };
  }

  getEditData(): unknown {
    return {
      nickname: this.nickname,
      email: this.email,
    };
  }
}

export class ProfileEditor implements EditProfileFormType {
  memberId: number;
  birth: string;
  email: string;
  gender: string;
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
    this.checkForm = {
      loginId: true,
      nickname: true,
      email: true,
    };
  }

  clear(): void {
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
          "pattern.email"
        ),
      ],
    };
  }

  setForm(data: ProfileEditType): void {
    this.nickname = data.nickname;
    this.email = data.email;
  }

  getRequest() {
    return {
      nickname: this.nickname,
      email: this.email,
    };
  }

  getCreateData(): unknown {
    return undefined;
  }

  getEditData(): unknown {
    return {
      nickname: this.nickname,
      email: this.email,
    };
  }
}

export class ChangePasswordEditor implements ChangePasswordFormType {
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
          "pattern.password"
        ),
      ],
      passwordCheck: [
        validateRequire("member.passwordCheck"),
        validatePassword(this),
      ],
    };
  }

  setForm(data: PasswordChangeType): void {
    this.password = data.password;
  }

  getCreateData(): unknown {
    return undefined;
  }

  getEditData(): unknown {
    return {
      password: this.password,
    };
  }
}

export class InquiryEditor implements InquiryWriteFormType {
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
      title: [validateRequire("form.label.title")],
      content: [validateRequire("form.label.content")],
    };
  }

  setForm(data: InquiryType): void {
    this.title = data.title;
    this.category = data.category.code;
    this.content = data.content;
    this.writer = data.writer;
  }

  getCreateData(): InquiryRequestType {
    return {
      title: this.title,
      category: this.category,
      content: this.content,
    };
  }

  getEditData(): InquiryRequestType {
    return {
      title: this.title,
      category: this.category,
      content: this.content,
    };
  }
}
