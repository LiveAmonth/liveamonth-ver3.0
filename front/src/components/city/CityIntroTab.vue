<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useCityStore } from "@/stores/city";
import { useCity } from "@/composables/city";
import CityIntroDetail from "@/components/city/CityIntroDetail.vue";
import CityTransportTable from "@/components/city/CityTransportTable.vue";
import CityWeatherTable from "@/components/city/CityWeatherTable.vue";
import CardModeCarousel from "@/components/CardModeCarousel.vue";
import TitleSlot from "@/components/TitleSlot.vue";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const store = useCityStore();

const { getCityIntro, getExtraCityInfo } = useCity();
const loading = ref(true);
const activeName = ref<string>("intro");

onMounted(async () => {
  if (!store.cityIntro) {
    await getCityIntro(props.name);
    await getExtraCityInfo(props.name);
  }
  store.setCity(props.name);
  console.log("인트로 탭 : ", props.name);
  loading.value = false;
});
</script>

<template>
  <div v-if="loading">로딩중</div>
  <div v-else>
    <el-tabs v-model="activeName" class="city-detail-tabs" tab-position="left">
      <el-tab-pane :label="$t('city.tab.category.intro')" name="intro">
        <CityIntroDetail :name="props.name" class="tab-detail" />
      </el-tab-pane>
      <el-tab-pane :label="$t('city.tab.category.transport')" name="transport">
        <CityTransportTable :name="props.name" class="tab-detail" />
      </el-tab-pane>
      <el-tab-pane :label="$t('city.tab.category.weather')" name="weather">
        <CityWeatherTable :name="props.name" class="tab-detail" />
      </el-tab-pane>
    </el-tabs>
    <div class="mt-5">
      <el-row v-for="cat in ['food', 'view']" :key="cat">
        <el-col>
          <TitleSlot>{{ $t(`city.intro.category.${cat}`) }}</TitleSlot>
          <CardModeCarousel :name="props.name" :dir="cat" />
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
.tab-detail {
  margin: 0 20px;
}
.title {
  color: #111111;
}
</style>
