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
import { useMember } from "@/composables/member/member";
import { useMessageBox } from "@/composables/common/messageBox";
import { useInteraction } from "@/composables/interaction/interaction";
import type {
  CommentEditor,
  CommentType,
} from "@/modules/types/comment/CommentTypes";

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
  error,
  commentPageable,
  comments,
  editableComment,
  getComments,
  writeComment,
  editComment,
  deleteComment,
  setEditableComment,
  extractIds,
} = useComment();
const { getMemberReactedComment, reactComment } = useInteraction();
const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
const { pageable, mappingPagination, movePage, setSize } =
  usePagination(category);
const {
  resultMsg,
  openWarningMessageByCode,
  openConfirmMessageBox,
  requireLoginMessageBox,
} = useMessageBox();

const isEdit = ref<boolean>(false);
const isReplyEdit = ref<boolean>(false);

onMounted(async () => {
  setSize(5);
  await settingComments();
  if (isLoggedIn.value && comments.value.length) {
    await getMemberReactedComment(props.type, extractIds(comments.value));
  }
});

const settingComments = async () => {
  await getComments(props.type, props.contentId, pageable.value);
  mappingPagination(commentPageable.value);
};

const pageClick = async (page: number) => {
  movePage(page);
  await settingComments();
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

const handleEdit = (comment: CommentType, isReply = false) => {
  if (isAlreadyEdit()) {
    openWarningMessageByCode("validation.comment.alreadyEdit");
  } else {
    isReply ? setEditInput(true, `#${comment.commentId}`) : setEditInput();
    setEditableComment(comment);
  }
};

const handleDelete = async (commentId: number) => {
  if (isAlreadyEdit()) {
    openWarningMessageByCode("validation.comment.alreadyEdit");
  } else {
    await openConfirmMessageBox(
      resultMsg("comment.delete.title"),
      resultMsg("comment.delete.message")
    ).then(async () => {
      await deleteComment(props.type, commentId);
    });
  }
  if (!error.value) {
    await settingComments();
  }
};

const submitForm = async (form: CommentEditor, isNew: boolean) => {
  isNew
    ? await writeComment(props.type, form)
    : await editComment(props.type, editableComment.value.commentId, form);
  if (!error.value) {
    await settingComments();
    isEdit.value = false;
    isReplyEdit.value = false;
  }
};

const handleCancel = (isReply = false) => {
  isReply ? (isReplyEdit.value = false) : (isEdit.value = false);
};

const isAlreadyEdit = () => {
  return isReplyEdit.value || isEdit.value;
};

const setEditInput = (isReply = false, id = "#0") => {
  if (isReply) {
    isReplyEdit.value = true;
  } else {
    isEdit.value = true;
  }
  window.location.href = id;
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
        :is-edit="isEdit"
        @submit-form="submitForm"
        @cancel="handleCancel"
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
          @delete="handleDelete(comment.commentId)"
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
                  @edit="handleEdit(reply, true)"
                  @delete="handleDelete(reply.commentId)"
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
                :content-id="contentId"
                :parent-id="comment.commentId"
                :is-edit="isReplyEdit"
                :is-reply="true"
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
