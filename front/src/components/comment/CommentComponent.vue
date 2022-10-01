<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentInput from "@/components/form/CommentInput.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { onMounted } from "vue";
import { usePagination } from "@/composables/common/pagination";
import { useComment } from "@/composables/common/comment";
import { useAuth } from "@/composables/member/auth";
import { useMessageBox } from "@/composables/common/messageBox";
import { useInteraction } from "@/composables/interaction/interaction";
import { CommentEditor } from "@/modules/types/comment/CommentTypes";

const props = defineProps({
  contentId: {
    type: Number,
    required: true,
  },
  type: {
    type: String,
    required: true,
    validator(value: string): boolean {
      return value === "schedule" || value === "review";
    },
  },
  writer: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(["refresh"]);
const category = "COMMENT";
const {
  isPending,
  commentPageable,
  comments,
  getComments,
  writeComment,
  extractIds,
} = useComment();
const { getMemberReactedComment, reactComment } = useInteraction();
const { isLoggedIn } = useAuth();
const { pageable, mappingPagination, movePage, setSize } =
  usePagination(category);
const { requireLoginMessageBox } = useMessageBox();

onMounted(async () => {
  setSize(5);
  await getComments(props.type, props.contentId, pageable.value).then(() => {
    mappingPagination(commentPageable.value);
  });
  if (isLoggedIn.value && comments.value.length) {
    await getMemberReactedComment(props.type, extractIds(comments.value));
  }
});

const pageClick = async (page: number) => {
  movePage(page);
  await getComments(props.type, props.contentId, pageable.value).then(() => {
    mappingPagination(commentPageable.value);
  });
};

const submitForm = async (form: CommentEditor) => {
  await writeComment(props.type, form).then(() => {
    getComments(props.type, props.contentId, pageable.value).then(() => {
      mappingPagination(commentPageable.value);
    });
  });
};

const react = async (
  commentId: number,
  option: boolean,
  isReacted: boolean
) => {
  if (isLoggedIn.value) {
    await reactComment(props.type, commentId, option, isReacted);
    await emits("refresh");
  } else {
    await requireLoginMessageBox();
  }
};
</script>

<template>
  <TitleSlot
    >{{ $t("comment.title") }}({{
      commentPageable ? commentPageable.totalElements : "0"
    }})
  </TitleSlot>
  <el-card>
    <SmallTitleSlot>{{ $t("comment.write") }}</SmallTitleSlot>
    <CommentInput
      :content-id="contentId"
      :is-pending="isPending"
      @submit-form="submitForm"
    />
  </el-card>
  <ul>
    <li v-for="comment in comments" :key="comment.commentId">
      <CommentSlot
        :id="comment.commentId"
        :avatar-url="'/src/assets/image/default.jpg'"
        :is-reply="false"
        :is-writer="writer === comment.profile.nickname"
        @react-comment="react"
      >
        <template v-slot:writer>{{ comment.profile.nickname }}</template>
        <template v-slot:elapsedTime>{{ comment.elapsedTime }}</template>
        <template v-slot:content>{{ comment.comment }}</template>
        <template v-slot:likeCount>{{ comment.likes }}</template>
        <template v-slot:dislikeCount>{{ comment.dislikes }}</template>
      </CommentSlot>
      <el-collapse>
        <el-collapse-item
          :title="`${$t('comment.reply')}(${comment.commentReplies.length})`"
        >
          <ul class="reply">
            <li v-for="reply in comment.commentReplies" :key="reply.commentId">
              <CommentSlot
                :id="reply.commentId"
                :avatar-url="'/src/assets/image/default.jpg'"
                :is-reply="true"
                :is-writer="writer === reply.profile.nickname"
                @react-comment="react"
              >
                <template v-slot:writer>{{ reply.profile.nickname }}</template>
                <template v-slot:elapsedTime>{{ reply.elapsedTime }}</template>
                <template v-slot:content>{{ reply.comment }}</template>
                <template v-slot:likeCount>{{ reply.likes }}</template>
                <template v-slot:dislikeCount>{{ reply.dislikes }}</template>
              </CommentSlot>
              <el-divider class="mt-1 mb-0" />
            </li>
          </ul>
          <div class="mt-2 ms-5">
            <CommentInput
              :content-id="contentId"
              :comment-id="comment.commentId"
              :is-pending="isPending"
              @submit-form="submitForm"
            />
          </div>
        </el-collapse-item>
      </el-collapse>
    </li>
  </ul>
  <CustomPagination :pagination-type="category" @click="pageClick" />
</template>

<style lang="scss" scoped>
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;

        .view-count {
          font-size: 1rem;
        }
      }

      &:hover {
        text-decoration: underline;
      }
    }

    &:last-child {
      margin-bottom: 0;
    }

    .reply {
      margin-left: 40px;
    }
  }
}

.el-collapse {
  border: none;

  > .el-collapse-item__header {
    background-color: #004a55;
    display: none;
    border: none;
  }

  .el-collapse-item__content {
    border: none;
  }
}
</style>
