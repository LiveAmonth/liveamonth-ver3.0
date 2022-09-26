<script lang="ts" setup>
import { useMessageBox } from "@/composables/common/messageBox";
import ReviewMenu from "@/components/review/ReviewMenu.vue";

const { titleMsg } = useMessageBox();
</script>

<template>
  <el-row class="container">
    <el-col :span="4">
      <ReviewMenu />
    </el-col>
    <el-col :span="18" class="main-content">
      <el-row>
        <el-col>
          <el-menu></el-menu>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <RouterView />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: space-between;

  .main-content {
    display: flex;
    flex-direction: column;
  }
}
</style>
