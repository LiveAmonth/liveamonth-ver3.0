<template>
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView/>
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup lang="ts">
import {RouterView} from "vue-router";
</script>

<style scoped>
@media screen and (min-width: 1200px) {
  .content {
    margin: 0 15%;
  }
}
.content {
  height: calc(100vh - 60px);
}
</style>
