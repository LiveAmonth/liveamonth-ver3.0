<script lang="ts" setup>
import {onBeforeMount, onMounted, ref} from "vue";
import type { TabsPaneContext } from "element-plus";
import CityApiService from "@/services/CityApiService";
import CityIntroTab from "@/components/city/CityIntroTab.vue";
import type {EnumResponse} from "@/modules/types/common/EnumResponse";

const cityNames = ref<EnumResponse[]>();
const activeName = ref();
onMounted(async () => {
  cityNames.value = await CityApiService.getCityNames();
  activeName.value = cityNames.value?.[0].code;
});

const handleClick = (tab: TabsPaneContext) => {
  activeName.value = tab.props.name;
  // CityApiService.getTotalCityIntro(tab.props.name).then((response) => {
  //   cityIntro.value = response.data.data;
  // });
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
        <el-tab-pane :name="cityName.code" :label="cityName.value">
          <CityIntroTab :name="cityName.code" />
        </el-tab-pane>
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
