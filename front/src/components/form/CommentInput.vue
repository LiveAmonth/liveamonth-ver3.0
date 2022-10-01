<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useComment } from "@/composables/common/comment";
import { useMessageBox } from "@/composables/common/messageBox";
import { CommentEditor } from "@/modules/types/comment/CommentTypes";
import type { FormInstance } from "element-plus/es";

const props = defineProps({
  contentId: {
    type: Number,
    required: true,
  },
  commentId: {
    type: Number,
    required: false,
    default: null,
  },
  isPending: {
    type: Boolean,
    required: true,
  },
});
const emit = defineEmits(["submitForm"]);

const { isLoggedIn } = useAuth();
const { isPending } = useComment();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const form = reactive<CommentEditor>(
  new CommentEditor(props.contentId, props.commentId)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await emit("submitForm", form);
      form.clear();
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>
<template>
  <el-form ref="ruleFormRef" :model="form" :rules="form.getRules()">
    <el-row :gutter="5">
      <el-col :span="22">
        <el-form-item>
          <el-input
            v-model="form.comment"
            :disabled="!isLoggedIn"
            :placeholder="
              isLoggedIn
                ? $t('common.please-input', {
                    field: labelMsg('title'),
                  })
                : $t('common.before-input', {
                    field: labelMsg('title'),
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
            color="#0f6778"
            size="large"
            @click="submitForm(ruleFormRef)"
            >{{ buttonMsg("write") }}
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
