<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentInput from "@/components/form/CommentInput.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { onMounted, ref } from "vue";
import { usePagination } from "@/composables/common/pagination";
import { useComment } from "@/composables/common/comment";
import { useAuth } from "@/composables/member/auth";
import { useMessageBox } from "@/composables/common/messageBox";
import { useInteraction } from "@/composables/interaction/interaction";
import type {
  CommentEditor,
  CommentReplyType,
  CommentType,
} from "@/modules/types/comment/CommentTypes";
import { useMember } from "@/composables/member/member";

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
  editComment,
  extractIds,
} = useComment();
const { getMemberReactedComment, reactComment } = useInteraction();
const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
const { pageable, mappingPagination, movePage, setSize } =
  usePagination(category);
const { requireLoginMessageBox } = useMessageBox();
const editedComment = ref<CommentType | null>();
const editedReplyComment = ref<CommentReplyType | null>();

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

const submitForm = async (form: CommentEditor, isEdit: boolean) => {
  if (isEdit) {
    console.log("댓글고치기");
    if (editedComment.value) {
      await editComment(props.type, editedComment.value.commentId, form);
      editedComment.value = null;
    }
    if (editedReplyComment.value) {
      await editComment(props.type, editedReplyComment.value.commentId, form);
      editedReplyComment.value = null;
    }
    await getComments(props.type, props.contentId, pageable.value).then(() => {
      mappingPagination(commentPageable.value);
    });
  } else {
    console.log("새댓글");
    await writeComment(props.type, form).then(() => {
      getComments(props.type, props.contentId, pageable.value).then(() => {
        mappingPagination(commentPageable.value);
      });
    });
  }
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

const handleEdit = (comment: CommentType) => {
  editedComment.value = comment;
  window.location.href = "#0";
};

const handleReplyEdit = (comment: CommentReplyType) => {
  editedReplyComment.value = comment;
  window.location.href = `#${comment.parentId}`;
};

const handleDelete = (comment: CommentType) => {
  console.log(comment);
};
const handleReplyDelete = (comment: CommentReplyType) => {
  console.log(comment);
};

const handleCancel = (isReply: boolean) => {
  isReply ? (editedReplyComment.value = null) : (editedComment.value = null);
};
</script>

<template>
  <div class="comment" id="0">
    <TitleSlot class="comment-title">
      {{ $t("comment.title") }}
      {{ `(${commentPageable ? commentPageable.totalElements : "0"})` }}
    </TitleSlot>
    <el-card class="comment-write">
      <SmallTitleSlot class="title">
        {{ $t("comment.write") }}
      </SmallTitleSlot>
      <CommentInput
        :content-id="contentId"
        :is-pending="isPending"
        :edited-comment="editedComment"
        @submit-form="submitForm"
        @cancel="handleCancel(false)"
      />
    </el-card>
    <ul class="comment-list">
      <li v-for="comment in comments" :key="comment.commentId">
        <CommentSlot
          :id="comment.commentId"
          :avatar-url="'/src/assets/image/default.jpg'"
          :is-reply="false"
          :is-writer="writer === comment.profile.nickname"
          :editable="comment.profile.nickname === simpleProfile.nickname"
          @react-comment="react"
          @edit="handleEdit(comment)"
          @delete="handleDelete(comment)"
        >
          <template v-slot:writer>{{ comment.profile.nickname }}</template>
          <template v-slot:elapsedTime>{{ comment.elapsedTime }}</template>
          <template v-slot:comment>{{ comment.comment }}</template>
          <template v-slot:likeCount>{{ comment.likes }}</template>
          <template v-slot:dislikeCount>{{ comment.dislikes }}</template>
        </CommentSlot>
        <el-collapse class="comment-reply">
          <el-collapse-item
            :title="`${$t('comment.reply')}(${comment.commentReplies.length})`"
          >
            <ul>
              <li
                class="reply-list"
                v-for="reply in comment.commentReplies"
                :key="reply.commentId"
              >
                <CommentSlot
                  :id="reply.commentId"
                  :avatar-url="'/src/assets/image/default.jpg'"
                  :is-reply="true"
                  :is-writer="writer === reply.profile.nickname"
                  :editable="reply.profile.nickname === simpleProfile.nickname"
                  @react-comment="react"
                  @edit="handleReplyEdit(reply)"
                  @delete="handleReplyDelete(reply)"
                >
                  <template v-slot:writer>
                    {{ reply.profile.nickname }}
                  </template>
                  <template v-slot:elapsedTime>
                    {{ reply.elapsedTime }}
                  </template>
                  <template v-slot:comment>{{ reply.comment }}</template>
                  <template v-slot:likeCount>{{ reply.likes }}</template>
                  <template v-slot:dislikeCount>{{ reply.dislikes }}</template>
                </CommentSlot>
              </li>
            </ul>
            <div class="mt-2 ms-5">
              <el-divider class="mb-1" />
              <CommentInput
                :id="comment.commentId"
                :parent-id="comment.commentId"
                :content-id="contentId"
                :edited-reply-comment="editedReplyComment"
                :is-pending="isPending"
                @submit-form="submitForm"
                @cancel="handleCancel(true)"
              />
            </div>
          </el-collapse-item>
        </el-collapse>
      </li>
    </ul>
    <CustomPagination :pagination-type="category" @click="pageClick" />
  </div>
</template>

<style lang="scss">
.comment {
  .comment-list {
    list-style: none;
    padding: 0;

    li {
      margin-bottom: 2.5rem;

      &:last-child {
        margin-bottom: 0;
      }

      .el-collapse {
        border: none;
      }

      .comment-reply {
        ul {
          .reply-list {
            list-style: none;
          }
        }
      }
    }

    .el-collapse-item__header,
    .el-collapse-item__wrap {
      border: none;
      background-color: inherit;
    }
  }
}
</style>
