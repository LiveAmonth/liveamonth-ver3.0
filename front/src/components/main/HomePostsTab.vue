<script lang="ts" setup>
import ContentTabsSlot from "@/components/common/ConentTabsSlot.vue";
import ScheduleInfiniteList from "@/components/schedule/list/ScheduleInfiniteList.vue";
import ScheduleListView from "@/views/schedule/ScheduleListView.vue";
import { ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMember } from "@/composables/member/member";
import { useHome } from "@/composables/home/home";
import { useReview } from "@/composables/review/review";
import ReviewList from "@/components/review/ReviewList.vue";

const props = defineProps({
  initialTab: {
    type: String,
    required: true,
  },
});
const { simpleProfile } = useMember();
const { homePostsTabs } = useHome();
const { otherSchedules, followedSchedules } = useSchedule();
const { otherReviews } = useReview();

const activeName = ref(props.initialTab);
const initialSize = ref<number>(3);
</script>

<template>
  <ContentTabsSlot
    v-model:active-name="activeName"
    :border-position="'bottom'"
    :header-width="'150px'"
    :tabs="homePostsTabs"
  >
    <template v-slot:tab-1>
      <ScheduleListView
        v-if="activeName === homePostsTabs[0].code && otherSchedules"
        :is-main="true"
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
            :is-my-page="false"
            :login-id="simpleProfile.loginId"
            :max-count="simpleProfile.numberOfFollows"
          />
        </el-col>
      </el-row>
    </template>
  </ContentTabsSlot>
</template>
