<script lang="ts" setup>
import CityGridContent from "@/components/city/CityGridContent.vue";
import MyProfileCard from "@/components/member/MyProfileCard.vue";
import HomePostsTab from "@/components/main/HomePostsTab.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { useMember } from "@/composables/member";
import { onMounted } from "vue";
import { useAuth } from "@/composables/auth";
import { useSchedule } from "@/composables/schedule";

const { isLoggedIn } = useAuth();
const { simpleProfile, getSimpleProfile } = useMember();
const { getInfiniteSchedules } = useSchedule();

onMounted(async () => {
  if (isLoggedIn.value) {
    await getSimpleProfile();
    await getInfiniteSchedules(simpleProfile.value.loginId, 3, null);
  }
});
</script>

<template>
  <div class="common-layout">
    <el-row class="main-content mb-5">
      <CityGridContent />
      <el-col :span="6">
        <MyProfileCard v-if="simpleProfile" />
      </el-col>
    </el-row>
    <TitleSlot>{{ $t("title.home.posts") }}</TitleSlot>
    <HomePostsTab />
  </div>
</template>

<style lang="scss" scoped>
.main-content {
  display: flex;
  justify-content: space-between;
}
</style>
