<script lang="ts" setup></script>

<template>
  <el-main class="content">
    <RouterView />
  </el-main>
</template>

<style lang="scss" scoped>
.content {
  height: calc(100vh - 250px);
}

@media screen and (min-width: 1200px) {
  .content {
    margin: 0 15%;
  }
}
</style>
