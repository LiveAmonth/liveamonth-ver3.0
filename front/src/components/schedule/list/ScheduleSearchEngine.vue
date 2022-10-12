<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { Search, Filter, Sort } from "@element-plus/icons-vue";
import { useDate } from "@/composables/common/date";
import { useCategory } from "@/composables/common/category";
import type { ScheduleSearchFormType } from "@/modules/types/schedule/ScheduleTypes";

const emit = defineEmits(["applyOption"]);
const { getDate } = useDate();
const {
  scheduleSearchType,
  scheduleFilterType,
  scheduleSortType,
  cityNames,
  getCityNames,
  getScheduleCategories,
} = useCategory();
const form = reactive<ScheduleSearchFormType>({
  searchType: null,
  searchInput: null,
  filterType: null,
  filterInput: null,
  sortType: null,
});

const searchCollapse = ref(0);
const filterCollapse = ref(0);
const sortCollapse = ref(0);

const justifyClass = (collapse: number): string => {
  return collapse ? "d-flex justify-content-center" : "";
};

onMounted(async () => {
  await getCityNames();
  await getScheduleCategories().then(() => {
    setUpEngine();
  });
});

const setUpEngine = () => {
  form.searchType = scheduleSearchType.value[0].code;
  form.filterType = scheduleFilterType.value[0].code;
  form.sortType = scheduleSortType.value[0].title;
};

const selectSearchType = () => {
  form.searchInput = "";
};

const selectFilterType = () => {
  form.filterInput =
    form.filterType === "CITY_NAME" ? cityNames.value[0].code : null;
};

const applyOption = () => {
  if (form.filterType === "START_DATE") {
    if (form.filterInput) {
      form.filterInput = getDate(form.filterInput);
    }
  }
  searchCollapse.value = 0;
  filterCollapse.value = 0;
  sortCollapse.value = 0;
  emit("applyOption", form);
};

const disabledDate = (time: Date) => {
  return time.getTime() > Date.now();
};
</script>
<template>
  <el-card class="schedule-list-card" :body-style="{ paddingBottom: '6px' }">
    <el-form :model="form" class="d-flex justify-content-center">
      <el-collapse
        v-model="searchCollapse"
        class="search me-3"
        :class="justifyClass(searchCollapse)"
        accordion
      >
        <el-collapse-item class="" :name="1">
          <template #title>
            <el-icon class="me-1">
              <Search />
            </el-icon>
            {{ $t("common.search") }}
          </template>
          <div class="d-flex justify-content-between pt-1">
            <el-form-item>
              <el-select
                v-model="form.searchType"
                :placeholder="$t('common.select')"
                class="me-2"
                style="width: 115px"
                @change="selectSearchType"
              >
                <template v-for="val in scheduleSearchType" :key="val.code">
                  <el-option
                    :label="$t(`schedule.search.${val.code.toLowerCase()}`)"
                    :value="val.code"
                  />
                </template>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="form.searchInput"
                :placeholder="
                  $t('common.please-input', {
                    field: $t('member.nickname'),
                  })
                "
                style="width: 200px"
              >
              </el-input>
            </el-form-item>
          </div>
        </el-collapse-item>
      </el-collapse>
      <el-collapse
        v-model="filterCollapse"
        class="filter me-3"
        :class="justifyClass(filterCollapse)"
        accordion
      >
        <el-collapse-item name="1">
          <template #title>
            <el-icon class="me-1">
              <Filter />
            </el-icon>
            {{ $t("common.filter") }}
          </template>
          <div class="d-flex justify-content-between pt-1">
            <el-form-item>
              <el-select
                v-model="form.filterType"
                :placeholder="$t('common.filter')"
                class="me-2"
                style="width: 115px"
                @change="selectFilterType"
              >
                <template v-for="val in scheduleFilterType" :key="val.code">
                  <el-option
                    :label="$t(`schedule.search.${val.code.toLowerCase()}`)"
                    :value="val.code"
                  />
                </template>
              </el-select>
            </el-form-item>
            <el-form-item v-if="form.filterType === 'CITY_NAME'">
              <el-select
                v-model="form.filterInput"
                :placeholder="$t('common.select')"
                style="width: 200px"
              >
                <template v-for="val in cityNames" :key="val.code">
                  <el-option
                    :label="$t(`city.name.${val.code}`)"
                    :value="val.code"
                  />
                </template>
              </el-select>
            </el-form-item>
            <el-form-item v-else-if="form.filterType === 'START_DATE'">
              <el-date-picker
                v-model="form.filterInput"
                :disabled-date="disabledDate"
                :placeholder="$t('common.pick-day')"
                style="width: 200px"
                type="date"
              />
            </el-form-item>
          </div>
        </el-collapse-item>
      </el-collapse>
      <el-collapse
        v-model="sortCollapse"
        class="sort me-3"
        :class="justifyClass(sortCollapse)"
        accordion
      >
        <el-collapse-item name="1">
          <template #title>
            <el-icon class="me-1">
              <Sort />
            </el-icon>
            {{ $t("common.sort") }}
          </template>
          <div class="d-flex justify-content-between pt-1">
            <el-form-item>
              <el-select
                v-model="form.sortType"
                :placeholder="$t('common.sort')"
                class="me-2"
                style="width: 115px"
              >
                <template v-for="val in scheduleSortType" :key="val.code">
                  <el-option
                    :label="$t(`schedule.sort.${val.code.toLowerCase()}`)"
                    :value="val.title"
                  />
                </template>
              </el-select>
            </el-form-item>
          </div>
        </el-collapse-item>
      </el-collapse>
      <div class="btn-wrapper ms-2 pt-1">
        <el-form-item>
          <el-button size="large" @click="applyOption">
            {{ $t("common.application") }}
          </el-button>
        </el-form-item>
      </div>
    </el-form>
  </el-card>
</template>

<style lang="scss">
.schedule-list-card {
  width: fit-content;

  .el-collapse {
    border: none;

    &.search {
      width: 323px;
    }

    &.filter {
      width: 323px;
    }

    &.sort {
      width: 123px;
    }
  }

  .el-collapse-item__header {
    border: none;
  }

  .el-collapse-item__wrap {
    border-bottom: none;

    .el-collapse-item__content {
      padding-bottom: 0;
    }
  }
}
</style>
