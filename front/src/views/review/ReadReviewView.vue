<script lang="ts" setup>
import CommentComponent from "@/components/comment/CommentComponent.vue";

import { View } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useReview } from "@/composables/review/review";
import TitleSlot from "@/components/common/TitleSlot.vue";

const props = defineProps({
  id: {
    type: [Number || String],
    required: true,
  },
});

const { currReview, getReview } = useReview();
const router = useRouter();
const commentKey = ref<number>(0);

onMounted(async () => {
  await getReview(Number(props.id));
});

const moveToEdit = () => {
  router.push({ name: "edit", params: { id: props.id } });
};
</script>

<template>
  <el-row class="review">
    <el-col :span="20">
      <el-row v-if="currReview.id">
        <el-col>
          <TitleSlot>{{ currReview.title }}</TitleSlot>
          <div class="sub d-flex justify-content-start">
            <div class="avatar me-1">
              <el-avatar :size="20" :src="'/src/assets/image/default.jpg'" />
            </div>
            <div class="writer">
              {{ currReview.profile.nickname }}
            </div>
            <div class="regDate me-2">
              {{ currReview.createDateTime }}
            </div>
            <div class="views">
              <el-icon class="me-1">
                <View />
              </el-icon>
              {{ $comma(currReview.numberOfHits) }}
            </div>
          </div>
        </el-col>
      </el-row>
      <el-divider />
      <el-row class="mt-3">
        <el-col>
          <div class="content">{{ currReview.content }}</div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <el-divider />
  <el-row class="comments" v-if="currReview.id">
    <el-col :span="20">
      <CommentComponent
        :key="commentKey"
        :content-id="Number(id)"
        :type="'review'"
        :writer="currReview.profile.nickname"
        @refresh="commentKey++"
      />
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.review {
  display: flex;
  justify-content: center;
  margin-bottom: 200px;

  .title {
    font-size: 1.8rem;
    font-weight: 600;
    color: #383838;
    margin-bottom: 30px;
  }

  .content {
    font-size: 1.2rem;
    margin-top: 8px;
    color: #535252;
    white-space: break-spaces;
    line-height: 1.5;
  }

  .sub {
    margin-top: 5px;
    font-size: 0.85rem;
    font-weight: 300;
    align-items: center;

    .writer {
      font-weight: 400;
    }

    .regDate {
      margin-left: 10px;
      color: #c4c4c4;
    }

    .views {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}

.comments {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}
</style>
