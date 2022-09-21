<script lang="ts" setup>
import CityIntroDetail from "@/components/city/CityIntroDetail.vue";
import CityTransportTable from "@/components/city/CityTransportTable.vue";
import CityWeatherTable from "@/components/city/CityWeatherTable.vue";
import { ref } from "vue";

const activeName = ref<string>("intro");
</script>

<template>
  <el-card>
    <el-tabs v-model="activeName" class="city-detail-tabs" tab-position="left">
      <el-tab-pane :label="$t('tabs.city.intro')" name="intro">
        <CityIntroDetail class="tab-detail" />
      </el-tab-pane>
      <el-tab-pane :label="$t('tabs.city.transport')" name="transport">
        <CityTransportTable class="tab-detail" />
      </el-tab-pane>
      <el-tab-pane :label="$t('tabs.city.weather')" name="weather">
        <CityWeatherTable class="tab-detail" />
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<style scoped>
.tab-detail {
  margin: 0 20px;
}
</style>
