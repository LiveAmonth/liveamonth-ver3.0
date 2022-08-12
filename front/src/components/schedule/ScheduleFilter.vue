<script lang="ts" setup>
import { useScheduleStore } from "@/stores/schedule";
import { onMounted, reactive, ref } from "vue";
import { useSchedule } from "@/composables/schedules";
import { Search } from "@element-plus/icons-vue";
import { useCityStore } from "@/stores/city";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { SearchSortFormType } from "@/modules/types/common/SearchSortType";
import type { SortType } from "@/modules/types/common/SortType";
import { useDate } from "@/composables/date";

const emit = defineEmits(["adaptOption"]);

const store = useScheduleStore();
const cityStore = useCityStore();
const { getSortTypes, getScheduleSearchCond } = useSchedule();
const { convertDateToString } = useDate();
const searchSortForm: SearchSortFormType = reactive({
  searchType: "",
  searchInput: "",
  sortType: 0,
});

const types = ref<SortType[]>([]);
const searchCond = ref<EnumType[]>();
const searchCollapse = ref(0);
const filterCollapse = ref(0);
const cityNames = ref<EnumType[]>(cityStore.cityNames.data as EnumType[]);

onMounted(async () => {
  await getSortTypes().then(() => {
    types.value = store.sortTypes;
  });
  await getScheduleSearchCond().then(() => {
    searchCond.value = store.searchCond;
  });
});
const selectSearchType = () => {
  searchSortForm.searchInput =
    searchSortForm.searchType === "CITY_NAME" ? "SE" : "";
};
const adaptOption = () => {
  const idx = searchSortForm.sortType as number;
  searchSortForm.sortType = types.value[idx].code;
  if (searchSortForm.searchType === "START_DATE") {
    const date: Date = searchSortForm.searchInput as Date;
    searchSortForm.searchInput = convertDateToString(date);
  }
  emit("adaptOption", searchSortForm);
  searchSortForm.sortType = idx;
};
const disabledDate = (time: Date) => {
  return time.getTime() > Date.now();
};
</script>
<template>
  <el-row>
    <el-col>
      <el-form :model="searchSortForm">
        <div class="d-flex justify-content-start filter-search">
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
                      v-model="searchSortForm.searchType"
                      :placeholder="$t('common.select')"
                      class="me-2"
                      style="width: 115px"
                      @change="selectSearchType"
                    >
                      <template v-for="val in searchCond" :key="val.code">
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
                    v-if="searchSortForm.searchType === 'MEMBER_NICKNAME'"
                  >
                    <el-input
                      v-model="searchSortForm.searchInput"
                      :placeholder="$t('common.please-input')"
                      style="width: 200px"
                    >
                    </el-input>
                  </el-form-item>
                  <el-form-item
                    v-else-if="searchSortForm.searchType === 'CITY_NAME'"
                  >
                    <el-select
                      v-model="searchSortForm.searchInput"
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
                    v-else-if="searchSortForm.searchType === 'START_DATE'"
                  >
                    <el-date-picker
                      v-model="searchSortForm.searchInput"
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
            <el-collapse v-model="filterCollapse" class="filter">
              <el-collapse-item name="1">
                <template #title>
                  <el-icon class="me-1">
                    <Filter />
                  </el-icon>
                  {{ $t("common.filter") }}
                </template>
                <el-form-item>
                  <el-radio-group v-model="searchSortForm.sortType">
                    <template v-for="(type, i) in types" :key="type.title">
                      <el-radio :label="i">{{ type.value }}</el-radio>
                    </template>
                  </el-radio-group>
                </el-form-item>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div class="btn-wrapper ms-2 pt-1">
            <el-form-item>
              <el-button size="large" @click="adaptOption"
                >검색 & 적용
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
}
</style>
