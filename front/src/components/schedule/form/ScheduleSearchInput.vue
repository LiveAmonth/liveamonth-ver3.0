<script lang="ts" setup>
import { Search, Refresh, Filter } from "@element-plus/icons-vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { reactive } from "vue";
import { useCategory } from "@/composables/common/category";
import type { ScheduleSearchFormType } from "@/modules/types/schedule/ScheduleTypes";

const emits = defineEmits(["applyOption"]);

const { scheduleFilterType, scheduleSearchType, cityNames } = useCategory();
const { buttonMsg, labelMsg, categoryMsg, placeholderMsg, inputPhMsg } =
  useMessageBox();

const form = reactive<ScheduleSearchFormType>({
  searchType: scheduleSearchType.value[0].code,
  searchInput: "",
  filterType: scheduleFilterType.value[0].code,
  filterInput: "",
});

const disabledDate = (time: Date) => {
  return time.getTime() > Date.now();
};

const clear = () => {
  form.searchInput = "";
  form.filterInput = "";
};

const submitForm = () => {
  emits("applyOption", form);
};
</script>

<template>
  <el-form :model="form" class="form">
    <el-row class="search-row">
      <el-col :span="20" class="mx-2 flex-column">
        <el-row class="form-item mb-3">
          <el-col :span="4">
            <span class="label">
              <el-icon class="me-1">
                <Search />
              </el-icon>
              {{ buttonMsg("search") }}
            </span>
          </el-col>
          <el-col :span="20">
            <el-row>
              <el-col :span="5">
                <el-select
                  v-model="form.searchType"
                  :placeholder="placeholderMsg('select')"
                  class="me-2"
                  style="width: 115px"
                  size="large"
                >
                  <template v-for="type in scheduleSearchType" :key="type.code">
                    <el-option
                      :label="categoryMsg('schedule.search', type.code)"
                      :value="type.code"
                    />
                  </template>
                </el-select>
              </el-col>
              <el-col :span="19">
                <el-input
                  v-model="form.searchInput"
                  :prefix-icon="Search"
                  :placeholder="inputPhMsg(labelMsg('content'))"
                  size="large"
                />
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-row class="form-item">
          <el-col :span="4">
            <span class="label">
              <el-icon class="me-1">
                <Filter />
              </el-icon>
              {{ labelMsg("filter") }}
            </span>
          </el-col>
          <el-col :span="20" class="d-flex justify-content-start">
            <el-select
              v-model="form.filterType"
              :placeholder="placeholderMsg('select')"
              class="me-1"
              style="width: 115px"
              size="large"
            >
              <template v-for="type in scheduleFilterType" :key="type.code">
                <el-option
                  :label="categoryMsg('schedule.filter', type.code)"
                  :value="type.code"
                />
              </template>
            </el-select>
            <el-select
              v-if="form.filterType === scheduleFilterType[0].code"
              v-model="form.filterInput"
              :placeholder="placeholderMsg('select')"
              style="width: 200px"
              size="large"
            >
              <template v-for="val in cityNames" :key="val.code">
                <el-option
                  :label="categoryMsg('city.name', val.code.toLowerCase())"
                  :value="val.code"
                />
              </template>
            </el-select>
            <el-date-picker
              v-else
              v-model="form.filterInput"
              :disabled-date="disabledDate"
              :placeholder="placeholderMsg('pick-day')"
              style="width: 200px"
              type="date"
              size="large"
            />
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="2" class="d-flex justify-content-start flex-column ms-2">
        <el-button
          size="large"
          color="#0f6778"
          style="width: 80px"
          @click="submitForm"
        >
          {{ buttonMsg("search") }}
        </el-button>
        <el-button class="reset-btn" size="large" @click="clear" text>
          <el-icon>
            <Refresh />
          </el-icon>
          {{ buttonMsg("reset") }}
        </el-button>
      </el-col>
    </el-row>
  </el-form>
</template>

<style lang="scss" scoped>
.form {
  display: flex;
  flex-direction: column;

  .search-row {
    margin-bottom: 5px;

    .form-item {
      display: flex;
      justify-content: start;
      align-items: center;
    }

    .label {
      display: flex;
      justify-content: center;
      align-items: center;
      font-weight: 400;
      margin-right: 25px;
    }
  }

  .reset-btn {
    margin-top: 20px;
  }
}
</style>
