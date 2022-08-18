<script setup lang="ts">
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import koLocale from "@fullcalendar/core/locales/ko";
import listPlugin from "@fullcalendar/list";
import { useScheduleStore } from "@/stores/schedule";
import { computed, reactive, ref, watch } from "vue";
import type { CalendarOptions } from "@fullcalendar/core";
import { useCalendarEvent } from "@/composables/calendarEvent";

const props = defineProps({
  manageState: {
    type: Boolean,
    required: true,
  },
  editable: {
    type: Boolean,
    required: true,
  },
});
const store = useScheduleStore();
const { setContent, getEvents, createEvents } = useCalendarEvent();
const options: CalendarOptions = reactive({
  locale: koLocale,
  plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
  initialView: "dayGridMonth",
  headerToolbar: {
    left: "prev,next today",
    center: "title",
    right: "dayGridMonth,listYear",
  },
  editable: props.editable,
  selectable: true,
  weekends: true,
  select: (arg) => {
    // if (eventsCount === 1) {
    //   console.log("컨텐츠 개수가 1개 입니다.");
    // }
  },
  dayMaxEvents: true,
  eventClick: (arg) => {
    setContent(props.manageState, arg.event);
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
  <FullCalendar :options="options" />
</template>

<style scoped lang="scss">
.fc .fc-daygrid-body-balanced .fc-daygrid-day-events:hover {
  cursor: pointer !important;
}
</style>
