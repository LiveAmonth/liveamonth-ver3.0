<script lang="ts" setup>
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import koLocale from "@fullcalendar/core/locales/ko";
import listPlugin from "@fullcalendar/list";
import { onMounted, reactive, watch } from "vue";
import { useCalendarEvent } from "@/composables/schedule/calendarEvent";
import { useDate } from "@/composables/common/date";
import { useMessageBox } from "@/composables/common/messageBox";
import type { PropType } from "vue";
import type { CalendarOptions } from "@fullcalendar/core";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  editable: {
    type: Boolean,
    required: false,
    default: false,
  },
  initPeriod: {
    type: Object as PropType<DatePeriodType>,
    required: true,
  },
  isBasic: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["selectContent", "addContent"]);
const { scheduleContents, setContent, getEvents } = useCalendarEvent();
const { now, isSameDate } = useDate();
const { resultMsg, openConfirmMessageBox } = useMessageBox();

const options: CalendarOptions = reactive({
  locale: koLocale,
  plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
  initialView: "dayGridMonth",
  initialDate: !props.isBasic ? props.initPeriod.startDate : now(),
  headerToolbar: {
    left: "prev,next today",
    center: "title",
    right: "dayGridMonth,listYear",
  },
  editable: false,
  selectable: !props.isBasic ? props.editable : false,
  weekends: true,
  dayMaxEvents: true,
  select: async (arg) => {
    let hasEvents = false;
    arg.view.calendar.getEvents().forEach((value) => {
      if (value.start && isSameDate(arg.start, value.start)) {
        hasEvents = true;
      }
    });
    await openConfirmMessageBox(
      resultMsg("content.new.title"),
      resultMsg(`content.new.${hasEvents ? "message" : "empty"}`)
    ).then(() => {
      emits("addContent", arg.startStr, true);
    });
  },
  eventClick: (arg) => {
    props.editable
      ? setContent(arg.event)
      : emits("selectContent", Number(arg.event.id));
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
