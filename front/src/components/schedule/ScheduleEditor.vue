<script setup lang="ts">
import { ref, watch } from "vue";
import ScheduleContentForm from "@/components/form/ScheduleContentForm.vue";
import ScheduleForm from "@/components/schedule/ScheduleForm.vue";
import { useCalendarEvent } from "@/composables/calendarEvent";

defineProps({
  loginId: {
    type: String,
    required: true,
  },
});

</script>

<template>
  <el-collapse v-model="editorCollapse" class="search">
    <el-collapse-item :name="1">
      <template #title>
        <el-icon class="me-1">
          <Calendar />
        </el-icon>
        {{ $t("schedule.collapse.schedule") }}
      </template>
      <ScheduleForm :login-id="loginId" />
    </el-collapse-item>
    <el-collapse-item :name="2">
      <template #title>
        <el-icon class="me-1">
          <DocumentAdd />
        </el-icon>
        {{ $t("schedule.collapse.content") }}
      </template>
      <ScheduleContentForm />
    </el-collapse-item>
    <el-collapse-item :name="3">
      <template #title>
        <el-icon class="me-1">
          <Search />
        </el-icon>
        {{ $t("common.search") }}
      </template>
      <div class="d-flex justify-content-start"></div>
    </el-collapse-item>
  </el-collapse>
</template>

<style scoped lang="scss"></style>
