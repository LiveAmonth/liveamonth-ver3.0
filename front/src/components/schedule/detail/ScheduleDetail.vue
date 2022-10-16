<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import ImageIcon from "@/components/common/ImageIcon.vue";
import { Reading, Paperclip, Money, Clock } from "@element-plus/icons-vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { onMounted } from "vue";
import { useInteraction } from "@/composables/interaction/interaction";
import { useAuth } from "@/composables/member/auth";
import { useMessageBox } from "@/composables/common/messageBox";

const { isLoggedIn } = useAuth();
const { type, currentSchedule, currScheduleContents, contentCollapse } =
  useSchedule();
const { isLiked, error, isLikedContent, reactContent } = useInteraction();
const { titleMsg, labelMsg } = useMessageBox();

onMounted(async () => {
  if (isLoggedIn.value) {
    await isLikedContent(type, currentSchedule.value.id);
  }
});

const handelLike = async () => {
  await reactContent(type, currentSchedule.value.id).then(() => {
    if (!error.value && isLoggedIn.value) {
      isLiked.value
        ? currentSchedule.value.numberOfLikes++
        : currentSchedule.value.numberOfLikes--;
    }
  });
};
</script>
<template>
  <el-card class="mb-3" :body-style="{ paddingRight: 0 }">
    <ScheduleInfoSlot :schedule="currentSchedule" :font-size="0.85">
      <template v-slot:title>
        <div class="title-like">
          <SmallTitleSlot :title="titleMsg('schedule.info')" />
          <el-button class="like-btn" @click="handelLike">
            <div class="icon-count">
              <ImageIcon
                :height="25"
                :width="25"
                :url="`/src/assets/image/icon/love${
                  !isLiked ? '' : '-fill'
                }.png`"
              />
              {{ $count(currentSchedule.numberOfLikes) }}
            </div>
          </el-button>
        </div>
      </template>
    </ScheduleInfoSlot>
  </el-card>
  <el-card class="content">
    <SmallTitleSlot :title="titleMsg('schedule.content.info')" class="mb-4" />
    <el-collapse v-model="contentCollapse" class="search">
      <template v-for="(content, idx) in currScheduleContents" :key="idx">
        <el-collapse-item :name="content.id">
          <template #title>
            <el-icon class="me-1">
              <Paperclip />
            </el-icon>
            {{ content.title }}
          </template>
          <el-descriptions
            :column="2"
            :title="content.title"
            border
            direction="vertical"
            size="small"
          >
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Reading />
                  </el-icon>
                  {{ labelMsg("content") }}
                </div>
              </template>
              {{ content.content }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Money />
                  </el-icon>
                  {{ labelMsg("schedule.content.cost") }}
                </div>
              </template>
              {{ $won(content.cost) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  {{ labelMsg("schedule.content.start") }}
                </div>
              </template>
              {{ content.timePeriod.startDateTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  {{ labelMsg("schedule.content.end") }}
                </div>
              </template>
              {{ content.timePeriod.endDateTime }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
      </template>
    </el-collapse>
  </el-card>
</template>
<style lang="scss" scoped>
.title-like {
  display: flex;
  justify-content: start;
  align-items: center;

  .like-btn {
    border: none;
    padding: 0 5px;
    margin-bottom: 10px;

    &:hover,
    &:focus {
      color: #606266;
      background-color: inherit;
      border-color: #dcdfe6;
    }

    .icon-count {
      display: flex;
      justify-content: center;
      font-size: 1.3rem;
    }
  }
}
</style>
