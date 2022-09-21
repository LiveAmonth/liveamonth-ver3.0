<script lang="ts" setup>
import CityGridContent from "@/components/city/CityGridContent.vue";
import MyProfileCard from "@/components/member/MyProfileCard.vue";
import HomePostsTab from "@/components/main/HomePostsTab.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
</script>

<template>
  <div class="common-layout">
    <el-row class="main-content mb-5">
      <CityGridContent />
      <el-col :span="6">
        <MyProfileCard />
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
