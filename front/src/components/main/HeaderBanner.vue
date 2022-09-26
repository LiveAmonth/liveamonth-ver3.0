<script lang="ts" setup>
import CityBanner from "@/assets/image/banner/city_banner.jpg";
import ScheduleBanner from "@/components/banner/ScheduleBanner.vue";
import { useRoute } from "vue-router";
import ReviewBanner from "@/components/banner/ReviewBanner.vue";

const route = useRoute();
</script>

<template>
  <el-image
    v-if="route.name === 'city'"
    :src="CityBanner"
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
