<script setup lang="ts">
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import koLocale from "@fullcalendar/core/locales/ko";
import listPlugin from "@fullcalendar/list";
import { reactive, watch } from "vue";
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
console.log(props.initDate);
const emit = defineEmits(["selectContent"]);
const { setContent, getEvents, createEvents } = useCalendarEvent();
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
  select: (arg) => {},
  dayMaxEvents: true,
  eventClick: (arg) => {
    props.manageState
      ? setContent(arg.event)
      : emit("selectContent", Number(arg.event.id));
  },
  eventAdd: (arg) => {
    createEvents({
      title: arg.event.title,
      start: arg.event.start,
      cost: arg.event.extendedProps.cost,
      content: arg.event.extendedProps.content,
    });
  },
  events: [],
});

options.events = getEvents.value;
watch(getEvents, () => {
  options.events = getEvents.value;
});
</script>

<template>
  <el-card>
    <FullCalendar :options="options" />
  </el-card>
</template>

<style scoped lang="scss">
.fc .fc-daygrid-body-balanced .fc-daygrid-day-events:hover {
  cursor: pointer !important;
}
</style>
