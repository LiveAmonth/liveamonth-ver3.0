<script lang="ts" setup>
import ContentTabsSlot from "@/components/common/ConentTabsSlot.vue";
import ScheduleInfiniteList from "@/components/schedule/list/ScheduleInfiniteList.vue";
import ScheduleListView from "@/views/schedule/ScheduleListView.vue";
import { ref } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMember } from "@/composables/member/member";
import { useHome } from "@/composables/home/home";

const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
const { homePostsTabs } = useHome();
const { followedSchedules } = useSchedule();
const activeName = ref(isLoggedIn ? "followed" : "schedule");
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
        v-if="activeName === homePostsTabs[0].code"
        :is-main="true"
      />
    </template>
    <template v-slot:tab-2>
      <div v-if="activeName === homePostsTabs[1].code">
        {{ activeName }}
      </div>
    </template>
    <template v-if="isLoggedIn" v-slot:tab-3>
      <ScheduleInfiniteList
        v-if="activeName === homePostsTabs[2].code && followedSchedules.length"
        :initial-count="initialSize"
        :is-my-page="false"
        :login-id="simpleProfile.loginId"
        :max-count="simpleProfile.numberOfFollows"
      />
    </template>
  </ContentTabsSlot>
</template>
