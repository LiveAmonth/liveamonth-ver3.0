import MemberApiService from "@/services/member/MemberApiService";
import { defineStore } from "pinia";
import type {
  FindIdType,
  FindPwType,
  ReconfirmType,
  FoundIdType,
  ProfileType,
  SimpleProfileType,
  ProfileEditor,
  ChangePasswordEditor,
  MemberCreate,
} from "@/modules/types/member/MemberTypes";
import type { CheckType } from "@/modules/types/common/CommonTypes";

export const useMemberStore = defineStore("member", {
  state: () => ({
    confirmResult: {} as CheckType,
    simpleProfile: {} as SimpleProfileType,
    memberProfile: {} as ProfileType,
    foundId: {} as FoundIdType,
  }),
  getters: {
    isAvailable: (state): boolean => state.confirmResult.result,
  },
  actions: {
    signUp: async function (request: MemberCreate) {
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
        .then((response: CheckType) => {
          this.confirmResult = response;
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
        .then((response: CheckType) => {
          this.confirmResult = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getMember: async function () {
      await MemberApiService.getMember()
        .then((response: ProfileType) => {
          this.memberProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getSimpleProfile: async function () {
      await MemberApiService.getSimpleProfile()
        .then((response: SimpleProfileType) => {
          this.simpleProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editProfileImage: async function (form: FormData) {
      await MemberApiService.editProfileImage(form)
        .then((response) => {
          console.log(response);
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
