<script setup lang="ts">
import TitleSlot from "@/components/TitleSlot.vue";

const props = defineProps({
  scheduleId: {
    type: [Number, String],
    required: true,
  },
});
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot>내 스케줄 관리</TitleSlot>
      <span>
        여기는 나의 스케줄 관리 화면입니다. 스케줄 아이디 :
        {{ props.scheduleId }}
      </span>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
