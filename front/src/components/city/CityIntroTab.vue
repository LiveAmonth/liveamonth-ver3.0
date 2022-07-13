<script lang="ts" setup>
import {defineProps, onMounted, ref} from "vue";
import CityIntroDetail from "@/components/city/CityIntroDetail.vue";
import CityTransportTable from "@/components/city/CityTransportTable.vue";
import CityWeatherTable from "@/components/city/CityWeatherTable.vue";
import {useCity} from "@/composables/city";
import type {ImageContentType} from "@/modules/types/common/ImageContentType";
import {useCityStore} from "@/stores/city";
import CardModeCarousel from "@/components/CardModeCarousel.vue";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const store = useCityStore();

const {isPending, getTotalCityIntro, getCityFoodAndView} = useCity();

const activeName = ref(store.cityIntroCat[0]);
const cityIntro = ref<ImageContentType>();
const cityFood = ref<ImageContentType[]>();
const cityView = ref<ImageContentType[]>();
onMounted(async () => {
  await getTotalCityIntro(props.name);
  await getCityFoodAndView(props.name);
  cityIntro.value = store.introDetail;
  cityFood.value = store.foods;
  cityView.value = store.views;
});
</script>

<template>
  <el-row>
    <el-col>
      <el-tabs v-model="activeName" tab-position="left" class="city-detail-tabs">
        <template v-for="introCat in store.cityIntroCat" :key="introCat">
          <el-tab-pane :name="introCat" :label="$t(`city.tab.${introCat}`)">
            <CityIntroDetail
                v-if="!isPending && introCat === 'intro'"
                class="tab-detail"
            />
            <CityTransportTable
                v-else-if="!isPending && introCat === 'tTransport'"
                class="tab-detail"
            />
            <CityWeatherTable
                v-else-if="!isPending && introCat === 'tWeather'"
                class="tab-detail"
            />
          </el-tab-pane>
        </template>
      </el-tabs>
    </el-col>
  </el-row>
  <div v-if="!isPending && cityView && cityFood">
    <el-row>
      <el-col>
        <CardModeCarousel :data="cityFood" dir="food"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <CardModeCarousel :data="cityView" dir="view"/>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.tab-detail {
  margin: 0 20px;
}
</style>
