<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import ScheduleSearchEngine from "@/components/schedule/list/ScheduleSearchEngine.vue";
import SchedulePageList from "@/components/schedule/list/ScheduleList.vue";
import { onMounted } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { usePagination } from "@/composables/common/pagination";
import { useCategory } from "@/composables/common/category";

const props = defineProps({
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const category = "SCHEDULE";
const { isPending, schedulePage, getOtherSchedules } = useSchedule();
const { pageable, mappingPagination, movePage } = usePagination(category);
const {
  hasCityNames,
  hasScheduleCategory,
  getCityNames,
  getScheduleCategories,
} = useCategory();

onMounted(async () => {
  await getCityNames();
  await getScheduleCategories();
  if (!props.isMain) {
    await settingSchedules();
  }
});

const pageClick = async (page: number) => {
  movePage(page);
  await settingSchedules();
};

const settingSchedules = async () => {
  await getOtherSchedules(pageable.value);
  mappingPagination(schedulePage.value);
};
</script>

<template>
  <el-row class="d-flex justify-content-center">
    <el-col :span="18" class="search-filter">
      <ScheduleSearchEngine
        v-if="hasScheduleCategory && hasCityNames"
        @apply-option="pageClick(1)"
      />
    </el-col>
    <el-col :span="18" v-if="!isPending">
      <SchedulePageList />
    </el-col>
  </el-row>
  <CustomPagination :pagination-type="category" @click="pageClick" />
</template>
