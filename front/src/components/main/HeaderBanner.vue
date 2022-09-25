<script setup lang="ts">
import CityBanner from "@/assets/image/banner/city_banner.jpg";
import ScheduleBanner from "@/components/banner/ScheduleBanner.vue";
import { useRoute } from "vue-router";
import { ref, watch } from "vue";

const route = useRoute();
</script>

<template>
  <el-image
    v-if="route.name === 'city'"
    style="width: 100%; height: 250px"
    :src="CityBanner"
    fit="fill"
  />
  <ScheduleBanner v-if="route.name === 'my-schedule'" />
  <ScheduleBanner v-if="route.name === 'schedule-list'" />
</template>

<style scoped lang="scss">
.el-image {
  border-radius: 0;
  margin-top: -1px;
  color: #6b778c;
}
</style>
