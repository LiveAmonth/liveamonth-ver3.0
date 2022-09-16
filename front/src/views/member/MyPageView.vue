<script setup lang="ts">
import { useMember } from "@/composables/member";

const { simpleProfile } = useMember();
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
<template>
  <el-row class="mt-0" :gutter="5">
    <el-col :span="4">
      <el-menu
        active-text-color="#016D7D"
        background-color="#ffffff"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#111111"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-menu-item index="1">
          <el-icon><icon-menu /></el-icon>
          <span>Navigator Two</span>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon><document /></el-icon>
          <span>Navigator Three</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><setting /></el-icon>
          <span>Navigator Four</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="20" class="content"> asd</el-col>
  </el-row>
</template>
<style scoped lang="scss">
.el-menu-item:hover {
  background-color: #5398a5d3;
}
</style>
