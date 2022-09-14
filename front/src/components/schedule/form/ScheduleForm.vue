<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";
import { onMounted, reactive, ref, watch } from "vue";
import { useCity } from "@/composables/city";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import { useMember } from "@/composables/member";
import type { PropType } from "vue";
import type { FormInstance } from "element-plus/es";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType> | null,
    required: false,
    default: null,
  },
});

const emits = defineEmits(["submit", "deleteSchedule"]);
const { addSchedule, editSchedule } = useSchedule();
const { cityNames } = useCity();
const { simpleProfile } = useMember();
const { openMessage, openMessageBox } = useMessageBox();
const { t } = useI18n();

const isEdit = ref<boolean>(!props.schedule);
const scheduleForm = reactive<ScheduleEditor>(new ScheduleEditor());
const ruleFormRef = ref<FormInstance>();

onMounted(() => {
  if (props.schedule) {
    scheduleForm.setForm(props.schedule);
  }
});

watch(
  () => props.schedule,
  () => {
    scheduleForm.setForm(props.schedule);
  }
);

const cancelEdit = () => {
  scheduleForm.setForm(props.schedule);
  isEdit.value = false;
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      if (props.schedule) {
        await editSchedule(props.schedule.id, scheduleForm).then(() => {
          isEdit.value = false;
          openMessage(t("form.message.schedule.update"));
          emits("submit", true);
        });
      } else {
        await addSchedule(simpleProfile.value.id, scheduleForm).then(() => {
          openMessage(t("form.message.schedule.add"));
          emits("submit");
        });
      }
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <el-card v-if="props.schedule">
    <el-form
      ref="ruleFormRef"
      :disabled="!isEdit"
      :model="scheduleForm"
      :rules="scheduleForm.getRules()"
      label-width="75px"
      status-icon
    >
      <div class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot>
          <slot name="title"></slot>
        </SmallTitleSlot>
        <el-form-item>
          <el-switch
            v-model="scheduleForm.publicFlag"
            :active-text="$t('common.open')"
            :inactive-text="$t('common.hide')"
            class="ml-2"
            inline-prompt
            size="large"
            style="
              --el-switch-on-color: #016d7d;
              --el-switch-off-color: #535252;
            "
          />
        </el-form-item>
      </div>
      <el-form-item :label="$t('common.title')" prop="title">
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
      <el-form-item
        :label="$t('schedule.form.main.period.start')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="scheduleForm.period.startDate"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item
        :label="$t('schedule.form.main.period.end')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="scheduleForm.period.endDate"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
    </el-form>
    <div v-if="schedule" class="d-flex justify-content-end">
      <template v-if="!isEdit">
        <el-button @click="isEdit = true"> 수정</el-button>
        <el-button @click="emits('deleteSchedule')"> 삭제 </el-button>
      </template>
      <template v-else>
        <el-button @click="submitForm(ruleFormRef)"> 업데이트</el-button>
        <el-button @click="cancelEdit"> 취소</el-button>
      </template>
    </div>
    <div v-else class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)"> 추가</el-button>
      <el-button @click="scheduleForm.clear()"> 초기화</el-button>
    </div>
  </el-card>
  <el-card v-else> 등록된 스케줄이 없습니다. </el-card>
</template>

<style lang="scss" scoped>
.el-collapse {
  border: none;
}
</style>
