<script setup lang="ts">
import { ref, defineProps } from "vue";
import TitleSlot from "@/components/TitleSlot.vue";

const props = defineProps({
  scheduleId: {
    type: [Number, String],
    required: true,
  },
});

const currDate = ref(new Date());
const calendar = ref();
const selectDate = (val: string) => {
  calendar.value.selectDate(val);
};
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot>{{ props.scheduleId }}번 스케줄</TitleSlot>
      <el-calendar v-model="currDate">
        <!--        <template #header="{ date }">-->
        <!--          <span>{{ props.scheduleId }}님의 스케줄</span>-->
        <!--          <span>{{ date }}</span>-->
        <!--          <el-button-group>-->
        <!--            <el-button size="small" @click="selectDate('2020-02-02')"-->
        <!--              >작년</el-button-->
        <!--            >-->
        <!--            <el-button size="small" @click="selectDate('prev-month')"-->
        <!--              >Previous Month</el-button-->
        <!--            >-->
        <!--            <el-button size="small" @click="selectDate('today')"-->
        <!--              >Today</el-button-->
        <!--            >-->
        <!--            <el-button size="small" @click="selectDate('next-month')"-->
        <!--              >Next Month</el-button-->
        <!--            >-->
        <!--            <el-button size="small" @click="selectDate('next-year')"-->
        <!--              >Next Year</el-button-->
        <!--            >-->
        <!--          </el-button-group>-->
        <!--        </template>-->
        <template #dateCell="{ data }">
          <p :class="data.isSelected ? 'is-selected' : ''">
            {{ data.day.split("-").slice(2).join("") }}
            {{ data.isSelected ? "✔️" : "" }}
          </p>
        </template>
      </el-calendar>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
