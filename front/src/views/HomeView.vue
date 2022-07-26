<script lang="ts" setup>
import CityGridContent from "@/components/city/CityGridContent.vue";
import MyProfileCard from "@/components/member/MyProfileCard.vue";
</script>

<template>
  <div class="common-layout">
    <el-row class="d-flex justify-content-between">
      <CityGridContent />
      <el-col :span="6">
        <MyProfileCard />
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped></style>
