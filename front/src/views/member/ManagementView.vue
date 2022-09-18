<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";
defineProps({
  menu: {
    type: String,
    required: true,
  },
});
</script>
<template>
  <TitleSlot class="mb-3"> 내 정보</TitleSlot>
  <el-card :body-style="{ padding: 0 }">
    <el-row :gutter="5">
      <el-col :span="4">
        <ManagementMenu class="pt-3" :initial-menu="menu" />
      </el-col>
      <el-col :span="20" class="content"> </el-col>
    </el-row>
  </el-card>
</template>
<style scoped lang="scss">
.el-menu-item {
  &:hover {
    background-color: #6c99a3d3;
  }
}
</style>
