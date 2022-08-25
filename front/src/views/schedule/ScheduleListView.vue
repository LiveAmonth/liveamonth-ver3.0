<script lang="ts" setup>
import { onMounted } from "vue";
import SimpleCalendar from "@/components/schedule/list/SimpleCalendar.vue";
import { useScheduleStore } from "@/stores/schedule";
import CustomPagination from "@/components/common/CustomPagination.vue";
import { useSchedule } from "@/composables/schedule";
import { usePagination } from "@/composables/pagination";
import ScheduleFilter from "@/components/schedule/list/ScheduleFilter.vue";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";
import ScheduleInfoCard from "@/components/schedule/list/ScheduleInfoCard.vue";

const store = useScheduleStore();
const { isPending, request, otherSchedules, getOtherSchedules } = useSchedule();
const { pageable, mappingPagination, movePage, setSort } = usePagination();

onMounted(async () => {
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(store.pageableSchedules);
  });
});

const pageClick = async (page: number) => {
  movePage(page);
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(store.pageableSchedules);
  });
};
const applyOptions = async (data: SearchSortFormType) => {
  request.value.setAttr(data);
  setSort(String(data.sortType));
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(store.pageableSchedules);
  });
};
</script>

<template>
  <el-row>
    <el-col class="align-content-center">
      <div class="search-filter">
        <ScheduleFilter @apply-option="applyOptions" />
      </div>
      <div class="schedule-list-container">
        <el-row>
          <el-col v-loading="isPending">
            <div class="schedule-list">
              <ul class="list">
                <li
                  v-for="(schedule, i) in otherSchedules"
                  :key="schedule.id"
                  class="list-item p-4"
                >
                  <el-row :gutter="5">
                    <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
                      <SimpleCalendar :index="i" />
                    </el-col>
                    <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
                      <ScheduleInfoCard
                        :index="i"
                        @go-to-member-schedules="applyOptions"
                      />
                      <el-card class="reply"> 댓글 내용 들어가야함.</el-card>
                    </el-col>
                  </el-row>
                </li>
              </ul>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>
  <CustomPagination @click="pageClick" />
</template>

<style lang="scss" scoped>
.search-filter {
  padding-left: 100px;
  margin-top: 30px;
}

@media (max-width: 1600px) {
  .search-filter {
    padding-left: 40px;
    margin-top: 30px;
  }
}

@media (max-width: 1024px) {
  .search-filter {
    padding-left: 40px;
    margin-top: 30px;
  }
}

.schedule-list {
  text-align: center;

  .list {
    padding: 0;
    margin: 0;
    list-style: none;
  }

  .list-item + .list-item {
    margin-top: 10px;
  }
}

.reply {
  margin-top: 1.3rem;
}
</style>
