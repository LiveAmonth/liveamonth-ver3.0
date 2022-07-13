<script lang="ts" setup>
import { defineProps, onMounted, ref } from "vue";
import { useCity } from "@/composables/city";
import { useCityStore } from "@/stores/city";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import CityIntroDetail from "@/components/city/CityIntroDetail.vue";
import CityTransportTable from "@/components/city/CityTransportTable.vue";
import CityWeatherTable from "@/components/city/CityWeatherTable.vue";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const store = useCityStore();

const { isPending, getTotalCityIntro } = useCity();

const activeName = ref(store.cityCategory[0]);
const cityIntro = ref<ImageContentType>();

onMounted(async () => {
  await getTotalCityIntro(props.name);
  cityIntro.value = store.introDetail;
});
</script>

<template>
  <el-row>
    <el-col>
      <el-tabs
        v-model="activeName"
        class="city-detail-tabs"
        tab-position="left"
      >
        <template v-for="introCat in store.cityCategory" :key="introCat">
          <el-tab-pane
            :label="$t(`city.tab.category.${introCat}`)"
            :name="introCat"
          >
            <template v-if="!isPending">
              <CityIntroDetail v-if="introCat === 'intro'" class="tab-detail" />
              <CityTransportTable
                v-else-if="introCat === 'transport'"
                class="tab-detail"
              />
              <CityWeatherTable
                v-else-if="introCat === 'weather'"
                class="tab-detail"
              />
            </template>
          </el-tab-pane>
        </template>
      </el-tabs>
    </el-col>
  </el-row>
</template>

<style scoped>
.tab-detail {
  margin: 0 20px;
}
</style>
