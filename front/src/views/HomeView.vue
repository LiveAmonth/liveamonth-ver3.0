<script lang="ts" setup>
import CityGridContent from "@/components/city/CityGridContent.vue";
import MyProfileCard from "@/components/member/MyProfileCard.vue";
import HomePostsTab from "@/components/main/HomePostsTab.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { useMember } from "@/composables/member/member";
import { computed, onMounted } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useSchedule } from "@/composables/schedule/schedule";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";

const { isLoggedIn } = useAuth();
const { simpleProfile, getSimpleProfile } = useMember();
const { titleMsg } = useMessageBox();
const {
  hasBestSchedules,
  hasFollowedSchedules,
  getBestSchedules,
  getInfiniteSchedules,
} = useSchedule();
const { hasBestReviews, getBestReviews } = useReview();
const hasTopPosts = computed(() => hasBestSchedules && hasBestReviews);
const hasFollowedPosts = computed(() =>
  isLoggedIn.value ? hasFollowedSchedules : true
);

onMounted(async () => {
  await getBestSchedules();
  await getBestReviews();
  if (isLoggedIn.value) {
    await getSimpleProfile();
    await getInfiniteSchedules(simpleProfile.value.loginId, 10, null);
  }
});
</script>

<template>
  <div class="common-layout">
    <el-row class="main-content mb-5">
      <CityGridContent />
      <el-col :span="6">
        <MyProfileCard />
      </el-col>
    </el-row>
    <TitleSlot :title="titleMsg('home.posts')" />
    <HomePostsTab v-if="hasTopPosts && hasFollowedPosts" />
  </div>
</template>

<style lang="scss" scoped>
.main-content {
  display: flex;
  justify-content: space-between;
}
</style>
