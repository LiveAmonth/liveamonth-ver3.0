<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import { useMessageBox } from "@/composables/messageBox";

const { titleMsg } = useMessageBox();
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot class="mb-4">{{ titleMsg("review.main") }}</TitleSlot>
      <RouterView />
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
