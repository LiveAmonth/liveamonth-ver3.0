<script setup lang="ts">
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
import { ElConfigProvider } from "element-plus";
import koKR from "element-plus/lib/locale/lang/ko";

const local = koKR;
</script>

<template>
  <el-config-provider :locale="local">
    <Header />
    <Content />
  </el-config-provider>
</template>

<style>
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    sans-serif;
}
</style>
