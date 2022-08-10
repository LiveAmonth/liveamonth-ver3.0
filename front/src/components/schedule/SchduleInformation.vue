<script setup lang="ts">
import { useScheduleStore } from "@/stores/schedule";
const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});

const store = useScheduleStore();
</script>
<template>
  <el-card class="information">
    <div class="profile-title d-flex">
      <div class="profile">
        <el-avatar :size="80" :src="`/src/assets/image/default.jpg`" />
        <div class="nickname mt-2">닉네임</div>
      </div>
      <div class="title-info">
        <el-row>
          <SmallTitleSlot>
            <router-link
              :to="{
                name: 'read-schedule',
                params: { scheduleId: 1 },
              }"
            >
              스케줄 제목..
            </router-link>
          </SmallTitleSlot>
        </el-row>
        <el-row>
          <div class="date-range">
            <span>
              <el-tooltip
                :content="$t('schedule.tooltip.date')"
                placement="left-start"
              >
                <el-icon><Calendar /></el-icon>
              </el-tooltip>
              2022-02-02 ~ 2022-02-02</span
            >
          </div>
        </el-row>
        <el-row>
          <div class="cost">
            <span>
              <el-tooltip
                :content="$t('schedule.tooltip.cost')"
                placement="left-start"
                ><el-icon><Money /></el-icon>
              </el-tooltip>
              {{ $filters.makeComma(20000) }}원</span
            >
          </div>
        </el-row>
        <el-row>
          <div class="location">
            <span
              ><el-tooltip
                :content="$t('schedule.tooltip.location')"
                placement="left-start"
                ><el-icon><Location /></el-icon
              ></el-tooltip>
              {{ $t("city.name.SE") }}</span
            >
          </div>
        </el-row>
        <el-row>
          <div class="view me-2">
            <span
              ><el-tooltip
                :content="$t('schedule.tooltip.view')"
                placement="bottom-end"
                ><el-icon><View /></el-icon
              ></el-tooltip>
              1</span
            >
          </div>
          <div class="like">
            <span
              ><el-tooltip
                :content="$t('schedule.tooltip.like')"
                placement="bottom"
                ><i class="bi bi-hand-thumbs-up"></i
              ></el-tooltip>
              1</span
            >
          </div>
        </el-row>
      </div>
    </div>
  </el-card>
</template>

<style scoped lang="scss">
.profile {
  .nickname {
    font-size: 0.9rem;
  }
}

.information {
  .title-info {
    width: 100%;
    margin-left: 25px;

    .el-row {
      justify-content: flex-start;
    }

    .el-icon {
      margin-right: 2px;
      vertical-align: middle;
    }

    span {
      font-size: 0.78rem;
    }
  }
}
a {
  text-decoration: none;

  &:hover {
    font-weight: bold;
  }

  &:visited {
    color: inherit;
  }
}
</style>
