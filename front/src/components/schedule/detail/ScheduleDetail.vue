<script lang="ts" setup>
import SimpleScheduleCard from "@/components/schedule/card/SimpleScheduleCard.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ImageIcon from "@/components/common/ImageIcon.vue";
import { Reading, Paperclip, Money, Clock } from "@element-plus/icons-vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { onMounted } from "vue";
import { useInteraction } from "@/composables/interaction/interaction";

const { type, currentSchedule, currScheduleContents, contentCollapse } =
  useSchedule();
const { isLiked, isLikedContent, reactContent, changeLikeState } =
  useInteraction();

onMounted(async () => {
  await isLikedContent(type, currentSchedule.value.id);
});

const clickHeart = async () => {
  await reactContent(type, currentSchedule.value.id)
    .then(async () => {
      await changeLikeState();
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>
<template>
  <el-card class="detail mb-3">
    <div class="d-flex justify-content-start">
      <SmallTitleSlot class="mb-4">
        {{ $t("schedule.title.schedule") }}
      </SmallTitleSlot>
      <ImageIcon
        v-if="!isLiked"
        :height="30"
        :url="`/src/assets/image/icon/love.png`"
        :width="30"
        class="ms-2"
        @click="clickHeart"
      />
      <ImageIcon
        v-else
        :height="30"
        :url="`/src/assets/image/icon/love-fill.png`"
        :width="30"
        class="ms-2"
        @click="clickHeart"
      />
      <span class="likes">
        {{ currentSchedule.numberOfLikes }}
      </span>
    </div>
    <SimpleScheduleCard />
  </el-card>
  <el-card class="content">
    <SmallTitleSlot class="mb-4"
      >{{ $t("schedule.title.content") }}
    </SmallTitleSlot>
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
<style lang="scss" scoped>
.el-icon {
  padding-bottom: 0.1rem;
}

.likes {
  margin-left: 5px;
  font-size: 25px;
}
</style>
