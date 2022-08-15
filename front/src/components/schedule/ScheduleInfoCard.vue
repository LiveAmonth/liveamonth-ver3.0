<script setup lang="ts">
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import { computed, reactive, ref } from "vue";
import { useScheduleStore } from "@/stores/schedule";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";

const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});
const emit = defineEmits(["goToMemberSchedules"]);

const store = useScheduleStore();
const schedule = computed(
  (): ScheduleCardType => store.scheduleDetails[props.index]
);

const goToMemberSchedules = () => {
  const scheduleSearchForm: SearchSortFormType = {
    searchType: store.searchTypes[0].code,
    searchInput: schedule.value.profile.nickname,
    filterType: null,
    filterInput: null,
    sortType: store.sortTypes[0].title,
  };
  emit("goToMemberSchedules", scheduleSearchForm);
};
</script>
<template>
  <el-card class="information">
    <div class="profile-title d-flex">
      <div class="profile">
        <el-popover
          :width="180"
          popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
        >
          <template #reference>
            <el-avatar :size="80" :src="`/src/assets/image/default.jpg`" />
          </template>
          <template #default>
            <div
              class="popover-content"
              style="display: flex; gap: 5px; flex-direction: column"
            >
              <el-avatar
                :size="60"
                :src="`/src/assets/image/default.jpg`"
                style="margin-bottom: 8px"
              />
              <p class="nickname" style="margin: 0; font-weight: 500">
                {{ schedule.profile.nickname }}
              </p>
              <a class="mention" @click="goToMemberSchedules">
                @ {{ $t("schedule.popover.link") }}
              </a>

              <div class="ds-info d-flex justify-content-center m-1">
                <div class="ds follower">
                  <h6>
                    {{ $t("member.follower") }}
                    <el-icon>
                      <User />
                    </el-icon>
                  </h6>
                  <p>{{ schedule.profile.numOfFollowers }}</p>
                </div>
                <div class="ds schedules">
                  <h6>
                    {{ $t("menu.schedule") }}
                    <el-icon>
                      <Calendar />
                    </el-icon>
                  </h6>
                  <p>{{ schedule.profile.numOfSchedules }}</p>
                </div>
                <div class="ds reviews">
                  <h6>
                    {{ $t("menu.review") }}
                    <el-icon>
                      <Notebook />
                    </el-icon>
                  </h6>
                  <p>{{ schedule.profile.numOfReviews }}</p>
                </div>
              </div>
            </div>
          </template>
        </el-popover>
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
</template>
<style scoped lang="scss">
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

  a {
    text-decoration: none;
    color: inherit;

    &:hover {
      font-weight: bold;
    }

    &:visited {
      color: inherit;
    }
  }
}

.popover-content {
  overflow: hidden;

  .mention {
    margin: 0;
    font-size: 14px;
    color: var(--el-color-info);
    text-decoration: none;

    &:hover {
      font-weight: bold;
      cursor: pointer;
    }

    &:visited {
      color: inherit;
    }
  }

  .ds-info {
    margin: auto;
    top: 100px;
    bottom: 0;
    right: 0;
    left: 0;
    width: inherit;
    height: 40px;
    display: flex;

    .follower,
    .schedules,
    .reviews {
      position: relative;
      left: -300px;
      width: 33%;
      text-align: center;
      color: black;
      animation: fadeInMove 2s;
      animation-fill-mode: forwards;

      h6 {
        margin: 0;
        text-transform: uppercase;
        color: #004a55;
      }

      p {
        margin-top: 0;
        font-size: 12px;
      }
    }

    .ds {
      &:nth-of-type(2) {
        animation-delay: 0.5s;
      }

      &:nth-of-type(1) {
        animation-delay: 1s;
      }
    }
  }
}
</style>
