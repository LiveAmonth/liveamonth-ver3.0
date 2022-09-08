<script setup lang="ts">
import type { ImageContentType } from "@/modules/types/common/ImageContentType";

defineProps({
  data: {
    type: Object as () => ImageContentType,
    required: true,
  },
});
</script>

<template>
  <el-card :body-style="{ padding: '0px' }">
    <img
      :src="`/src/assets/image/intro/${data.image}`"
      class="image"
      alt="image"
    />
    <div style="padding: 14px">
      <span>${props.content}</span>
    </div>
  </el-card>
</template>

<style></style>
