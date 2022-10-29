<script lang="ts" setup>
import ScheduleBanner from "@/components/banner/ScheduleBanner.vue";
import ReviewBanner from "@/components/banner/ReviewBanner.vue";
import { useRoute } from "vue-router";

const route = useRoute();
</script>

<template>
  <el-image
    v-if="route.name === 'city'"
    src="https://i.ibb.co/jzBK031/city-banner.jpg"
    fit="fill"
    style="width: 100%; height: 250px"
  />
  <ScheduleBanner v-if="route.name === 'my-schedule'" :name="'my'" />
  <ScheduleBanner v-if="route.name === 'schedule-list'" :name="'other'" />
  <ReviewBanner v-if="route.name === 'review-list'" />
</template>

<style lang="scss" scoped>
.el-image {
  border-radius: 0;
  margin-top: -1px;
  color: #6b778c;
}
</style>
