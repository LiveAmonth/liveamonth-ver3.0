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
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
import type { CalendarOptions } from "@fullcalendar/core";
import { useCalendarEvent } from "@/composables/calendarEvent";

const store = useScheduleStore();
const { getEvents, createEvents } = useCalendarEvent();
const scheduleContents = computed(
  (): ScheduleContentType[] => store.scheduleContents
);
const id = ref(10);
const options: CalendarOptions = reactive({
  locale: koLocale,
  plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
  initialView: "dayGridMonth",
  headerToolbar: {
    left: "prev,next today",
    center: "title",
    right: "dayGridMonth,listDay",
  },
  editable: true,
  selectable: true,
  weekends: true,
  select: (arg) => {
    console.log(arg);
    id.value = id.value + 1;
    const cal = arg.view.calendar;
    cal.unselect();
    cal.addEvent({
      id: `${id.value}`,
      title: `New event ${id.value}`,
      start: arg.start,
      end: arg.end,
      allDay: true,
    });
  },
  dayMaxEvents: true,
  eventClick: (arg) => {
    console.log(arg.event);
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

<style scoped lang="scss"></style>
