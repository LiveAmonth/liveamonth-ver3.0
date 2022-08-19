<script setup lang="ts">
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import type { PropType } from "vue";

defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
    required: true,
  },
});
</script>

<template>
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
        <slot></slot>

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
</template>

<style scoped lang="scss">
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
      animation: fadeInMove 1s;
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
