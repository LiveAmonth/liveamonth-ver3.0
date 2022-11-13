<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentInput from "@/components/comment/CommentInput.vue";
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
import BestComment from "@/components/comment/BestComment.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import { useRoute } from "vue-router";

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
const route = useRoute();
const {
  error,
  commentPageable,
  comments,
  hasBestComments,
  editableComment,
  getComments,
  getBestComments,
  writeComment,
  editComment,
  deleteComment,
  setEditableComment,
  extractIds,
} = useComment();
const { getInteractedCommentsByMember, interactComment } = useInteraction();
const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
const { pageable, mappingPagination, movePage, setSize } =
  usePagination(category);
const {
  resultMsg,
  titleMsg,
  buttonMsg,
  openWarningMessageByCode,
  openConfirmMessageBox,
  requireLoginMessageBox,
} = useMessageBox();

const isEdit = ref<boolean>(false);
const isReplyEdit = ref<boolean>(false);

onMounted(async () => {
  setSize(5);
  await settingComments();
  await getBestComments(props.type, props.contentId);
  if (isLoggedIn.value && comments.value.length) {
    await getInteractedCommentsByMember(props.type, extractIds(comments.value));
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

const interact = async (
  commentId: number,
  option: boolean,
  isInteracted: boolean
) => {
  if (isLoggedIn.value) {
    await interactComment(props.type, commentId, option, isInteracted);
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
  <div id="0" class="comment">
    <SmallTitleSlot
      :title="
        titleMsg(
          'comment.count',
          commentPageable ? commentPageable.totalElements : '0'
        )
      "
      class="comment-title"
    >
    </SmallTitleSlot>
    <el-card class="comment-write" :body-style="{ paddingBottom: 0 }">
      <BestComment v-if="hasBestComments" :writer="writer" />
      <div class="d-flex justify-content-start">
        <SmallTitleSlot :title="titleMsg('comment.write')" class="title mt-1" />
        <LinkSlot
          v-if="!isLoggedIn"
          class="ms-2"
          :label="buttonMsg('member.goLogin')"
          :link="`/login/?returnUrl=${route.path}`"
        />
      </div>
      <CommentInput
        :content-id="contentId"
        :is-edit="isEdit"
        @cancel="handleCancel"
        @submit-form="submitForm"
      />
    </el-card>
    <ul class="comment-list">
      <li v-for="comment in comments" :key="comment.commentId">
        <CommentSlot
          :comment="comment"
          :editable="comment.profile.nickname === simpleProfile.nickname"
          :is-reply="false"
          :is-writer="writer === comment.profile.nickname"
          @delete="handleDelete(comment.commentId)"
          @edit="handleEdit(comment)"
          @interact-comment="interact"
        />
        <el-collapse class="comment-reply">
          <el-collapse-item
            :title="titleMsg('comment.reply', comment.commentReplies.length)"
          >
            <ul>
              <li
                v-for="reply in comment.commentReplies"
                :key="reply.commentId"
                class="reply-list"
              >
                <CommentSlot
                  :comment="reply"
                  :editable="reply.profile.nickname === simpleProfile.nickname"
                  :is-reply="true"
                  :is-writer="writer === reply.profile.nickname"
                  @delete="handleDelete(reply.commentId)"
                  @edit="handleEdit(reply, true)"
                  @interact-comment="interact"
                />
              </li>
            </ul>
            <CommentInput
              class="comment-reply-input"
              :content-id="contentId"
              :is-edit="isReplyEdit"
              :is-reply="true"
              :parent-id="comment.commentId"
              @cancel="handleCancel(true)"
              @submit-form="submitForm"
            />
          </el-collapse-item>
        </el-collapse>
      </li>
    </ul>
    <CustomPagination :pagination-type="category" @click="pageClick" />
  </div>
</template>

<style lang="scss">
.comment {
  display: flex;
  justify-content: center;
  flex-direction: column;

  .comment-write {
    width: fit-content;
  }

  .comment-list {
    max-width: 870px;
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
        width: inherit;
        margin-top: 15px;

        ul {
          margin-bottom: 30px;

          .reply-list {
            list-style: none;
          }
        }

        .comment-reply-input {
          padding-left: 40px;
          padding-top: 20px;
          border-top: 1px solid #e7e9eb;
        }
      }
    }

    .el-collapse-item__header,
    .el-collapse-item__wrap {
      border: none;
      background-color: #fafafa;
    }
  }
}
</style>
