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
  if (isLoggedIn.value) {
    await getInfiniteSchedules(
      simpleProfile.value.loginId,
      initialSize.value,
      null
    );
  }
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
        :login-id="simpleProfile.loginId"
        :max-count="simpleProfile.numberOfFollows"
        :initial-count="initialSize"
        :is-my-page="false"
      />
    </template>
  </ContentTabsSlot>
</template>
