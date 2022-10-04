<script lang="ts" setup>
import { useCity } from "@/composables/city/city";

const { hasCityExtraInfo, cityWeather } = useCity();
</script>

<template>
  <el-table
    v-if="hasCityExtraInfo"
    :data="cityWeather"
    stripe
    table-layout="auto"
  >
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
    <el-table-column label="기온 정보(&#8451;)">
      <el-table-column :label="$t('city.weather.low')" prop="minDegree" />
      <el-table-column :label="$t('city.weather.avg')" prop="averageDegree" />
      <el-table-column :label="$t('city.weather.high')" prop="maxDegree" />
    </el-table-column>
  </el-table>
</template>
