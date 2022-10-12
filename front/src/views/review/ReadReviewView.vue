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
    type: String,
    required: true,
  },
});

const { currReview, getReview, deleteReview } = useReview();
const { isLoggedInMemberPost } = useMember();
const { buttonMsg, resultMsg, openMessage, openConfirmMessageBox } =
  useMessageBox();

const router = useRouter();
const commentKey = ref<number>(0);

onMounted(async () => {
  await getReview(Number(props.id));
});

const backBtn = () => {
  router.push({ name: "review-list", params: { menu: "review_liveamonth" } });
};

const editBtn = () => {
  console.log("게시글 수정");
  router.push({ name: "edit", params: { id: Number(props.id) } });
};

const deleteBtn = async () => {
  await openConfirmMessageBox(
    resultMsg("review.delete.title"),
    resultMsg("review.delete.content")
  ).then(async () => {
    await deleteReview(Number(props.id));
    openMessage(resultMsg("review.delete.success"));
    backBtn();
  });
};
</script>

<template>
  <div v-if="currReview.id">
    <el-row class="review">
      <el-col :span="20">
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
          <div class="flex-grow-1"></div>
          <div class="d-flex justify-content-end">
            <el-button @click="backBtn" text>
              <el-icon>
                <Back />
              </el-icon>
              {{ buttonMsg("back") }}
            </el-button>
          </div>
        </div>
        <el-divider />
        <div class="content mt-3">
          <QuillEditor
            theme="bubble"
            v-model:content="currReview.content"
            read-only
            contentType="html"
          />
        </div>
      </el-col>
    </el-row>
    <el-row
      v-if="isLoggedInMemberPost(currReview.profile.id)"
      class="mt-3 me-5"
    >
      <el-col>
        <div class="d-flex justify-content-end me-5">
          <el-button color="#0f6778" @click="editBtn">
            {{ buttonMsg("edit") }}
          </el-button>
          <el-button type="danger" @click="deleteBtn">
            {{ buttonMsg("delete") }}
          </el-button>
        </div>
      </el-col>
    </el-row>
    <el-divider />
    <el-row class="comments">
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
  </div>
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
