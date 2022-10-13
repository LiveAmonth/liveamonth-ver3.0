<script lang="ts" setup>
import CommentComponent from "@/components/comment/CommentComponent.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { Back } from "@element-plus/icons-vue";
import { View } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";

const props = defineProps({
  id: {
    type: String || Number,
    required: true,
  },
});

const { currReview, getReview, deleteReview, goReviewList } = useReview();
const { isLoggedInMemberPost } = useMember();
const { buttonMsg, resultMsg, openMessage, openConfirmMessageBox } =
  useMessageBox();

const router = useRouter();
const commentKey = ref<number>(0);

onMounted(async () => {
  await getReview(Number(props.id));
});

const deleteBtn = async () => {
  await openConfirmMessageBox(
    resultMsg("review.delete.title"),
    resultMsg("review.delete.content")
  ).then(async () => {
    await deleteReview(Number(props.id)).then(() => {
      openMessage(resultMsg("review.delete.success"));
      goReviewList();
    });
  });
};
</script>

<template>
  <div v-if="currReview.id === Number(id)">
    <el-row class="review">
      <el-col :span="18" class="review-wrapper">
        <TitleSlot>{{ currReview.title }}</TitleSlot>
        <div class="sub">
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
          <div class="flex-grow-1"></div>
          <div class="d-flex justify-content-end">
            <el-button @click="goReviewList" text>
              <el-icon>
                <Back />
              </el-icon>
              {{ buttonMsg("back") }}
            </el-button>
          </div>
        </div>
        <el-divider class="mt-0" />
        <div class="content">
          <QuillEditor
            theme="bubble"
            v-model:content="currReview.content"
            read-only
            contentType="html"
          />
        </div>
        <div class="tags">
          {{ currReview.tags }}
          <el-tag v-for="tag in currReview.tags" :key="tag" size="large">
            {{ `#${tag}` }}
          </el-tag>
        </div>
      </el-col>
      <el-col
        :span="20"
        class="mt-2"
        v-if="isLoggedInMemberPost(currReview.profile.id)"
      >
        <div class="d-flex justify-content-end">
          <el-button
            color="#0f6778"
            @click="router.push({ name: 'edit-review', params: { id: id } })"
          >
            {{ buttonMsg("edit") }}
          </el-button>
          <el-button type="danger" @click="deleteBtn">
            {{ buttonMsg("delete") }}
          </el-button>
        </div>
      </el-col>
    </el-row>
    <el-row class="comments">
      <el-col :span="20" class="d-flex justify-content-center">
        <CommentComponent
          :key="commentKey"
          :content-id="Number(id)"
          :type="'review'"
          :writer="currReview.profile.nickname"
          @refresh="commentKey++"
        />
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.review {
  display: flex;
  justify-content: center;

  .review-wrapper {
    display: flex;
    justify-content: center;
    flex-direction: column;

    .title {
      font-size: 1.8rem;
      font-weight: 600;
      color: #383838;
      margin-bottom: 30px;
    }

    .sub {
      display: flex;
      justify-content: start;
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

    .content {
      margin-bottom: 100px;
    }

    .tags {
      .el-tag {
        border: none;
        background-color: #fafafa;
        font-size: 0.95rem;
        color: #383838;
        font-style: italic;
        font-weight: 400;
      }
    }
  }
}

.comments {
  margin-top: 100px;
  display: flex;
  justify-content: center;
}
</style>
