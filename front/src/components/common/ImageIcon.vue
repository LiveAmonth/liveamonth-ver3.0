<script setup lang="ts">
import { watch } from "vue";

const props = defineProps({
  width: {
    type: Number,
    default: 25,
  },
  height: {
    type: Number,
    default: 25,
  },
  url: {
    type: String,
    required: true,
  }
});
</script>

<template>
  <el-image
    :src="url"
    :style="{ height: `${height}px`, width: `${width}px` }"
  />
</template>

<style scoped lang="scss">
.el-image {
  cursor: pointer;
}
</style>
