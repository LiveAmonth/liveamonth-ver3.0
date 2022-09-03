<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { reactive, ref, watch } from "vue";
import { useCity } from "@/composables/city";
import { useFormValidate } from "@/composables/formValidate";
import type { PropType } from "vue";
import type { FormRules, FormInstance } from "element-plus/es";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";
import { useSchedule } from "@/composables/schedule";

const props = defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
    required: true,
  },
});
const {} = useSchedule();
const { validateRequire } = useFormValidate();
const { cityNames } = useCity();

const isEdit = ref<boolean>(false);
const ruleFormRef = ref<FormInstance>();
const scheduleForm = reactive<ScheduleEditor>(
  new ScheduleEditor(props.schedule)
);

const rules = reactive<FormRules>({
  title: [validateRequire("common.title")],
});

watch(
  () => props.schedule,
  () => {
    scheduleForm.setForm(props.schedule);
  }
);

const updateSchedule = () => {
  console.log("업데이트!!!");
};
const cancelEdit = () => {
  scheduleForm.setForm(props.schedule);
  isEdit.value = false;
};
</script>

<template>
  <el-card>
    <el-form
      ref="ruleFormRef"
      :model="scheduleForm"
      :rules="rules"
      status-icon
      :disabled="!isEdit"
    >
      <div class="d-flex justify-content-between">
        <SmallTitleSlot>스케줄 정보</SmallTitleSlot>
        <el-form-item>
          <el-switch
            v-model="scheduleForm.publicFlag"
            class="ml-2"
            size="large"
            inline-prompt
            style="
              --el-switch-on-color: #016d7d;
              --el-switch-off-color: #535252;
            "
            :active-text="$t('common.open')"
            :inactive-text="$t('common.hide')"
          />
        </el-form-item>
      </div>
      <el-form-item :label="$t('common.title')">
        <el-input
          v-model="scheduleForm.title"
          :placeholder="
            $t('common.please-input', {
              field: $t('common.title'),
            })
          "
          style="width: 200px"
        >
        </el-input>
      </el-form-item>
      <el-form-item :label="$t('city.title')" prop="city">
        <el-select
          v-model="scheduleForm.city"
          :placeholder="$t('common.select')"
          style="width: 200px"
        >
          <template v-for="val in cityNames" :key="val.code">
            <el-option :label="$t(`city.name.${val.code}`)" :value="val.code" />
          </template>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('schedule.form.content.period.start')">
        <el-date-picker
          v-model="scheduleForm.period.startDate"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="date"
        />
      </el-form-item>
      <el-form-item :label="$t('schedule.form.content.period.end')">
        <el-date-picker
          v-model="scheduleForm.period.endDate"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="date"
        />
      </el-form-item>
    </el-form>
    <div class="d-flex justify-content-end">
      <el-button v-if="!isEdit" @click="isEdit = true"> 스케줄 수정</el-button>
      <template v-else>
        <el-button @click="updateSchedule"> 업데이트</el-button>
        <el-button @click="cancelEdit"> 취소</el-button>
      </template>
    </div>
  </el-card>
</template>

<style scoped lang="scss"></style>
