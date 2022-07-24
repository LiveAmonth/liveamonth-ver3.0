<script lang="ts" setup>
import { computed, onMounted, ref } from "vue";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import { useCityStore } from "@/stores/city";

const props = defineProps({
  dir: {
    type: String,
    required: true,
    validator: function (value) {
      return value === "food" || value === "view";
    },
  },
  name: {
    type: String,
    required: true,
  },
});

const store = useCityStore();
const carouselData = ref<ImageContentType[]>(
  props.dir === "food" ? store.foods : store.views
);
const dataSize = ref<number>(carouselData.value.length);
const carouselLen = computed<number>(() => Math.ceil(dataSize.value / 4));
const calcIdx = (idx: number, col: number) => idx * 4 + col - 5;

onMounted(async () => {
  await store.setCity(props.name);
  console.log(props.dir + "슬라이드 : ", props.name);
  carouselData.value = props.dir === "food" ? store.foods : store.views;
});
</script>

<template>
  <el-carousel direction="vertical" height="350px">
    <el-carousel-item v-for="idx in carouselLen" :key="idx">
      <el-row :gutter="5">
        <el-col v-for="col in 4" :key="col" :span="6">
          <el-card v-if="calcIdx(idx, col) < dataSize">
            <img
              :src="`/src/assets/image/${props.dir}/${
                carouselData[calcIdx(idx, col)].image
              }`"
              class="image"
            />
            <div class="content mt-3">
              <span>{{ carouselData[calcIdx(idx, col)].content }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-carousel-item>
  </el-carousel>
</template>

<style lang="scss" scoped>
.image {
  width: 100%;
  display: block;
  box-shadow: var(--el-box-shadow);
}

.content {
  text-align: end;

  > span {
    font-size: var(--el-font-size-large);
  }
}
</style>
