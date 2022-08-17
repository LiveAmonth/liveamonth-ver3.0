<script lang="ts" setup>
import { useRoute, useRouter } from "vue-router";
import TitleSlot from "@/components/common/TitleSlot.vue";

const route = useRoute();
</script>

<template>
  <TitleSlot v-if="route.name === 'schedule-list'"
    >{{ $t("menu.otherSchedule") }}
  </TitleSlot>
  <el-row>
    <el-col>
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped></style>
