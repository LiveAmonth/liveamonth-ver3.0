<script lang="ts" setup>
import { ref } from "vue";
import type { CityWeatherType } from "@/modules/types/city/CityIntroType";
import { useCityStore } from "@/stores/city";

const store = useCityStore();

const cityWeather = ref<CityWeatherType[]>(store.weathers);
</script>

<template>
  <el-table :data="cityWeather" stripe table-layout="auto">
    <el-table-column label="월">
      <template #default="scope">
        <div
          v-if="scope.row.month !== undefined"
          style="display: flex; align-items: center"
        >
          <span>{{ $t(`city.weather.month.${scope.row.month}`) }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="기온 정보">
      <el-table-column :label="$t('city.weather.low')" prop="minDegree">
      </el-table-column>
      <el-table-column :label="$t('city.weather.avg')" prop="averageDegree">
      </el-table-column>
      <el-table-column :label="$t('city.weather.high')" prop="maxDegree">
      </el-table-column>
    </el-table-column>
  </el-table>
</template>
