<script lang="ts" setup>
import { computed, ref } from "vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";

const count = ref(10);
const loading = ref(false);
const noMore = computed(() => count.value >= 20);
const disabled = computed(() => loading.value || noMore.value);
const load = () => {
  loading.value = true;
  setTimeout(() => {
    count.value += 2;
    loading.value = false;
  }, 2000);
};
const todos = ref([
  {
    description: "테스트1테스트1테스트1테스트1테스트1",
    dates: new Date("2022-08-02"),
  },
  {
    description: "테스트2",
    dates: new Date("2022-08-02"),
  },
]);
const attrs = computed(() =>
  todos.value.map((contents) => ({
    dates: contents.dates,
    popover: {
      label: contents.description,
    },
    highlight: "teal",
    customData: contents,
  }))
);
</script>

<template>
  <el-row>
    <el-col>
      <div>
        <span> 여기는 스케줄 리스트 화면 입니다.</span>
      </div>
      <div class="infinite-list-wrapper" style="overflow: auto">
        <ul
          v-infinite-scroll="load"
          :infinite-scroll-disabled="disabled"
          class="list"
        >
          <li v-for="i in count" :key="i" class="list-item p-4">
            <el-row :gutter="5">
              <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
                <v-calendar
                  :attributes="attrs"
                  :max-date="'2022-08-12'"
                  :min-date="'2022-08-02'"
                  trim-weeks
                >
                  <template #day-popover="{ day, attributes }">
                    <el-row>
                      {{ day.ariaLabel }}
                    </el-row>
                    <el-row class="mt-2 mx-1">
                      <el-col>
                        <el-timeline class="px-2">
                          <el-timeline-item
                            v-for="({ customData }, index) in attributes"
                            :key="index"
                            :hollow="true"
                            class="pb-1"
                            color="#004a55"
                          >
                            <span class="p-0 m-0" style="color: white">
                              {{ customData.description }}
                            </span>
                          </el-timeline-item>
                        </el-timeline>
                      </el-col>
                    </el-row>
                  </template>
                </v-calendar>
              </el-col>
              <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
                <el-card class="information">
                  <div class="profile-title d-flex">
                    <div class="profile">
                      <el-avatar
                        :size="80"
                        :src="`/src/assets/image/default.jpg`"
                      />
                      <div class="nickname mt-2">닉네임</div>
                    </div>
                    <div class="title-info">
                      <el-row>
                        <SmallTitleSlot>
                          <router-link
                            :to="{
                              name: 'read-schedule',
                              params: { scheduleId: 1 },
                            }"
                          >
                            {{ i }} 번 스케줄
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
                            2022-02-02 ~ 2022-02-02</span
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
                            {{ $filters.makeComma(20000) }}원</span
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
                            {{ $t("city.name.SE") }}</span
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
                            1</span
                          >
                        </div>
                        <div class="like">
                          <span
                            ><el-tooltip
                              :content="$t('schedule.tooltip.like')"
                              placement="bottom"
                              ><i class="bi bi-hand-thumbs-up"></i
                            ></el-tooltip>
                            1</span
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
        <p v-if="loading">Loading...</p>
        <p v-if="noMore">No more</p>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.infinite-list-wrapper {
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
