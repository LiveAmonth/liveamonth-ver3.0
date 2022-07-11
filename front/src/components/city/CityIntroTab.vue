<script lang="ts" setup>
import { defineProps, onBeforeMount, onMounted, ref } from "vue";
import type { TabsPaneContext } from "element-plus";
import CityApiService from "@/services/CityApiService";
import CityIntroDetail from "@/components/city/CityIntroDetail.vue";
import type { EnumResponse } from "@/modules/types/common/EnumResponse";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const cityIntroCat = ref<EnumResponse[]>();
const activeName = ref();
const cityIntro = ref({});
onMounted(async () => {
  cityIntroCat.value = await CityApiService.getCityIntroCategory();
  cityIntro.value = await CityApiService.getTotalCityIntro(props.name);
  activeName.value = cityIntroCat.value?.[0].code;
});
</script>
<template v-if="cityIntroCat">
  <el-tabs
    v-model="activeName"
    tab-position="left"
    class="city-detail-tabs"
  >
    <template v-for="introCat in cityIntroCat" :key="introCat.code">
      <el-tab-pane :name="introCat.code" :label="introCat.value">
        <CityIntroDetail
          v-if="introCat.code === 'INTRO'"
          :content="cityIntro.content"
          :image="cityIntro.image"
        />
      </el-tab-pane>
    </template>
  </el-tabs>
</template>
