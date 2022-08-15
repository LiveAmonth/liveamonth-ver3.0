<script lang="ts" setup>
import { computed, onMounted, ref } from "vue";
import SimpleCalendar from "@/components/schedule/SimpleCalendar.vue";
import { useScheduleStore } from "@/stores/schedule";
import CustomPagination from "@/components/common/CustomPagination.vue";
import { useSchedule } from "@/composables/schedules";
import { usePagination } from "@/composables/pagination";
import type {
  ScheduleCardType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import ScheduleFilter from "@/components/schedule/ScheduleFilter.vue";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";
import ScheduleSearchCond from "@/modules/class/ScheduleCond";
import PageableRequest from "@/modules/class/PageableRequest";
import ScheduleInfoCard from "@/components/schedule/ScheduleInfoCard.vue";

const store = useScheduleStore();
const { isPending, getOtherSchedules } = useSchedule();
const { pagination, mappingPagination } = usePagination();
const request: ScheduleSearchType = new ScheduleSearchCond();
const pageable: PageableRequestType = new PageableRequest();
const schedules = computed((): ScheduleCardType[] => store.scheduleDetails);
onMounted(async () => {
  await getOtherSchedules(request, pageable).then(() => {
    mappingPagination(store.pageableSchedules);
  });
});

const pageClick = async (page: number) => {
  pagination.currentPage.value = page;
  pageable.page = pagination.currentPage.value;
  await getOtherSchedules(request, pageable).then(() => {
    mappingPagination(store.pageableSchedules);
  });
};
const applyOptions = async (data: SearchSortFormType) => {
  request.setAttr(data);
  pageable.sort = String(data.sortType);
  await getOtherSchedules(request, pageable).then(() => {
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
      <el-row>
        <el-col>
          <div class="schedule-list">
            <ul class="list">
              <li
                v-for="(schedule, i) in schedules"
                :key="schedule.id"
                class="list-item p-4"
                v-loading="isPending"
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
    </el-col>
  </el-row>
  <CustomPagination :pagination="pagination" @click="pageClick" />
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
