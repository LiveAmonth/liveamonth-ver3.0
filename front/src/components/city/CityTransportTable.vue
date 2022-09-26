<script lang="ts" setup>
import { useCity } from "@/composables/city/city";
const { cityTransport } = useCity();
</script>

<template>
  <el-table :data="cityTransport" stripe table-layout="auto">
    <el-table-column :label="$t('city.transport.kind')">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span>{{
            $t(`city.transport.category.${scope.row.category.code}`)
          }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column
      :label="$t('city.transport.infoTitle')"
      prop="station_count"
    />
    <el-table-column :label="$t('city.transport.score')">
      <template #default="scope">
        <el-rate
          v-model="scope.row.score"
          class="pt-2"
          disabled
          text-color="#ff9900"
        />
      </template>
    </el-table-column>
  </el-table>
</template>
