<script lang="ts" setup>
import { onMounted, ref } from "vue";
import type { TabsPaneContext } from "element-plus";
import axios from "axios";

const cityNames = ref([]);
const activeName = ref("");
onMounted(() => {
  axios.get("/lam-api/categories/city/name").then((res) => {
    cityNames.value = res.data.data;
    activeName.value = cityNames.value[0].code;
  });
});
const handleClick = (tab: TabsPaneContext) => {
  console.log(tab);
  console.log(tab.props.name);
};
</script>

<template>
  <div class="px-0">
    <el-tabs
      v-model="activeName"
      class="city-intro-tabs"
      type="border-card"
      @tab-click="handleClick"
    >
      <template v-for="cityName in cityNames" :key="cityName.code">
        <el-tab-pane
          :name="cityName.code"
          :label="cityName.value"
        ></el-tab-pane>
      </template>
    </el-tabs>
  </div>
</template>

<style>
.city-intro-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
