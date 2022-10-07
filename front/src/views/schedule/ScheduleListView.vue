<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import ScheduleSearchEngine from "@/components/schedule/list/ScheduleSearchEngine.vue";
import SchedulePageList from "@/components/schedule/list/SchedulePageList.vue";
import { onMounted } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { usePagination } from "@/composables/common/pagination";
import type { SearchEngineFormType } from "@/modules/types/common/SearchType";

const props = defineProps({
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const category = "SCHEDULE";
const { isPending, request, schedulePage, getOtherSchedules } = useSchedule();
const { pageable, mappingPagination, movePage, setSort, clear } =
  usePagination(category);

onMounted(async () => {
  if (props.isMain) {
    await setSort("like,desc");
  } else {
    await clear();
  }
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

const applyOptions = async (data: SearchEngineFormType) => {
  request.value.setAttr(data);
  await setSort(String(data.sortType));
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(schedulePage.value);
  });
};
</script>

<template>
  <el-row>
    <el-col>
      <div v-if="!isMain" class="search-filter">
        <ScheduleSearchEngine @apply-option="applyOptions" />
      </div>
      <div class="schedule-list-container">
        <el-row>
          <el-col v-if="!isPending">
            <SchedulePageList @apply-option="applyOptions" />
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>
  <CustomPagination
    v-if="!isMain"
    :pagination-type="category"
    @click="pageClick"
  />
</template>

<style lang="scss" scoped>
.search-filter {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin-left: 10px;
}

@media screen and (max-width: 1200px) {
  .schedule-list-container {
    margin-right: 34px;
  }
}
</style>
