<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { onMounted, reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import { ScheduleEditor } from "@/modules/types/schedule/ScheduleTypes";
import { useCategory } from "@/composables/common/category";
import type { FormInstance } from "element-plus/es";

const props = defineProps({
  selectedId: {
    type: Number,
    required: false,
    default: 0,
  },
  isAddForm: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const emits = defineEmits(["submit", "deleteSchedule"]);
const { editedSchedule, addSchedule, editSchedule } = useSchedule();
const { cityNames } = useCategory();
const { openMessage, openMessageBox, labelMsg, resultMsg, buttonMsg } =
  useMessageBox();

const isEdit = ref<boolean>(!props.selectedId);
const form = reactive<ScheduleEditor>(new ScheduleEditor());
const ruleFormRef = ref<FormInstance>();

onMounted(() => {
  if (props.selectedId) {
    form.setForm(editedSchedule.value);
  }
});

watch(
  () => editedSchedule.value,
  () => {
    form.setForm(editedSchedule.value);
  }
);

const cancelEdit = () => {
  form.setForm(editedSchedule.value);
  isEdit.value = false;
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      if (props.selectedId) {
        await editSchedule(props.selectedId, form).then(() => {
          isEdit.value = false;
          openMessage(resultMsg("schedule.update"));
          emits("submit", true);
        });
      } else {
        await addSchedule(form).then(() => {
          openMessage(resultMsg("schedule.add"));
          emits("submit");
        });
      }
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>

<template>
  <el-card v-if="selectedId || (!selectedId && isAddForm)">
    <el-form
      ref="ruleFormRef"
      :disabled="!isEdit"
      :model="form"
      :rules="form.getRules()"
      label-width="75px"
      status-icon
    >
      <div class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot>
          <slot name="title"></slot>
        </SmallTitleSlot>
        <el-form-item>
          <el-switch
            v-model="form.publicFlag"
            :active-text="buttonMsg('schedule.open')"
            :inactive-text="buttonMsg('schedule.hide')"
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
      <el-form-item :label="labelMsg('title')" prop="title">
        <el-input
          v-model="form.title"
          :placeholder="
            $t('common.please-input', {
              field: labelMsg('title'),
            })
          "
          style="width: 200px"
        >
        </el-input>
      </el-form-item>
      <el-form-item :label="labelMsg('city')" prop="city">
        <el-select
          v-model="form.city"
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
          v-model="form.period.startDate"
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
          v-model="form.period.endDate"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
    </el-form>
    <div v-if="selectedId" class="d-flex justify-content-end">
      <template v-if="!isEdit">
        <el-button @click="isEdit = true">
          {{ $t("common.button.edit") }}
        </el-button>
        <el-button @click="emits('deleteSchedule')">
          {{ $t("common.button.delete") }}
        </el-button>
      </template>
      <template v-else>
        <el-button @click="submitForm(ruleFormRef)">
          {{ $t("common.button.update") }}
        </el-button>
        <el-button @click="cancelEdit">
          {{ $t("common.button.cancel") }}
        </el-button>
      </template>
    </div>
    <div v-else class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)">
        {{ $t("common.button.add") }}
      </el-button>
      <el-button @click="form.clear()">
        {{ $t("common.button.clear") }}
      </el-button>
    </div>
  </el-card>
  <el-card v-else-if="!isAddForm">
    {{ $t("schedule.form.empty.schedule") }}
  </el-card>
</template>

<style lang="scss" scoped>
.el-collapse {
  border: none;
}
</style>
