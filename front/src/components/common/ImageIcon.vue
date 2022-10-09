<script lang="ts" setup>
defineProps({
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
  },
  disable: {
    type: Boolean,
    required: false,
    default: false,
  },
});
</script>

<template>
  <el-image
    :src="url"
    :style="{
      height: `${height}px`,
      width: `${width}px`,
      cursor: `${disable ? 'auto' : 'pointer'}`,
    }"
  />
</template>

<style lang="scss" scoped></style>
