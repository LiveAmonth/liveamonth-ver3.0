<script lang="ts" setup>
import { reactive, ref, watch, getCurrentInstance } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useComment } from "@/composables/common/comment";
import { useMessageBox } from "@/composables/common/messageBox";
import { CommentEditor } from "@/modules/types/comment/CommentTypes";
import type {
  CommentType,
  CommentReplyType,
} from "@/modules/types/comment/CommentTypes";
import type { PropType } from "vue";
import type { FormInstance } from "element-plus/es";

const props = defineProps({
  contentId: {
    type: Number,
    required: true,
  },
  parentId: {
    type: Number,
    required: false,
    default: 0,
  },
  isPending: {
    type: Boolean,
    required: true,
  },
  editedComment: {
    type: Object as PropType<CommentType> | null,
    required: false,
  },
  editedReplyComment: {
    type: Object as PropType<CommentReplyType> | null,
    required: false,
  },
});
const emits = defineEmits(["submitForm", "cancel"]);

const { isLoggedIn } = useAuth();
const { isPending } = useComment();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();
const isEdit = ref<boolean>(false);
const form = reactive<CommentEditor>(
  new CommentEditor(props.contentId, props.parentId)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await emits("submitForm", form, isEdit.value);
      form.clear();
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};

watch(
  () => props.editedComment,
  (value) => {
    isEdit.value = props.parentId == 0 && !!value;
    value ? form.setForm(value) : form.clear();
  }
);

watch(
  () => props.editedReplyComment,
  (value) => {
    isEdit.value = props.parentId != 0 && !!value;
    if (value) {
      props.parentId == value.parentId ? form.setForm(value) : form.clear();
    }
  }
);
</script>
<template>
  <el-form ref="ruleFormRef" :model="form" :rules="form.getRules()">
    <el-row :gutter="5">
      <el-col :span="isEdit ? 21 : 22">
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
      <el-col :span="isEdit ? 3 : 2">
        <el-form-item class="d-flex justify-content-center">
          <el-button
            :disabled="!isLoggedIn"
            :loading="isPending"
            color="#0f6778"
            :size="isEdit ? 'default' : 'large'"
            @click="submitForm(ruleFormRef)"
          >
            {{ isEdit ? buttonMsg("edit") : buttonMsg("write") }}
          </el-button>
          <el-button
            v-if="isEdit"
            class="ms-1"
            :disabled="!isLoggedIn"
            :loading="isPending"
            color="#0f6778"
            size="default"
            @click="emits('cancel')"
          >
            {{ buttonMsg("cancel") }}
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
