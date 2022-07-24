<script setup lang="ts">
import Content from "@/components/Content.vue";
</script>

<template>
  <Content />
</template>

<style lang="scss">
@forward "element-plus/theme-chalk/src/common/var.scss" with (
  $colors: (
    "primary": (
      "base": #004a55,
    ),
  )
);
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    sans-serif;
}
</style>
