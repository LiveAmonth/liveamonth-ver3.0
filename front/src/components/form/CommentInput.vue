<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useAuth } from "@/composables/auth";
import { useComment } from "@/composables/comment";
import { useMessageBox } from "@/composables/messageBox";
import { CommentEditor } from "@/modules/class/comment/CommentEditor";
import type { FormInstance } from "element-plus/es";

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
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const commentForm = reactive<CommentEditor>(new CommentEditor());
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await emit("submitForm", commentForm, props.commentId);
      commentForm.comment = "";
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>
<template>
  <el-form
    ref="ruleFormRef"
    :model="commentForm"
    :rules="commentForm.getRules()"
  >
    <el-row :gutter="5">
      <el-col :span="22">
        <el-form-item>
          <el-input
            v-model="commentForm.comment"
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
            color="#004A55"
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
