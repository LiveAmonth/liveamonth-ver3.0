<script lang="ts" setup>
import { computed } from "vue";
import { useCityStore } from "@/stores/city";
import type { CityTransportType } from "@/modules/types/city/CityType";
const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const store = useCityStore();

const cityTransport = computed((): CityTransportType[] => store.transports);
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
