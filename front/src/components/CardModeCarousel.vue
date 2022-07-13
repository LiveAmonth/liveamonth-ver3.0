<script setup lang="ts">
import { defineProps, ref } from "vue";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
const props = defineProps({
  data: {
    type: Array as () => ImageContentType[],
    required: true,
  },
  dir: {
    type: String,
    required: true,
  },
});

const carouselData = ref<ImageContentType[]>(props.data);
</script>

<template>
  <el-carousel :interval="2000" type="card" height="300px">
    <el-carousel-item v-for="item in carouselData" :key="item.content">
      <el-row>
        <el-col>
          <div style="box-shadow: var(--el-box-shadow)">
            <el-card :body-style="{ padding: '0px' }">
              <img
                :src="`/src/assets/image/${props.dir}/${item.image}`"
                class="image"
              />
              <div style="padding: 14px">
                <span>${{ item.content }}</span>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </el-carousel-item>
  </el-carousel>
</template>

<style scoped>
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
