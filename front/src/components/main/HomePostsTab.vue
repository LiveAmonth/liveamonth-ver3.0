<script setup lang="ts">
import ContentTabsSlot from "@/components/common/ConentTabsSlot.vue";
import ScheduleInfiniteList from "@/components/schedule/list/ScheduleInfiniteList.vue";
import ScheduleListView from "@/views/schedule/ScheduleListView.vue";
import { onMounted, ref } from "vue";
import { useAuth } from "@/composables/auth";
import { useHome } from "@/composables/home";
import { useSchedule } from "@/composables/schedule";
import { useMember } from "@/composables/member";

const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
const { homePostsTabs } = useHome();
const { followedSchedules, getInfiniteSchedules } = useSchedule();
const activeName = ref(isLoggedIn ? "followed" : "schedule");
const initialSize = ref<number>(3);

onMounted(async () => {
  await getInfiniteSchedules(
    simpleProfile.value.loginId,
    initialSize.value,
    null
  );
});
</script>

<template>
  <ContentTabsSlot
    v-model:active-name="activeName"
    :tabs="homePostsTabs"
    :header-width="'150px'"
    :border-position="'bottom'"
  >
    <template v-slot:tab-1>
      <ScheduleInfiniteList
        v-if="
          isLoggedIn &&
          activeName === homePostsTabs[0].code &&
          followedSchedules.length
        "
        :login-id="simpleProfile.loginId"
        :max-count="simpleProfile.numberOfFollows"
        :initial-count="initialSize"
        :is-my-page="false"
      />
      <div v-else-if="!isLoggedIn">로그인을 해주세요</div>
      <div v-else-if="!followedSchedules.length">로딩중 ..</div>
    </template>
    <template v-slot:tab-2>
      <ScheduleListView
        v-if="activeName === homePostsTabs[1].code"
        :is-main="true"
      />
    </template>
    <template v-slot:tab-3>
      <div v-if="activeName === homePostsTabs[2].code">{{ activeName }}</div>
    </template>
  </ContentTabsSlot>
</template>
