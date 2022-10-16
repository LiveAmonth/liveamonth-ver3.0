<script lang="ts" setup>
import CityCard from "@/components/city/CityCard.vue";
import { onMounted } from "vue";
import { useCity } from "@/composables/city/city";

const { hasCityGridInfos, getCityGridInfo } = useCity();

onMounted(async () => {
  await getCityGridInfo();
});
</script>

<template>
  <el-col v-if="hasCityGridInfos" :span="18">
    <el-row v-for="i in 2" :key="i" :gutter="3" class="mb-3">
      <el-col v-for="j in 3" :key="j" :span="8">
        <CityCard :index="i * 3 + j - 4" />
      </el-col>
    </el-row>
  </el-col>
</template>

<style lang="scss" scoped>
.grid-content {
  height: 400px;
  background: #d1d0d0;

  .el-card {
    width: 200px;
  }
}
</style>
