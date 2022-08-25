<script setup lang="ts">
import SimpleScheduleCard from "@/components/schedule/SimpleScheduleCard.vue";
import { useSchedule } from "@/composables/schedule";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ImageIcon from "@/components/common/ImageIcon.vue";
import { onMounted, ref } from "vue";
import { useAuth } from "@/composables/auth";
import { useInteraction } from "@/composables/interaction";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});

const store = useScheduleContentStore();
const { currScheduleContents } = useSchedule();
const { getOtherSchedule } = useSchedule();
const { isLike, isLoggedInMemberLikes, likeContent } = useInteraction();

const schedule = ref(getOtherSchedule(Number(props.id)));

onMounted(async () => {
  await isLoggedInMemberLikes("SCHEDULE", Number(props.id));
});

</script>
<template>
  <el-card class="mb-3">
    <div class="d-flex justify-content-start">
      <SmallTitleSlot class="mb-4">
        {{ $t("schedule.title.schedule") }}
      </SmallTitleSlot>
      <ImageIcon
        class="ms-2"
        :height="30"
        :width="30"
        :url="`/src/assets/image/icon/love${isLike ? '-fill' : ''}.png`"
        @click="likeContent('SCHEDULE', Number(id))"
      />
      <span class="likes">
        {{ schedule.likes }}
      </span>
    </div>
    <SimpleScheduleCard :schedule="schedule" />
  </el-card>
  <el-card>
    <SmallTitleSlot class="mb-4">{{
      $t("schedule.title.content")
    }}</SmallTitleSlot>
    <el-collapse v-model="store.contentCollapse" class="search">
      <template v-for="(content, idx) in currScheduleContents" :key="idx">
        <el-collapse-item :name="content.id">
          <template #title>
            <el-icon class="me-1">
              <Paperclip />
            </el-icon>
            {{ content.title }}
          </template>
          <el-descriptions
            :title="content.title"
            direction="vertical"
            :column="2"
            size="small"
            border
          >
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Reading />
                  </el-icon>
                  {{ $t("schedule.form.content.content") }}
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
                  {{ $t("schedule.form.content.cost") }}
                </div>
              </template>
              {{ content.cost }}원
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  {{ $t("schedule.form.content.period.start") }}
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
                  {{ $t("schedule.form.content.period.end") }}
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
<style scoped lang="scss">
.el-icon {
  padding-bottom: 0.1rem;
}

.likes {
  margin-left: 5px;
  font-size: 25px;
}
</style>
