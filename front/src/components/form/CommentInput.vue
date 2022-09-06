<script lang="ts" setup>
import { useAuth } from "@/composables/auth";
import { useComment } from "@/composables/comment";
import { useFormValidate } from "@/composables/formValidate";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus/es";
import type { CommentFormType } from "@/modules/types/form/FormType";

const props = defineProps({
  commentId: {
    type: Number,
    required: false,
  },
  isPending: {
    type: Boolean,
    required: true,
  },
});
const emit = defineEmits(["submitForm"]);

const { isLoggedIn } = useAuth();
const { isPending } = useComment();
const { validateRequire, validateRange } = useFormValidate();
const { openMessageBox } = useMessageBox();

const { t } = useI18n();

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

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await emit("submitForm", commentForm, props.commentId);
      commentForm.comment = "";
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>
<template>
  <el-form ref="ruleFormRef" :model="commentForm" :rules="rules">
    <el-row :gutter="5">
      <el-col :span="22">
        <el-form-item>
          <el-input
            v-model="commentForm.comment"
            :disabled="!isLoggedIn"
            :placeholder="
              isLoggedIn
                ? $t('common.please-input', {
                    field: $t('comment.title'),
                  })
                : $t('common.before-input', {
                    field: $t('comment.title'),
                  })
            "
            :rows="3"
            type="textarea"
          >
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="2">
        <el-form-item>
          <el-button
            :disabled="!isLoggedIn"
            :loading="isPending"
            color="#004A55"
            size="large"
            @click="submitForm(ruleFormRef)"
            >{{ $t("common.submit") }}
          </el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<style lang="scss" scoped>
.el-button {
  height: 73px;
}
</style>
