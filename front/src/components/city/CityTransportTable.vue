<script lang="ts" setup>
import { useCity } from "@/composables/city/city";
import { useMessageBox } from "@/composables/common/messageBox";

const { hasCityExtraInfo, cityTransport } = useCity();
const { labelMsg, titleMsg, categoryMsg } = useMessageBox();
</script>

<template>
  <el-table
    v-if="hasCityExtraInfo"
    :data="cityTransport"
    stripe
    table-layout="auto"
  >
    <el-table-column :label="labelMsg('city.transport.kind')">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span>
            {{ categoryMsg("city.transport", scope.row.category.code) }}
          </span>
        </div>
      </template>
    </el-table-column>
    <el-table-column :label="titleMsg('city.transport')" prop="station_count" />
    <el-table-column :label="labelMsg('city.transport.score')">
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
