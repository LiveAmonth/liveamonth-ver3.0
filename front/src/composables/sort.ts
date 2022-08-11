import type { SortType } from "@/modules/types/common/SortType";
import { ref } from "vue";

export const useSort = () => {
  const types = ref<SortType[]>([]);
  const currSortType = ref<string>("id,desc");

  return {
    types,
    currSortType,
  };
};
