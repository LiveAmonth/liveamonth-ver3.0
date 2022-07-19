<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useCity } from "@/composables/city";
import { useCityStore } from "@/stores/city";
import type { TabsPaneContext } from "element-plus";
import type { EnumType } from "@/modules/types/common/EnumType";
import CityIntroTab from "@/components/city/CityIntroTab.vue";
import TitleSlot from "@/components/TitleSlot.vue";

const store = useCityStore();

const { getCityNames } = useCity();

const cityNames = ref<EnumType[]>();
const activeName = ref<string>();
const loading = ref<boolean>(true);

onMounted(async () => {
  await getCityNames();
  cityNames.value = store.cityNames;
  activeName.value = store.cityNames?.[0].code;
  store.setCity(activeName.value);
  loading.value = false;
});

const handleClick = async (tab: TabsPaneContext) => {
  const selected = String(tab.props.name);
  activeName.value = selected;
  store.setCity(activeName.value);
};
</script>

<template>
  <div v-if="loading">로딩중</div>
  <div v-else>
    <el-row class="mb-lg-2">
      <el-col>
        <TitleSlot>{{ $t("city.intro.title") }}</TitleSlot>
        <div class="px-0">
          <el-tabs
            v-model="activeName"
            class="city-intro-tabs"
            type="border-card"
            @tab-click="handleClick"
          >
            <template v-for="cityName in cityNames" :key="cityName.code">
              <el-tab-pane :label="cityName.value" :name="cityName.code">
                <CityIntroTab :name="cityName.code" />
              </el-tab-pane>
            </template>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
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
