<script setup lang="ts">
import TitleSlot from "@/components/TitleSlot.vue";
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot>스케줄 목록</TitleSlot>
      <div>
        <span> 여기는 스케줄 리스트 화면 입니다.</span>
      </div>
      <router-link :to="{ name: 'read-schedule', params: { scheduleId: 1 } }">
        1번 스케줄 바로가기
      </router-link>
      <el-icon><View /></el-icon> 22
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.el-icon {
  top: 3%;
}
</style>
