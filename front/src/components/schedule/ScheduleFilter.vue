<script lang="ts" setup>
import { useScheduleStore } from "@/stores/schedule";
import { onMounted, reactive, ref } from "vue";
import { useSchedule } from "@/composables/schedules";
import { Search } from "@element-plus/icons-vue";
import { useCityStore } from "@/stores/city";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";
import type { SortType } from "@/modules/types/common/SortType";
import { useDate } from "@/composables/date";
import { useI18n } from "vue-i18n";

const emit = defineEmits(["applyOption"]);
const { t } = useI18n();
const store = useScheduleStore();
const cityStore = useCityStore();
const { getSearchTypes, getFilterTypes, getSortTypes } = useSchedule();
const { convertDateToString } = useDate();
const scheduleSearchForm: SearchSortFormType = reactive({
  searchType: null,
  searchInput: null,
  filterType: null,
  filterInput: null,
  sortType: null,
});

const sortTypes = ref<SortType[]>();
const searchTypes = ref<EnumType[]>();
const filterTypes = ref<EnumType[]>();
const searchCollapse = ref(0);
const filterCollapse = ref(0);
const sortCollapse = ref(0);
const cityNames = ref<EnumType[]>(cityStore.cityNames.data as EnumType[]);

onMounted(async () => {
  await getSearchTypes().then(() => {
    searchTypes.value = store.searchTypes;
    scheduleSearchForm.searchType = searchTypes.value[0].code;
  });
  await getFilterTypes().then(() => {
    filterTypes.value = store.filterTypes;
    scheduleSearchForm.filterType = filterTypes.value[0].code;
  });
  await getSortTypes().then(() => {
    sortTypes.value = store.sortTypes;
    scheduleSearchForm.sortType = sortTypes.value[0].title;
  });
});
const selectSearchType = () => {
  scheduleSearchForm.searchInput = "";
};
const selectFilterType = () => {
  scheduleSearchForm.filterInput =
    scheduleSearchForm.filterType === "CITY_NAME"
      ? cityNames.value[0].code
      : null;
};
const applyOption = () => {
  if (scheduleSearchForm.filterType === "START_DATE") {
    if (scheduleSearchForm.filterInput instanceof Date) {
      const date: Date = scheduleSearchForm.filterInput as Date;
      scheduleSearchForm.filterInput = convertDateToString(date);
    }
  }
  emit("applyOption", scheduleSearchForm);
};
const disabledDate = (time: Date) => {
  return time.getTime() > Date.now();
};
</script>
<template>
  <el-row>
    <el-col>
      <el-form :model="scheduleSearchForm">
        <div class="d-flex justify-content-start flex-wrap filter-search">
          <div class="filter-collapse me-3">
            <el-collapse v-model="searchCollapse" class="search">
              <el-collapse-item :name="1">
                <template #title>
                  <el-icon class="me-1">
                    <Search />
                  </el-icon>
                  {{ $t("common.search") }}
                </template>
                <div class="d-flex justify-content-start">
                  <el-form-item>
                    <el-select
                      v-model="scheduleSearchForm.searchType"
                      :placeholder="$t('common.select')"
                      class="me-2"
                      style="width: 115px"
                      @change="selectSearchType"
                    >
                      <template v-for="val in searchTypes" :key="val.code">
                        <el-option
                          :label="
                            $t(`schedule.search.${val.code.toLowerCase()}`)
                          "
                          :value="val.code"
                        />
                      </template>
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-input
                      v-model="scheduleSearchForm.searchInput"
                      :placeholder="$t('common.please-input')"
                      style="width: 200px"
                    >
                    </el-input>
                  </el-form-item>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div class="filter-collapse me-3">
            <el-collapse v-model="filterCollapse" class="filter">
              <el-collapse-item name="1">
                <template #title>
                  <el-icon class="me-1">
                    <Filter />
                  </el-icon>
                  {{ $t("common.filter") }}
                </template>
                <div class="d-flex justify-content-start">
                  <el-form-item>
                    <el-select
                      v-model="scheduleSearchForm.filterType"
                      :placeholder="$t('common.filter')"
                      class="me-2"
                      style="width: 115px"
                      @change="selectFilterType"
                    >
                      <template v-for="val in filterTypes" :key="val.code">
                        <el-option
                          :label="
                            $t(`schedule.search.${val.code.toLowerCase()}`)
                          "
                          :value="val.code"
                        />
                      </template>
                    </el-select>
                  </el-form-item>
                  <el-form-item
                    v-if="scheduleSearchForm.filterType === 'CITY_NAME'"
                  >
                    <el-select
                      v-model="scheduleSearchForm.filterInput"
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
                  <el-form-item
                    v-else-if="scheduleSearchForm.filterType === 'START_DATE'"
                  >
                    <el-date-picker
                      v-model="scheduleSearchForm.filterInput"
                      :disabled-date="disabledDate"
                      :placeholder="$t('common.pick-day')"
                      style="width: 200px"
                      type="date"
                    />
                  </el-form-item>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div class="filter-collapse me-3">
            <el-collapse v-model="sortCollapse" class="sort">
              <el-collapse-item name="1">
                <template #title>
                  <el-icon class="me-1">
                    <Sort />
                  </el-icon>
                  {{ $t("common.sort") }}
                </template>
                <el-form-item>
                  <el-select
                    v-model="scheduleSearchForm.sortType"
                    :placeholder="$t('common.sort')"
                    class="me-2"
                    style="width: 115px"
                  >
                    <template v-for="val in sortTypes" :key="val.code">
                      <el-option
                        :label="$t(`schedule.sort.${val.code.toLowerCase()}`)"
                        :value="val.title"
                      />
                    </template>
                  </el-select>
                </el-form-item>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div class="btn-wrapper ms-2 pt-1">
            <el-form-item>
              <el-button size="large" @click="applyOption"
                >{{ $t("common.application") }}
              </el-button>
            </el-form-item>
          </div>
        </div>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.el-collapse {
  &.search {
    width: 350px;
  }

  &.filter {
    width: 350px;
  }
  &.sort {
    width: 140px;
  }
}
</style>
