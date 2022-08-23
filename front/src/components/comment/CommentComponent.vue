<script setup lang="ts">
import CustomPagination from "@/components/common/CustomPagination.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { onMounted, reactive, ref } from "vue";
import { usePagination } from "@/composables/pagination";
import { useComment } from "@/composables/comment";
import { useCommentStore } from "@/stores/comment";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useAuthStore } from "@/stores/auth";
import { useAuth } from "@/composables/auth";
import { Search } from "@element-plus/icons-vue";
import type { FormInstance } from "element-plus/es";
import type { FormRules } from "element-plus/es";
import type { CommentFormType } from "@/modules/types/form/FormType";
import { useFormValidate } from "@/composables/formValidate";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";

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
const router = useRouter();
const store = useCommentStore();
const { isLoggedIn } = useAuth();
const { isPending, error, comments, commentsCount, getComments, writeComment } =
  useComment();
const { pageable, mappingPagination, movePage } = usePagination();
const { validateRequire, validateRange } = useFormValidate();
const { openMessageBox } = useMessageBox();
const { t } = useI18n();

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

const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  comment: [
    validateRequire("comment.title"),
    validateRange("comment.title", 0, 1000),
  ],
});
const commentForm = reactive<CommentFormType>({
  comment: "",
});

const submitForm = async (formEl: FormInstance | undefined, commentId = 0) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await writeComment(props.path, Number(props.id), commentId, commentForm);
      if (error.value) {
        await openMessageBox(t("form.message.wrongIdPw"));
      } else {
        await getComments(props.path, Number(props.id), pageable.value).then(
          () => {
            mappingPagination(store.pageableComments);
          }
        );
      }
      commentForm.comment = "";
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <TitleSlot>댓글({{ commentsCount }})</TitleSlot>
  <el-card>
    <SmallTitleSlot>댓글 쓰기</SmallTitleSlot>
    <el-form ref="ruleFormRef" :model="commentForm" :rules="rules" status-icon>
      <el-form-item>
        <el-input
          v-model="commentForm.comment"
          type="textarea"
          :disabled="!isLoggedIn"
          :rows="3"
          :placeholder="
            isLoggedIn
              ? '댓글을 작성해주세요'
              : '댓글을 작성하려면 로그인해주세요.'
          "
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          :loading="isPending"
          color="#004A55"
          size="large"
          style="width: 100%"
          @click="submitForm(ruleFormRef)"
          >Submit
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <ul>
    <li v-for="comment in comments" :key="comment.commentId">
      <div class="sub d-flex justify-content-start">
        <div class="avatar">
          <el-avatar :size="25" :src="`/src/assets/image/default.jpg`" />
        </div>
        <div class="writer">
          {{ comment.profile.nickname }}
        </div>
        <div class="regDate">
          {{ comment.elapsedTime }}
        </div>
      </div>
      <div class="content">
        {{ comment.content }}
      </div>
      <el-collapse>
        <el-collapse-item
          :title="`댓글의 댓글(${comment.commentReplies.length})`"
        >
          <ul class="reply mb-0">
            <li v-for="reply in comment.commentReplies" :key="reply.commentId">
              <div class="sub d-flex justify-content-start">
                <i class="bi bi-arrow-return-right me-2 mt-1"></i>
                <div class="avatar">
                  <el-avatar
                    :size="25"
                    :src="`/src/assets/image/default.jpg`"
                  />
                </div>
                <div class="writer">
                  {{ reply.profile.nickname }}
                </div>
                <div class="regDate">
                  {{ reply.elapsedTime }}
                </div>
              </div>
              <div class="content">
                {{ reply.content }}
              </div>
              <el-divider class="mt-1 mb-0" />
            </li>
          </ul>
          <div class="ms-5">
            <el-form
              ref="ruleFormRef"
              :model="commentForm"
              :rules="rules"
              status-icon
            >
              <el-form-item class="mb-0">
                <el-input
                  v-model="commentForm.comment"
                  type="textarea"
                  :disabled="!isLoggedIn"
                  class="w-50 m-2"
                  :rows="2"
                  :placeholder="
                    isLoggedIn
                      ? '댓글을 작성해주세요'
                      : '댓글을 작성하려면 로그인해주세요.'
                  "
                >
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  class="ms-2"
                  :loading="isPending"
                  color="#004A55"
                  @click="submitForm(ruleFormRef, comment.commentId)"
                  >Submit
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-collapse-item>
      </el-collapse>
    </li>
  </ul>
  <CustomPagination @click="pageClick" />
</template>

<style scoped lang="scss">
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

    .content {
      font-size: 1rem;
      margin-top: 8px;
      color: #737373;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 7px;
      font-size: 1rem;

      .avatar {
        margin-left: 0;
        padding-top: 0;
      }

      .writer {
        margin-left: 10px;
        padding-top: 6px;
      }

      .regDate {
        margin-left: 10px;
        padding-top: 6px;
        color: #6b6b6b;
      }
    }

    .reply {
      margin-left: 40px;
    }
  }
}

.el-input {
  height: 50px;
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
