<script lang="ts" setup>
import { computed, onMounted, ref } from "vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import SimpleCalendar from "@/components/schedule/SimpleCalendar.vue";
import { useScheduleStore } from "@/stores/schedule";
import CustomPagination from "@/components/common/CustomPagination.vue";
import { useSchedule } from "@/composables/schedules";
import { usePagination } from "@/composables/pagination";
import type {
  ScheduleCardType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import { useSort } from "@/composables/sort";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

const store = useScheduleStore();
const {
  error,
  isPending,
  getSortTypes,
  getScheduleSearchCond,
  getOtherSchedules,
} = useSchedule();
const { pagination, mappingPagination } = usePagination();
const { types, currSortType } = useSort();
const searchCond = ref<EnumType[]>();
const schedules = computed((): ScheduleCardType[] => store.scheduleDetails);

onMounted(async () => {
  await getSortTypes().then(() => {
    types.value = store.sortTypes;
  });
  await getScheduleSearchCond().then(() => {
    searchCond.value = store.searchCond;
  });
  const request: ScheduleSearchType = {
    memberNickname: null,
    cityName: null,
    startDate: null,
  };
  const pageable: PageableRequestType = {
    page: pagination.currentPage.value,
    size: pagination.contentLimit,
    sorts: currSortType.value,
  };
  await getOtherSchedules(request, pageable).then(() => {
    mappingPagination(store.pageableSchedules);
  });
});

const pageClick = async (page: number) => {
  console.log(page);
  pagination.currentPage.value = page;
  const request: ScheduleSearchType = {
    memberNickname: null,
    cityName: null,
    startDate: null,
  };
  const pageable: PageableRequestType = {
    page: pagination.currentPage.value,
    size: pagination.contentLimit,
    sorts: currSortType.value,
  };
  await getOtherSchedules(request, pageable).then(() => {
    mappingPagination(store.pageableSchedules);
  });
};

const activeName = ref([1]);
const filter = ref("오래된 순");
</script>

<template>
  <el-row>
    <el-col>
      <div class="filter-collapse">
        <el-collapse v-model="activeName">
          <el-collapse-item :name="1">
            <template #title>
              <el-icon class="me-1"><Search /></el-icon>
              Search
            </template>
            <div>{{ store.searchCond }}</div>
          </el-collapse-item>
          <el-collapse-item :name="2">
            <template #title>
              <el-icon class="me-1"><Filter /></el-icon>
              Filter
            </template>
            <div>
              <el-radio-group v-model="filter" size="large">
                <template v-for="type in store.sortTypes" :key="type.code">
                  <el-radio-button :label="type.value" />
                </template>
              </el-radio-group>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <div class="schedule-list">
        <ul class="list">
          <li
            v-for="(schedule, i) in schedules"
            :key="schedule.id"
            class="list-item p-4"
          >
            <el-row :gutter="5">
              <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
                <SimpleCalendar :index="i" />
              </el-col>
              <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
                <el-card class="information">
                  <div class="profile-title d-flex">
                    <div class="profile">
                      <el-avatar
                        :size="80"
                        :src="`/src/assets/image/default.jpg`"
                      />
                      <div class="nickname mt-2">
                        {{ schedule.profile.nickname }}
                      </div>
                    </div>
                    <div class="title-info">
                      <el-row>
                        <SmallTitleSlot>
                          <router-link
                            :to="{
                              name: 'read-schedule',
                              params: { scheduleId: schedule.id },
                            }"
                          >
                            {{ schedule.title }}
                          </router-link>
                        </SmallTitleSlot>
                      </el-row>
                      <el-row>
                        <div class="date-range">
                          <span>
                            <el-tooltip
                              :content="$t('schedule.tooltip.date')"
                              placement="left-start"
                            >
                              <el-icon><Calendar /></el-icon>
                            </el-tooltip>
                            {{ schedule.period.startDate }} ~
                            {{ schedule.period.endDate }}</span
                          >
                        </div>
                      </el-row>
                      <el-row>
                        <div class="cost">
                          <span>
                            <el-tooltip
                              :content="$t('schedule.tooltip.cost')"
                              placement="left-start"
                              ><el-icon><Money /></el-icon>
                            </el-tooltip>
                            {{ $filters.makeComma(schedule.cost) }}원</span
                          >
                        </div>
                      </el-row>
                      <el-row>
                        <div class="location">
                          <span
                            ><el-tooltip
                              :content="$t('schedule.tooltip.location')"
                              placement="left-start"
                              ><el-icon><Location /></el-icon
                            ></el-tooltip>
                            {{ $t(`city.name.${schedule.city.code}`) }}</span
                          >
                        </div>
                      </el-row>
                      <el-row>
                        <div class="view me-2">
                          <span
                            ><el-tooltip
                              :content="$t('schedule.tooltip.view')"
                              placement="bottom-end"
                              ><el-icon><View /></el-icon
                            ></el-tooltip>
                            {{ schedule.hits }}</span
                          >
                        </div>
                        <div class="like">
                          <span
                            ><el-tooltip
                              :content="$t('schedule.tooltip.like')"
                              placement="bottom"
                              ><i class="bi bi-hand-thumbs-up"></i
                            ></el-tooltip>
                            {{ schedule.likes }}</span
                          >
                        </div>
                      </el-row>
                    </div>
                  </div>
                </el-card>
                <el-card class="reply"> 댓글 내용 들어가야함.</el-card>
              </el-col>
            </el-row>
          </li>
        </ul>
      </div>
    </el-col>
  </el-row>
  <CustomPagination :pagination="pagination" @click="pageClick" />
</template>

<style lang="scss" scoped>
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

.profile {
  .nickname {
    font-size: 0.9rem;
  }
}

.information {
  .title-info {
    width: 100%;
    margin-left: 25px;

    .el-row {
      justify-content: flex-start;
    }

    .el-icon {
      margin-right: 2px;
      vertical-align: middle;
    }

    span {
      font-size: 0.78rem;
    }
  }
}

.reply {
  margin-top: 1.3rem;
}

a {
  text-decoration: none;

  &:hover {
    font-weight: bold;
  }

  &:visited {
    color: inherit;
  }
}
</style>
