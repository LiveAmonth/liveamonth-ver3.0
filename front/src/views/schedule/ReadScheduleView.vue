<script setup lang="ts">
import { ref } from "vue";
import TitleSlot from "@/components/common/TitleSlot.vue";

const props = defineProps({
  scheduleId: {
    type: [Number, String],
    required: true,
  },
});

const currDate = ref(new Date());
const calendar = ref();
const dialogTableVisible = ref(false);

const selectDate = (val: string) => {
  calendar.value.selectDate(val);
};
const removeZero = (date: string) => {
  return Number.parseInt(date);
};
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot>{{ props.scheduleId }}번 스케줄</TitleSlot>
      <el-calendar v-model="currDate">
        <template #dateCell="{ data }">
          <el-row class="mb-2">
            <el-col>
              <span class="mb-5">
                {{ removeZero(data.day.split("-").slice(2).join("")) }}
              </span>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-button text @click="dialogTableVisible = true">
                스케줄 보기</el-button
              >
            </el-col>
          </el-row>
        </template>
      </el-calendar>
    </el-col>
  </el-row>
  <el-dialog v-model="dialogTableVisible" title="Shipping address">
    <el-table :data="gridData">
      <el-table-column property="date" label="Date" width="150" />
      <el-table-column property="name" label="Name" width="200" />
      <el-table-column property="address" label="Address" />
    </el-table>
  </el-dialog>
</template>

<style scoped lang="scss">
.el-calendar-day {
  padding: 0;
}
</style>
