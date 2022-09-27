<script lang="ts" setup>
import ReviewMenu from "@/components/review/ReviewMenu.vue";
import ReviewSearch from "@/components/review/ReviewSearch.vue";

defineProps({
  menu: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <el-row :gutter="40" class="container">
    <el-col :span="4">
      <ReviewMenu :initial-menu="menu" />
    </el-col>
    <el-col :span="16" class="main-content">
      <el-row>
        <el-col :span="22">
          <ReviewSearch />
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <RouterView />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: start;

  .main-content {
    display: flex;
    flex-direction: column;
  }
}
</style>
