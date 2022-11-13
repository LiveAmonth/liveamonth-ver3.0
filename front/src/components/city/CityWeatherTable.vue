<script lang="ts" setup>
import { useCity } from "@/composables/city/city";
import { useMessageBox } from "@/composables/common/messageBox";

const { hasCityExtraInfo, cityWeather } = useCity();
const { categoryMsg, labelMsg } = useMessageBox();
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
          <span>{{ categoryMsg("city.month", scope.row.month.code) }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="기온 정보(&#8451;)">
      <el-table-column :label="labelMsg('city.weather.low')" prop="minDegree" />
      <el-table-column
        :label="labelMsg('city.weather.avg')"
        prop="averageDegree"
      />
      <el-table-column
        :label="labelMsg('city.weather.high')"
        prop="maxDegree"
      />
    </el-table-column>
  </el-table>
</template>
