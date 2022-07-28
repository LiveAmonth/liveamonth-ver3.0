import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  DuplicationCheckType,
  FindIdType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  PostCountType,
} from "@/modules/types/member/MemberType";

export const useMemberStore = defineStore("member", {
  state: () => ({
    genderType: {} as EnumType[],
    duplicationCheck: {} as DuplicationCheckType,
    postCount: {} as PostCountType,
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
    async getPostCount(param: number) {
      await MemberApiService.getPostCount(param)
        .then((response: PostCountType) => {
          this.postCount = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
