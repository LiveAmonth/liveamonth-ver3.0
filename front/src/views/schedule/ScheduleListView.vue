<script lang="ts" setup>
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import ScheduleFilter from "@/components/schedule/list/ScheduleFilter.vue";
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { onMounted } from "vue";
import { useSchedule } from "@/composables/schedule";
import { usePagination } from "@/composables/pagination";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";

const category = "SCHEDULE";
const { isPending, request, schedulePage, otherSchedules, getOtherSchedules } =
  useSchedule();
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
  console.log(data);
  request.value.setAttr(data);
  await setSort(String(data.sortType));
  await getOtherSchedules(pageable.value).then(() => {
    mappingPagination(schedulePage.value);
  });
};
</script>

<template>
  <el-row>
    <TitleSlot>다른 사람 스케줄</TitleSlot>
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
                      <el-card v-if="schedule.comment" class="reply mb-2">
                        <SmallTitleSlot class="mb-3">
                          {{ $t("comment.best") }}
                        </SmallTitleSlot>
                        <CommentSlot
                          :id="schedule.comment.commentId"
                          :avatar-url="'/src/assets/image/default.jpg'"
                          :is-best="true"
                          :is-reply="false"
                          :is-writer="
                            schedule.profile.nickname ===
                            schedule.comment.profile.nickname
                          "
                        >
                          <template v-slot:writer>
                            {{ schedule.comment.profile.nickname }}
                          </template>
                          <template v-slot:elapsedTime>
                            {{ schedule.comment.elapsedTime }}
                          </template>
                          <template v-slot:content>
                            {{ schedule.comment.content }}
                          </template>
                          <template v-slot:likeCount>
                            {{ schedule.comment.likes }}
                          </template>
                          <template v-slot:dislikeCount>
                            {{ schedule.comment.dislikes }}
                          </template>
                        </CommentSlot>
                      </el-card>
                      <el-card v-else class="reply mb-0">
                        {{ $t("comment.no-comment") }}
                      </el-card>
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
  <CustomPagination :pagination-type="category" @click="pageClick" />
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
  text-align: left;
}
</style>
