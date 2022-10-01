<script setup lang="ts">
import OpenModal from "@/components/common/OpenModal.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import AddContentForm from "@/components/schedule/form/AddContentForm.vue";
import type { PropType } from "vue";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleTypes";

defineProps({
  scheduleId: Number,
  scheduleModal: Boolean,
  contentModal: Boolean,
  period: {
    type: Object as PropType<DatePeriodType>,
    required: true,
  },
  startDate: {
    type: String,
    required: true,
  },
});

const emits = defineEmits([
  "update:scheduleModal",
  "update:contentModal",
  "addSchedule",
  "addContent",
]);

const addSchedule = () => {
  emits("update:scheduleModal", false);
  emits("addSchedule");
};

const addContent = () => {
  emits("update:contentModal", false);
  emits("addContent");
};
</script>

<template>
  <OpenModal @close="$emit('update:scheduleModal', false)" v-if="scheduleModal">
    <ScheduleForm @submit="addSchedule">
      <template v-slot:title>
        {{ $t("schedule.form.main.add") }}
      </template>
    </ScheduleForm>
  </OpenModal>
  <OpenModal @close="$emit('update:contentModal', false)" v-if="contentModal">
    <AddContentForm
      :schedule-id="scheduleId"
      :period="period"
      :start-date="period.startDate"
      @submit="addContent"
    />
  </OpenModal>
</template>

<style scoped lang="scss"></style>
