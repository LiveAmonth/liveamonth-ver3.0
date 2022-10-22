<script lang="ts" setup>
import ScheduleSearchInput from "@/components/schedule/form/ScheduleSearchInput.vue";
import { Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useDate } from "@/composables/common/date";
import { useCategory } from "@/composables/common/category";
import { useMessageBox } from "@/composables/common/messageBox";
import { usePagination } from "@/composables/common/pagination";
import { useSchedule } from "@/composables/schedule/schedule";
import type { ScheduleSearchFormType } from "@/modules/types/schedule/ScheduleTypes";

const emits = defineEmits(["applyOption"]);

const { type, request } = useSchedule();
const { pageable, setSort } = usePagination(type.toUpperCase());
const { scheduleSortType, scheduleFilterType } = useCategory();
const { categoryMsg, buttonMsg } = useMessageBox();
const { getDate } = useDate();

const searchCollapse = ref(0);
const order = ref<string>(pageable.value.sort);

const submitForm = async (form: ScheduleSearchFormType) => {
  if (form.filterType === scheduleFilterType.value[1].code) {
    if (form.filterInput) {
      form.filterInput = getDate(form.filterInput);
    }
  }
  request.value.setAttr(form);
  emits("applyOption");
};

const changeSort = async () => {
  await setSort(order.value);
  await emits("applyOption");
};
</script>
<template>
  <el-collapse v-model="searchCollapse" class="search me-3" accordion>
    <el-collapse-item name="1">
      <template #title>
        <span class="ms-3 d-flex justify-content-start align-items-center">
          <el-icon class="me-1">
            <Search />
          </el-icon>
          {{ buttonMsg("schedule.search") }}
        </span>
      </template>
      <ScheduleSearchInput class="mt-3" @apply-option="submitForm" />
    </el-collapse-item>
  </el-collapse>
  <div class="sort-write-content">
    <el-radio-group
      class="order-radio"
      v-model="order"
      :text-color="'#0f6778'"
      :fill="'#0f6778'"
      @change="changeSort"
    >
      <el-radio
        v-for="order in scheduleSortType"
        :key="order.code"
        :label="order.title"
      >
        {{ categoryMsg("schedule.sort", order.code) }}
      </el-radio>
    </el-radio-group>
  </div>
  <el-divider class="mt-0 mb-2" />
</template>

<style lang="scss">
.sort-write-content {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;

  .order-radio {
    .el-radio__input.is-checked {
      & + .el-radio__label {
        color: #006778;
      }

      & .el-radio__inner {
        background-color: #006778;
        border-color: #006778;
      }
    }
  }

  .write-btn {
    margin-bottom: 5px;
    margin-right: 5px;
  }
}
</style>
