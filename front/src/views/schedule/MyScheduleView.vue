<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/detail/ScheduleCalendar.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { Search } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import ScheduleForm from "@/components/schedule/ScheduleForm.vue";

const props = defineProps({
  loginId: {
    type: String,
    required: true,
  },
});
const { isPending, mySchedules, getMySchedules, getScheduleContents } =
  useSchedule();
const selectedId = ref<number>();
const currSchedule = ref<ScheduleCardType>();
const initDate = ref<string>("");

onMounted(async () => {
  await getMySchedules(props.loginId).then(async () => {
    selectedId.value = mySchedules.value[0].id;
    await changeSchedule();
  });
});

const findSchedule = (): ScheduleCardType | undefined => {
  return mySchedules.value.find((value) => value.id === selectedId.value);
};

const changeSchedule = async () => {
  currSchedule.value = findSchedule();
  if (selectedId.value != null) {
    await getScheduleContents(selectedId.value).then(() => {
      initDate.value = String(currSchedule.value?.period.startDate);
    });
  }
};
</script>

<template>
  <el-row v-if="currSchedule" class="mb-5">
    <el-col>
      <TitleSlot>내 스케줄 관리</TitleSlot>
      <el-row :gutter="10">
        <el-col :span="18">
          <ScheduleCalendar
            v-if="initDate"
            :editable="false"
            :init-date="initDate"
            :manage-state="false"
            @select-content="console.log('선택..')"
          />
        </el-col>
        <el-col :span="6">
          <el-card class="mb-3">
            <SmallTitleSlot>스케줄 선택</SmallTitleSlot>
            <div class="d-flex justify-content-between mt-3 mb-3">
              <el-select
                v-model="selectedId"
                :placeholder="$t('common.select')"
              >
                <template v-for="val in mySchedules" :key="val.id">
                  <el-option :label="val.title" :value="val.id" />
                </template>
              </el-select>
              <el-button
                :icon="Search"
                circle
                class="ms-2"
                @click="changeSchedule"
              />
            </div>
          </el-card>
          <ScheduleForm :schedule="currSchedule" />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.layout-container-demo .el-header {
  position: relative;
}

.layout-container-demo .el-aside {
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>
