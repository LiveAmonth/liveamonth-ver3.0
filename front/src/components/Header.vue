<script setup lang="ts">
import LogoIcon from "../components/image/LogoIcon.vue";
</script>

<template>
  <el-header class="header">
    <el-menu mode="horizontal" :ellipsis="false" router>
      <el-menu-item index="0">
        <LogoIcon />
      </el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write">글 작성</el-menu-item>
    </el-menu>
  </el-header>
</template>

<style scoped>
.header {
  background: #383838;
  padding: 0;
  height: 60px;
}
</style>
