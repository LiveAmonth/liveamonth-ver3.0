<script lang="ts" setup>
import { reactive, ref, watch } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useMessageBox } from "@/composables/common/messageBox";
import { CommentEditor } from "@/modules/types/comment/CommentTypes";
import type { FormInstance } from "element-plus/es";
import { useComment } from "@/composables/common/comment";

const props = defineProps({
  isEdit: {
    type: Boolean,
    required: true,
  },
  isReply: {
    type: Boolean,
    required: false,
    default: false,
  },
  contentId: {
    type: Number,
    required: true,
  },
  parentId: {
    type: Number,
    required: false,
    default: null,
  },
});
const emits = defineEmits(["submitForm", "cancel"]);

const { editableComment } = useComment();
const { isLoggedIn } = useAuth();
const {
  openMessageBox,
  buttonMsg,
  labelMsg,
  resultMsg,
  inputPhMsg,
  beforeWritePhMsg,
} = useMessageBox();
const form = reactive<CommentEditor>(
  new CommentEditor(props.contentId, props.parentId)
);
const ruleFormRef = ref<FormInstance>();
const editable = ref(props.isEdit);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await emits("submitForm", form, !props.isEdit);
      form.clear();
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};

watch(
  () => props.isEdit,
  (value) => {
    if (props.isEdit) {
      if (props.isReply && props.parentId == editableComment.value.parentId) {
        form.setForm(editableComment.value);
      }
      if (!props.isReply && !editableComment.value.parentId) {
        form.setForm(editableComment.value);
      }
    } else {
      form.clear();
    }
    editable.value = value;
  }
);
</script>
<template>
  <el-form ref="ruleFormRef" :model="form" :rules="form.getRules()">
    <div class="d-flex justify-content-start">
      <el-form-item>
        <el-input
          class="comment-input"
          v-model="form.comment"
          :input-style="{ minWidth: isReply ? `700px` : `750px` }"
          :disabled="!isLoggedIn"
          :placeholder="
            isLoggedIn
              ? inputPhMsg(labelMsg('title'))
              : beforeWritePhMsg(labelMsg('title'))
          "
          :rows="3"
          type="textarea"
        >
        </el-input>
      </el-form-item>
      <el-button
        class="ms-2"
        :disabled="!isLoggedIn || !form.comment"
        color="#0f6778"
        @click="submitForm(ruleFormRef)"
      >
        {{ isEdit ? buttonMsg("edit") : buttonMsg("write") }}
      </el-button>
      <el-button
        v-if="editable"
        class="ms-1"
        :disabled="!isLoggedIn"
        color="#0f6778"
        @click="emits('cancel')"
      >
        {{ buttonMsg("cancel") }}
      </el-button>
    </div>
  </el-form>
</template>
<style lang="scss" scoped>
.el-button {
  height: 73px;
}
</style>
