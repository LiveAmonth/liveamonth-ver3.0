import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  DuplicationCheckType,
  FindIdType,
  FindPwType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";
import type { SignUpType } from "@/modules/types/form/FormType";

export const useMemberStore = defineStore("member", {
  state: () => ({
    genderType: {} as EnumType[],
    duplicationCheck: {} as DuplicationCheckType,
    simpleProfile: {} as SimpleProfileType,
    memberProfile: {} as ProfileType,
    foundId: {} as FoundIdType,
  }),
  getters: {
    isAvailable: (state): boolean => state.duplicationCheck.isAvailable,
  },
  actions: {
    async signUp(request: SignUpType) {
      await MemberApiService.signUp(request)
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    async getGenderType() {
      await MemberApiService.getGenderTypes()
        .then((response: EnumType[]) => {
          this.genderType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    async findId(param: FindIdType) {
      await MemberApiService.findId(param)
        .then((response: FoundIdType) => {
          this.foundId = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    async findPw(param: FindPwType) {
      await MemberApiService.findPw(param)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    async duplicateCheck(field: string, param: string) {
      await MemberApiService.duplicateCheck(field, param)
        .then((response: DuplicationCheckType) => {
          this.duplicationCheck = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getMember(param: string) {
      await MemberApiService.getMember(param)
        .then((response: ProfileType) => {
          this.memberProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getSimpleProfile(param: string) {
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
    paths: ["simpleProfile"],
  },
});
