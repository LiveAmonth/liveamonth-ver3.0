import { defineStore } from "pinia";
import type { EnumType } from "@/modules/types/common/EnumType";
import MemberApiService from "@/services/MemberApiService";

export const useMemberStore = defineStore({
  id: "member",
  state: () => ({
    genderType: {} as EnumType[],
  }),
  getters: {},
  actions: {
    async getGenderType() {
      try {
        this.genderType = await MemberApiService.getGenderTypes();
      } catch (error) {
        console.log(error);
      }
    },
  },
});
