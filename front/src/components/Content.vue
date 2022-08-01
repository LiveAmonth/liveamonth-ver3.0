<script lang="ts" setup></script>

<template>
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView />
      </el-col>
    </el-row>
  </el-main>
</template>

<style scoped>
@media screen and (min-width: 1200px) {
  .content {
    margin: 0 15%;
  }
}
</style>
