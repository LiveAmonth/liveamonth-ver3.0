<script lang="ts" setup>
import CustomPagination from "@/components/common/CustomPagination.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentInput from "@/components/form/CommentInput.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { onMounted } from "vue";
import { usePagination } from "@/composables/pagination";
import { useComment } from "@/composables/comment";
import { useCommentStore } from "@/stores/comment";
import type { CommentFormType } from "@/modules/types/form/FormType";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
  path: {
    type: String,
    required: true,
    validator(value: string): boolean {
      return value === "schedule" || value === "review";
    },
  },
});
const store = useCommentStore();
const { isPending, comments, commentsCount, getComments, writeComment } =
  useComment();
const { pageable, mappingPagination, movePage } = usePagination();

onMounted(async () => {
  await getComments(props.path, Number(props.id), pageable.value).then(() => {
    mappingPagination(store.pageableComments);
  });
});

const pageClick = async (page: number) => {
  movePage(page);
  await getComments(props.path, Number(props.id), pageable.value).then(() => {
    mappingPagination(store.pageableComments);
  });
};

const submitForm = async (form: CommentFormType, commentId = 0) => {
  await writeComment(props.path, Number(props.id), commentId, form).then(() => {
    getComments(props.path, Number(props.id), pageable.value).then(() => {
      mappingPagination(store.pageableComments);
    });
  });
};
</script>

<template>
  <TitleSlot>{{ $t("comment.title") }}({{ commentsCount }})</TitleSlot>
  <el-card>
    <SmallTitleSlot>{{ $t("comment.write") }}</SmallTitleSlot>
    <CommentInput :is-pending="isPending" @submit-form="submitForm" />
  </el-card>
  <ul>
    <li v-for="comment in comments" :key="comment.commentId">
      <CommentSlot
        :avatar-url="'/src/assets/image/default.jpg'"
        :is-reply="false"
      >
        <template v-slot:writer>{{ comment.profile.nickname }}</template>
        <template v-slot:elapsedTime>{{ comment.elapsedTime }}</template>
        <template v-slot:content>{{ comment.content }}</template>
      </CommentSlot>
      <el-collapse>
        <el-collapse-item
          :title="`${$t('comment.reply')}(${comment.commentReplies.length})`"
        >
          <ul>
            <li v-for="reply in comment.commentReplies" :key="reply.commentId">
              <CommentSlot
                :avatar-url="'/src/assets/image/default.jpg'"
                :is-reply="true"
              >
                <template v-slot:writer>{{ reply.profile.nickname }}</template>
                <template v-slot:elapsedTime>{{ reply.elapsedTime }}</template>
                <template v-slot:content>{{ reply.content }}</template>
              </CommentSlot>
              <el-divider class="mt-1 mb-0" />
            </li>
          </ul>
          <div class="mt-2 ms-5">
            <CommentInput
              :comment-id="comment.commentId"
              :is-pending="isPending"
              @submit-form="submitForm"
            />
          </div>
        </el-collapse-item>
      </el-collapse>
    </li>
  </ul>
  <CustomPagination @click="pageClick" />
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
