<script lang="ts" setup>
import ContentTabsSlot from "@/components/common/ConentTabsSlot.vue";
import ScheduleInfiniteList from "@/components/schedule/list/ScheduleInfiniteList.vue";
import ScheduleList from "@/components/schedule/list/ScheduleList.vue";
import ReviewList from "@/components/review/ReviewList.vue";
import { ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useHome } from "@/composables/home/home";
import { useReview } from "@/composables/review/review";

const { homePostsTabs } = useHome();
const { otherSchedules, followedSchedules } = useSchedule();
const { otherReviews } = useReview();
const activeName = ref(homePostsTabs.value[0].code);
const initialSize = ref<number>(20);
</script>

<template>
  <ContentTabsSlot
    v-model:active-name="activeName"
    :border-position="'bottom'"
    :header-width="'150px'"
    :tabs="homePostsTabs"
  >
    <template v-slot:tab-1>
      <ScheduleList
        v-if="activeName === homePostsTabs[0].code && otherSchedules"
      />
    </template>
    <template v-slot:tab-2>
      <el-row class="d-flex justify-content-center">
        <el-col :span="18">
          <ReviewList
            v-if="activeName === homePostsTabs[1].code && otherReviews"
          />
        </el-col>
      </el-row>
    </template>
    <template v-if="homePostsTabs.length > 2" v-slot:tab-3>
      <el-row class="d-flex justify-content-center">
        <el-col :span="20">
          <ScheduleInfiniteList
            v-if="followedSchedules.length"
            :initial-count="initialSize"
          />
        </el-col>
      </el-row>
    </template>
  </ContentTabsSlot>
</template>
