<script lang="ts" setup>
import CardModeCarousel from "@/components/common/CardModeCarousel.vue";
import CityIntroTab from "@/components/city/CityIntroTab.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { onMounted, ref } from "vue";
import { useCity } from "@/composables/city/city";

const {
  cityNames,
  hasCityNames,
  getCityNames,
  getCityIntro,
  getExtraCityInfo,
} = useCity();

const activeName = ref<string>();
const loading = ref<boolean>(true);

onMounted(async () => {
  if (!hasCityNames.value) {
    await getCityNames();
  }
  activeName.value = cityNames.value[0]?.code;
  await getCityIntro(activeName.value);
  await getExtraCityInfo(activeName.value);
  loading.value = false;
});

const cityChange = async () => {
  if (activeName.value != null) {
    await getCityIntro(activeName.value);
    await getExtraCityInfo(activeName.value);
  }
};
</script>

<template>
  <div v-if="loading">로딩중</div>
  <div v-else>
    <el-row class="mb-lg-2">
      <el-col>
        <div class="d-flex justify-content-between">
          <TitleSlot>{{ $t("city.intro.title") }}</TitleSlot>
          <el-radio-group v-model="activeName" fill="#004a55" size="large">
            <template v-for="name in cityNames" :key="name.code">
              <el-radio-button :label="name.code" @change="cityChange"
                >{{ name.value }}
              </el-radio-button>
            </template>
          </el-radio-group>
        </div>
        <div class="mt-3 px-0">
          <CityIntroTab />
          <div class="mt-5">
            <el-row v-for="cat in ['food', 'view']" :key="cat">
              <el-col>
                <TitleSlot>{{ $t(`city.intro.category.${cat}`) }}</TitleSlot>
                <CardModeCarousel :dir="cat" />
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss">
.city-intro-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
