<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import ScheduleFilter from "@/components/schedule/list/ScheduleFilter.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleList from "@/components/schedule/list/ScheduleList.vue";
import { onMounted } from "vue";
import { useSchedule } from "@/composables/schedule";
import { usePagination } from "@/composables/pagination";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";

const category = "SCHEDULE";
const { isPending, request, schedulePage, getOtherSchedules } = useSchedule();
const { pageable, mappingPagination, movePage, setSort } =
  usePagination(category);

onMounted(async () => {
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(schedulePage.value);
  });
});

const pageClick = async (page: number) => {
  movePage(page);
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(schedulePage.value);
  });
};

const applyOptions = async (data: SearchSortFormType) => {
  request.value.setAttr(data);
  await setSort(String(data.sortType));
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(schedulePage.value);
  });
};
</script>

<template>
  <TitleSlot class="ms-5">{{ $t("schedule.title.other") }}</TitleSlot>
  <el-row>
    <el-col>
      <div class="search-filter">
        <ScheduleFilter @apply-option="applyOptions" />
      </div>
      <div class="schedule-list-container">
        <el-row>
          <el-col v-loading="isPending">
            <ScheduleList @apply-option="applyOptions" />
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>
  <CustomPagination :pagination-type="category" @click="pageClick" />
</template>

<style lang="scss" scoped>
.search-filter {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  margin-left: 10px;
}

@media (max-width: 1600px) {
  .search-filter {
    margin-top: 30px;
  }
}

@media (max-width: 1024px) {
  .search-filter {
    margin-top: 30px;
  }
}
</style>
