<script lang="ts" setup>
import { useMemberStore } from "@/stores/member";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import type { ScheduleSimpleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  loginId: {
    type: String,
    required: true,
  },
});
const { getMemberScheduleList } = useSchedule();
const scheduleList = ref<ScheduleSimpleCardType[]>();
const scheduleForm = onMounted(async () => {
  scheduleList.value = await getMemberScheduleList(props.loginId);
});
</script>
<el-form
  ref="ruleFormRef"
  :model="scheduleContentDetail"
  :rules="rules"
  label-position="top"
  label-width="120px"
  status-icon
>
<span class="resetBtn" size="small" @click="resetField"
><el-icon><Refresh/></el-icon>{{ $t("form.reset") }}
    </span>
<el-form-item :label="$t('schedule.form.content.title')" prop="title">
  <el-input v-model="scheduleContentDetail.title"/>
</el-form-item>
<el-form-item :label="$t('schedule.form.content.content')" prop="content">
  <el-input
      v-model="scheduleContentDetail.content"
      autosize
      type="textarea"
  />
</el-form-item>
<el-form-item :label="$t('schedule.form.content.cost')" prop="cost">
  <el-input v-model="scheduleContentDetail.cost">
    <template #append> 원</template>
  </el-input>
</el-form-item>
<el-form-item
    :label="$t('schedule.form.content.period.start')"
    prop="start"
>
  <v-date-picker
      v-model="scheduleContentDetail.start"
      class
      mode="time"
      timezone="Asia/Seoul"
  />
</el-form-item>
<el-form-item :label="$t('schedule.form.content.period.end')" prop="end">
  <v-date-picker
      v-model="scheduleContentDetail.end"
      mode="time"
      readonly
      timezone="Asia/Seoul"
  />
</el-form-item>
<el-form-item>
  <el-button
      color="#004A55"
      size="large"
      style="width: 100%"
      @click="submitForm(ruleFormRef)"
  >{{ $t("schedule.form.content.add") }}
  </el-button>
</el-form-item>
</el-form>
<template></template>
<style lang="scss" scoped>
.resetBtn {
  float: right;

  &:hover {
    font-size: 14px;
    cursor: pointer;
  }
}
</style>
