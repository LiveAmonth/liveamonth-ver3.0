<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { onMounted, reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import { ScheduleEditor } from "@/modules/types/schedule/ScheduleTypes";
import { useCategory } from "@/composables/common/category";
import type { FormInstance } from "element-plus/es";
import { useMember } from "@/composables/member/member";

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
const { simpleProfile } = useMember();
const { cityNames } = useCategory();
const {
  labelMsg,
  resultMsg,
  buttonMsg,
  inputPhMsg,
  selectPhMsg,
  categoryMsg,
  placeholderMsg,
  openMessage,
  openMessageBox,
} = useMessageBox();

const isEdit = ref<boolean>(props.isAddForm);
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
        await addSchedule(simpleProfile.value.loginId, form).then(() => {
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
      <el-row class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot>
          <slot name="title"></slot>
        </SmallTitleSlot>
        <el-switch
          v-model="form.publicFlag"
          :active-text="buttonMsg('schedule.open')"
          :inactive-text="buttonMsg('schedule.hide')"
          class="mb-2"
          inline-prompt
          size="large"
          style="--el-switch-on-color: #016d7d; --el-switch-off-color: #535252"
        />
      </el-row>
      <el-form-item :label="labelMsg('title')" prop="title">
        <el-input
          v-model="form.title"
          :placeholder="inputPhMsg(labelMsg('title'))"
          style="width: 200px"
        >
        </el-input>
      </el-form-item>
      <el-form-item :label="labelMsg('city.title')" prop="city">
        <el-select
          v-model="form.city"
          :placeholder="selectPhMsg(labelMsg('city.title'))"
          style="width: 200px"
        >
          <template v-for="val in cityNames" :key="val.code">
            <el-option
              :label="categoryMsg('city.name', val.code)"
              :value="val.code"
            />
          </template>
        </el-select>
      </el-form-item>
      <el-form-item
        :label="labelMsg('schedule.start')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="form.period.startDate"
          :placeholder="placeholderMsg('pick-day')"
          style="width: 200px"
          type="date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item
        :label="labelMsg('schedule.end')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="form.period.endDate"
          :placeholder="placeholderMsg('pick-day')"
          style="width: 200px"
          type="date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
    </el-form>
    <div v-if="selectedId" class="d-flex justify-content-end">
      <template v-if="!isEdit">
        <el-button @click="isEdit = true">
          {{ buttonMsg("edit") }}
        </el-button>
        <el-button @click="emits('deleteSchedule')">
          {{ buttonMsg("delete") }}
        </el-button>
      </template>
      <template v-else>
        <el-button @click="submitForm(ruleFormRef)">
          {{ buttonMsg("update") }}
        </el-button>
        <el-button @click="cancelEdit">
          {{ buttonMsg("cancel") }}
        </el-button>
      </template>
    </div>
    <div v-else class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)">
        {{ buttonMsg("add") }}
      </el-button>
      <el-button @click="form.clear()">
        {{ buttonMsg("reset") }}
      </el-button>
    </div>
  </el-card>
  <el-card v-else-if="!isAddForm">
    {{ resultMsg("schedule.empty.schedule") }}
  </el-card>
</template>

<style lang="scss" scoped>
.el-collapse {
  border: none;
}
</style>
