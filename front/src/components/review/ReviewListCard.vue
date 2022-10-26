<script lang="ts" setup>
import ImageIcon from "@/components/common/ImageIcon.vue";
import { View, Close } from "@element-plus/icons-vue";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";
import type { ReviewListType } from "@/modules/types/review/ReviewTypes";
import type { PropType } from "vue";

const props = defineProps({
  review: {
    type: Object as PropType<ReviewListType>,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emit = defineEmits(["deleteReview"]);
const { goReadReview, goReview } = useReview();
const { buttonMsg, resultMsg, openConfirmMessageBox } = useMessageBox();

const handleDelete = async () => {
  await openConfirmMessageBox(
    resultMsg("review.delete.title"),
    resultMsg("review.delete.message")
  ).then(() => {
    emit("deleteReview", props.review.id);
  });
};
</script>

<template>
  <el-row class="review-item">
    <el-col :span="18" class="review">
      <div class="title">
        <span @click="isMyPage ? goReadReview(review.id) : goReview(review.id)">
          {{ review.title }}
        </span>
      </div>
      <div class="content">
        {{ review.content }}
      </div>
      <div class="sub d-flex">
        <div class="writer">
          {{ review.writer }}
        </div>
        <div class="regDate">
          {{ review.elapsedTime }}
        </div>
        <div class="views">
          <el-icon class="me-1">
            <View />
          </el-icon>
          {{ $comma(review.numberOfHits) }}
        </div>
      </div>
    </el-col>
    <el-col :span="2" class="comment-like">
      <div v-if="isMyPage" class="flex-grow-1"></div>
      <div class="comment-count">
        <span class="count">
          {{ $count(review.numberOfComments) }}
        </span>
        <span class="label"> 댓글 </span>
      </div>
      <div class="like-count">
        <ImageIcon
          :disable="true"
          :height="20"
          :url="`/src/assets/image/icon/love-fill.png`"
          :width="20"
          class="me-1"
        />
        <span class="likes">
          {{ $count(review.numberOfLikes) }}
        </span>
      </div>
    </el-col>
    <div v-if="isMyPage" class="delete" @click="handleDelete">
      {{ buttonMsg("delete") }}
      <el-icon>
        <Close />
      </el-icon>
    </div>
  </el-row>
</template>

<style lang="scss" scoped>
.review-item {
  display: flex;
  justify-content: space-between;

  .review {
    margin-right: 40px;

    .title {
      margin-bottom: 15px;
      font-size: 1.5rem;

      span {
        color: #383838;
        text-decoration: none;
        cursor: pointer;
      }
    }

    .content {
      font-size: 0.95rem;
      margin-top: 8px;
      color: #737373;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2; /* 라인수 */
      -webkit-box-orient: vertical;
      word-wrap: break-word;
      line-height: 1.2em;
      height: 2.4rem;
    }

    .sub {
      margin-top: 20px;
      font-size: 0.85rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }

      .views {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }

  .comment-like {
    display: flex;
    flex-direction: column;
    align-items: center;

    .comment-count {
      margin-bottom: 10px;
      display: flex;
      width: 60px;
      height: 60px;
      text-align: center;
      justify-content: center;
      flex-direction: column;
      border: #dcdfe6 1px solid;
      border-radius: 50%;
    }

    .like-count {
      display: flex;
      justify-content: center;
    }
  }
}

.delete {
  display: flex;
  justify-content: end;
  cursor: pointer;
  margin-top: -15px;
}
</style>
