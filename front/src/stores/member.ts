import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  DuplicationCheckType,
  FindIdType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";
import type { TokenType } from "@/modules/types/auth/AuthType";

export const useMemberStore = defineStore("member", {
  state: () => ({
    genderType: {} as EnumType[],
    duplicationCheck: {} as DuplicationCheckType,
    simpleProfile: {} as SimpleProfileType,
    foundId: {} as FoundIdType,
  }),
  getters: {
    isAvailable: (state): boolean => state.duplicationCheck.isAvailable,
  },
  actions: {
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
    async duplicateCheck(field: string, param: string) {
      await MemberApiService.duplicateCheck(field, param)
        .then((response: DuplicationCheckType) => {
          this.duplicationCheck = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getMemberProfile(param: TokenType) {
      await MemberApiService.getMemberProfile(param)
        .then((response: SimpleProfileType) => {
          this.simpleProfile = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
