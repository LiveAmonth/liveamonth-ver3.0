<script lang="ts" setup>
import { useRoute, useRouter } from "vue-router";
import TitleSlot from "@/components/TitleSlot.vue";

const route = useRoute();
</script>

<template>
  <TitleSlot
    >{{ route.name === "schedule-list" ? $t("menu.otherSchedule") : "스케줄" }}
  </TitleSlot>
  <el-row>
    <el-col>
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped></style>
