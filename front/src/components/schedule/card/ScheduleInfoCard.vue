<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import PopoverProfileSlot from "@/components/common/PopoverProfileSlot.vue";
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import { Location, Money, View } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";

const props = defineProps({
  index: {
    type: Number || String,
    required: true,
  },
});
const emit = defineEmits(["goToMemberSchedules"]);

const { searchTypes, sortTypes, otherSchedules } = useSchedule();

const schedule = ref(otherSchedules.value[props.index]);

const goToMemberSchedules = () => {
  const request: SearchSortFormType = {
    searchType: searchTypes.value[0].code,
    searchInput: schedule.value.profile.nickname,
    filterType: null,
    filterInput: null,
    sortType: sortTypes.value[0].title,
  };
  emit("goToMemberSchedules", request);
};
</script>
<template>
  <el-card class="information">
    <div class="profile-title d-flex justify-content-start">
      <div class="profile">
        <PopoverProfileSlot :schedule="schedule">
          <a class="mention" @click="goToMemberSchedules">
            @ {{ $t("schedule.popover.link") }}
          </a>
        </PopoverProfileSlot>
        <div class="nickname mt-2">
          {{ schedule.profile.nickname }}
        </div>
      </div>
      <ScheduleInfoSlot
        :schedule="schedule"
        :font-size="0.9"
        :show-likes="true"
      >
        <template v-slot:title>
          <SmallTitleSlot>
            <router-link
              :to="{
                name: 'read-schedule',
                params: {
                  id: schedule.id,
                },
              }"
            >
              {{ schedule.title }}
            </router-link>
          </SmallTitleSlot>
        </template>
        <template v-slot:period-title>
          <el-tooltip
            :content="$t('schedule.tooltip.date')"
            placement="left-start"
          >
            <el-icon><Calendar /></el-icon>
          </el-tooltip>
        </template>
        <template v-slot:cost-title>
          <el-tooltip
            :content="$t('schedule.tooltip.cost')"
            placement="left-start"
          >
            <el-icon><Money /></el-icon>
          </el-tooltip>
        </template>
        <template v-slot:location-title>
          <el-tooltip
            :content="$t('schedule.tooltip.location')"
            placement="left-start"
          >
            <el-icon><Location /></el-icon>
          </el-tooltip>
        </template>
        <template v-slot:view-title>
          <el-tooltip
            :content="$t('schedule.tooltip.view')"
            placement="bottom-end"
          >
            <el-icon><View /></el-icon>
          </el-tooltip>
        </template>
        <template v-slot:like-title>
          <el-tooltip :content="$t('schedule.tooltip.like')" placement="bottom">
            <i class="bi bi-hand-thumbs-up"></i>
          </el-tooltip>
        </template>
      </ScheduleInfoSlot>
    </div>
  </el-card>
</template>
<style scoped lang="scss">
.nickname {
  font-size: 0.9rem;
}
span {
  font-size: 0.78rem;
}
</style>
