<script setup lang="ts">
import OpenModal from "@/components/common/OpenModal.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import ScheduleContentForm from "@/components/schedule/form/ScheduleContentForm.vue";
import { ref } from "vue";

const emits = defineEmits(["addSchedule", "addContent"]);

const scheduleModal = ref<boolean>(false);
const contentModal = ref<boolean>(false);
</script>

<template>
  <OpenModal @close="scheduleModal = false" v-if="scheduleModal">
    <ScheduleForm @submit="emits('addSchedule')">
      <template v-slot:title>
        {{ $t("schedule.form.main.add") }}
      </template>
    </ScheduleForm>
  </OpenModal>
  <OpenModal @close="contentModal = false" v-if="contentModal">
    <ScheduleContentForm @submit="emits('addContent')">
      <template v-slot:title>
        {{ $t("schedule.form.content.add") }}
      </template>
    </ScheduleContentForm>
  </OpenModal>
</template>

<style scoped lang="scss"></style>
