<script lang="ts" setup>
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import koLocale from "@fullcalendar/core/locales/ko";
import listPlugin from "@fullcalendar/list";
import { onMounted, reactive, watch } from "vue";
import { useCalendarEvent } from "@/composables/calendarEvent";
import type { CalendarOptions } from "@fullcalendar/core";

const props = defineProps({
  manageState: {
    type: Boolean,
    required: true,
  },
  editable: {
    type: Boolean,
    required: true,
  },
  initDate: {
    type: String,
    required: false,
  },
});
const emit = defineEmits(["selectContent"]);
const { scheduleContents, setContent, getEvents, createEvents } =
  useCalendarEvent();

const options: CalendarOptions = reactive({
  locale: koLocale,
  plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
  initialView: "dayGridMonth",
  initialDate: props.initDate,
  headerToolbar: {
    left: "prev,next today",
    center: "title",
    right: "dayGridMonth,listYear",
  },
  editable: props.editable,
  selectable: true,
  weekends: true,
  dayMaxEvents: true,
  eventClick: (arg) => {
    props.manageState
      ? setContent(arg.event)
      : emit("selectContent", Number(arg.event.id));
  },
  eventAdd: (arg) => {
    createEvents(arg.event);
  },
  events: [],
});
onMounted(() => {
  options.events = getEvents();
});

watch(
  () => scheduleContents.value,
  () => {
    options.events = getEvents();
  }
);
</script>

<template>
  <el-card>
    <FullCalendar :options="options" />
  </el-card>
</template>

<style lang="scss" scoped>
.fc .fc-daygrid-body-balanced .fc-daygrid-day-events:hover {
  cursor: pointer !important;
}
</style>
