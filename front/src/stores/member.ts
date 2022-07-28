import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { DuplicationCheckType } from "@/modules/types/form/FormType";
import type { PostCountType } from "@/modules/types/member/MemberType";
import { resultProps } from "element-plus";

export const useMemberStore = defineStore("member", {
  state: () => ({
    genderType: {} as EnumType[],
    duplicationCheck: {} as DuplicationCheckType,
    postCount: {} as PostCountType,
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
