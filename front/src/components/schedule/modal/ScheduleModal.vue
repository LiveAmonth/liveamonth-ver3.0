<script setup lang="ts">
import OpenModal from "@/components/common/OpenModal.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import ScheduleContentForm from "@/components/schedule/form/ScheduleContentForm.vue";
import type { PropType } from "vue";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

defineProps({
  scheduleId: Number,
  scheduleModal: Boolean,
  contentModal: Boolean,
  period: {
    type: Object as PropType<DatePeriodType>,
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
    <ScheduleContentForm
      :schedule-id="scheduleId"
      :period="period"
      :is-addition="true"
      @submit="addContent"
    >
      <template v-slot:title>
        {{ $t("schedule.form.content.add") }}
      </template>
    </ScheduleContentForm>
  </OpenModal>
</template>

<style scoped lang="scss"></style>
