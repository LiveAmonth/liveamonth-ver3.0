<script setup lang="ts">
import { ref } from "vue";
import type { CityTransportType } from "@/modules/types/city/CityIntroType";
import { useCityStore } from "@/stores/city";

const store = useCityStore();

const cityTransport = ref<CityTransportType[]>(store.transports);
</script>

<template>
  <el-table :data="cityTransport" table-layout="auto" stripe>
    <el-table-column :label="$t('city.tab.transport.category')">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span>{{
            $t(`city.transport.name.${scope.row.category.code}`)
          }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column
      prop="station_count"
      :label="$t('city.tab.transport.infoTitle')"
    />
    <el-table-column :label="$t('city.tab.transport.score')">
      <template #default="scope">
        <el-rate
          v-model="scope.row.score"
          disabled
          text-color="#ff9900"
          class="pt-2"
        />
      </template>
    </el-table-column>
  </el-table>
</template>
