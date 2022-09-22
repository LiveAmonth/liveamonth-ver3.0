import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type {
  ConfirmFormType,
  FindIdType,
  FindPwType,
  ReconfirmType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";
import type { SignUpType } from "@/modules/types/form/FormType";
import type ProfileEditor from "@/modules/class/member/ProfileEditor";
import type ChangePasswordEditor from "@/modules/class/member/ChangePasswordEditor";

export const useMemberStore = defineStore("member", {
  state: () => ({
    confirmForm: {} as ConfirmFormType,
    simpleProfile: {} as SimpleProfileType,
    memberProfile: {} as ProfileType,
    foundId: {} as FoundIdType,
  }),
  getters: {
    isAvailable: (state): boolean => state.confirmForm.isAvailable,
  },
  actions: {
    signUp: async function (request: SignUpType) {
      await MemberApiService.signUp(request)
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    reconfirm: async function (request: ReconfirmType) {
      await MemberApiService.reconfirm(request)
        .then((response: ConfirmFormType) => {
          this.confirmForm = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editProfile: async function (request: ProfileEditor) {
      await MemberApiService.edit(request)
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    changePassword: async function (request: ChangePasswordEditor) {
      await MemberApiService.changePassword(request)
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    dropMember: async function () {
      await MemberApiService.dropMember()
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    findId: async function (param: FindIdType) {
      await MemberApiService.findId(param)
        .then((response: FoundIdType) => {
          this.foundId = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    findPw: async function (param: FindPwType) {
      await MemberApiService.findPw(param)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    duplicateCheck: async function (field: string, param: string) {
      await MemberApiService.duplicateCheck(field, param)
        .then((response: ConfirmFormType) => {
          this.confirmForm = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getMember: async function (param: string) {
      await MemberApiService.getMember(param)
        .then((response: ProfileType) => {
          this.memberProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getSimpleProfile: async function (param: string) {
      await MemberApiService.getSimpleProfile(param)
        .then((response: SimpleProfileType) => {
          this.simpleProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
  persist: {
    storage: sessionStorage,
    paths: ["simpleProfile", "memberProfile"],
  },
});
