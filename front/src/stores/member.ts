import { defineStore } from "pinia";
import MemberApiService from "@/services/MemberApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { DuplicationCheckType } from "@/modules/types/form/FormType";

export const useMemberStore = defineStore("member", {
  state: () => ({
    genderType: {} as EnumType[],
    duplicationCheck: {} as DuplicationCheckType,
  }),
  getters: {
    isAvailable: (state): boolean => state.duplicationCheck.isAvailable,
  },
  actions: {
    async getGenderType() {
      try {
        this.genderType = await MemberApiService.getGenderTypes();
      } catch (error) {
        console.log(error);
      }
    },
    async duplicateCheck(field: string, param: string) {
      try {
        this.duplicationCheck = await MemberApiService.duplicateCheck(
          field,
          param
        );
      } catch (error) {
        console.log(error);
      }
    },
  },
});
