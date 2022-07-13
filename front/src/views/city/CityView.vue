<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useCity } from "@/composables/city";
import { useCityStore } from "@/stores/city";
import type { TabsPaneContext } from "element-plus";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import CityIntroTab from "@/components/city/CityIntroTab.vue";
import CardModeCarousel from "@/components/CardModeCarousel.vue";
import TitleSlot from "@/views/city/TitleSlot.vue";

const store = useCityStore();

const { isPending, getCityNames, getCityFoodAndView } = useCity();

const cityNames = ref<EnumType[]>();
const activeName = ref();

onMounted(async () => {
  await getCityNames();
  cityNames.value = store.cityNames;
  activeName.value = store.cityNames?.[0].code;
  await getCityFoodAndView(activeName.value);
});

const handleClick = async (tab: TabsPaneContext) => {
  const selected = String(tab.props.name);
  await getCityFoodAndView(selected);
  activeName.value = selected;
};
</script>

<template>
  <el-row class="mb-lg-2">
    <el-col>
      <TitleSlot>{{ $t("city.intro.title") }}</TitleSlot>
      <div class="px-0">
        <el-tabs
          v-if="!isPending"
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
  <div v-if="!isPending && store.views && store.foods" class="mt-5">
    <el-row class="mb-lg-2">
      <el-col>
        <TitleSlot>{{ $t("city.intro.category.FOOD") }}</TitleSlot>
        <CardModeCarousel dir="food" />
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <TitleSlot>{{ $t("city.intro.category.VIEW") }}</TitleSlot>
        <CardModeCarousel dir="view" />
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
