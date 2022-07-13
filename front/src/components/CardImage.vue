<script setup lang="ts">
import { defineProps } from "vue";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";

const props = defineProps({
  data: {
    type: Object as () => ImageContentType,
    required: true,
  },
});
</script>

<template>
  <el-card :body-style="{ padding: '0px' }">
    <img :src="`/src/assets/image/intro/${props.image}`" class="image" />
    <div style="padding: 14px">
      <span>${props.content}</span>
    </div>
  </el-card>
</template>

<style></style>
