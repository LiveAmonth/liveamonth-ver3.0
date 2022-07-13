<script setup lang="ts">
import { ref } from "vue";
import type { CityWeatherType } from "@/modules/types/city/CityIntroType";
import { useCityStore } from "@/stores/city";

const store = useCityStore();

const cityWeather = ref<CityWeatherType[]>(store.weathers);
</script>

<template>
  <el-table :data="cityWeather" table-layout="auto" stripe>
    <el-table-column label="월">
      <template #default="scope">
        <div
          v-if="scope.row.month !== undefined"
          style="display: flex; align-items: center"
        >
          <span>{{ $t(`city.tab.weather.month.${scope.row.month}`) }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="기온 정보">
      <el-table-column prop="minDegree" :label="$t('city.tab.weather.low')">
      </el-table-column>
      <el-table-column prop="averageDegree" :label="$t('city.tab.weather.avg')">
      </el-table-column>
      <el-table-column prop="maxDegree" :label="$t('city.tab.weather.high')">
      </el-table-column>
    </el-table-column>
  </el-table>
</template>
