<script lang="ts" setup>
import CardModeCarousel from "@/components/common/CardModeCarousel.vue";
import CityIntroTab from "@/components/city/CityIntroTab.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { onMounted, ref } from "vue";
import { useCity } from "@/composables/city/city";
import { useCategory } from "@/composables/common/category";
import { useMessageBox } from "@/composables/common/messageBox";

const { cityNames, hasCityNames, getCityNames } = useCategory();
const { getCityIntro, getExtraCityInfo } = useCity();
const { titleMsg, categoryMsg } = useMessageBox();

const activeName = ref<string>();

onMounted(async () => {
  await getCityNames().then(() => {
    activeName.value = cityNames.value[0]?.code;
    getCityIntro(activeName.value);
    getExtraCityInfo(activeName.value);
  });
});

const cityChange = async () => {
  if (activeName.value != null) {
    await getCityIntro(activeName.value);
    await getExtraCityInfo(activeName.value);
  }
};
</script>

<template>
  <el-row class="mb-lg-2">
    <el-col>
      <div class="d-flex justify-content-between">
        <TitleSlot :title="titleMsg('city.intro')" />
        <el-radio-group
          v-if="hasCityNames"
          v-model="activeName"
          :fill="'#0f6778'"
          size="large"
        >
          <template v-for="name in cityNames" :key="name.code">
            <el-radio-button :label="name.code" @change="cityChange">
              {{ name.value }}
            </el-radio-button>
          </template>
        </el-radio-group>
      </div>
      <div class="mt-3 px-0">
        <CityIntroTab />
        <div class="mt-5">
          <el-row v-for="cat in ['food', 'view']" :key="cat">
            <el-col>
              <TitleSlot :title="categoryMsg('city.intro', cat)" />
              <CardModeCarousel :dir="cat" />
            </el-col>
          </el-row>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss">
.city-intro-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
