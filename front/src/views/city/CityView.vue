<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useCity } from "@/composables/city";
import { useCityStore } from "@/stores/city";
import type { TabsPaneContext } from "element-plus";
import type { EnumType } from "@/modules/types/common/EnumType";
import CityIntroTab from "@/components/city/CityIntroTab.vue";

const cityNames = ref<EnumType[]>();
const activeName = ref();
const store = useCityStore();

const { isPending, getCityNames } = useCity();

onMounted(async () => {
  await getCityNames();
  cityNames.value = store.cityNames;
  activeName.value = store.cityNames?.[0].code;
});

const handleClick = (tab: TabsPaneContext) => {
  activeName.value = tab.props.name;
};
</script>

<template>
  <div class="px-0">
    <el-tabs
      v-if="!isPending"
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
