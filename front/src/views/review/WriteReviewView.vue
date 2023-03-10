<script lang="ts" setup>
import TagsInput from "@/components/review/TagsInput.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { reactive, ref } from "vue";
import { ReviewEditor } from "@/modules/types/review/ReviewTypes";
import { useCategory } from "@/composables/common/category";
import { useMessageBox } from "@/composables/common/messageBox";
import { useQuillEditor } from "@/composables/common/quilleditor";
import { useReview } from "@/composables/review/review";
import type { FormInstance } from "element-plus/es";
import { useMember } from "@/composables/member/member";

const { reviewCategory } = useCategory();
const { simpleProfile } = useMember();
const { addedReviewId, addReview, goReadReview, goReviewList } = useReview();
const { toolbarOptions, onEditorReady } = useQuillEditor();
const {
  resultMsg,
  buttonMsg,
  labelMsg,
  inputPhMsg,
  selectPhMsg,
  openMessageBox,
} = useMessageBox();

const form = reactive<ReviewEditor>(new ReviewEditor());
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await addReview(simpleProfile.value.loginId, form)
        .then(() => {
          openMessageBox(resultMsg("review.write.success"));
          goReadReview(addedReviewId.value);
        })
        .catch(() => {
          openMessageBox(resultMsg("review.write.exception"));
          goReviewList();
        });
    }
  });
};

const pushTag = (tag: string) => {
  form.tags.push(tag);
};

const handleClose = (tag: string) => {
  form.tags.splice(form.tags.indexOf(tag), 1);
};
</script>

<template>
  <el-row class="d-flex justify-content-center">
    <el-col :span="20">
      <el-row class="d-flex justify-content-center mt-3">
        <el-col :span="18">
          <el-form
            ref="ruleFormRef"
            :model="form"
            :rules="form.getRules()"
            status-icon
          >
            <div class="title-items">
              <SmallTitleSlot :title="labelMsg('title')" />
              <el-input
                class="title-input"
                :placeholder="inputPhMsg(labelMsg('title'))"
                v-model="form.title"
              />
            </div>
            <div class="category-items">
              <SmallTitleSlot class="category-title">
                {{ labelMsg("category") }}
              </SmallTitleSlot>
              <el-select
                class="category-select"
                v-model="form.category"
                :placeholder="selectPhMsg(labelMsg('category'))"
              >
                <el-option
                  v-for="item in reviewCategory"
                  :key="item.code"
                  :label="item.value"
                  :value="item.code"
                />
              </el-select>
            </div>
            <div class="tags-items">
              <SmallTitleSlot>
                {{ labelMsg("review.tags") }}
                <span class="title-span">
                  ({{ labelMsg("review.tags-pl") }})
                </span>
              </SmallTitleSlot>
              <TagsInput
                :dynamic-tags="form.tags"
                @push-tag="pushTag"
                @handle-close="handleClose"
                @clear="form.tags = []"
              />
            </div>
            <el-form-item>
              <div class="quill-editor">
                <QuillEditor
                  theme="snow"
                  v-model:content="form.content"
                  :toolbar="toolbarOptions"
                  contentType="html"
                  @ready="onEditorReady($event, '', false)"
                />
              </div>
            </el-form-item>
          </el-form>
          <div class="d-flex justify-content-end">
            <el-button size="large" @click="submitForm(ruleFormRef)">
              {{ buttonMsg("write") }}
            </el-button>
          </div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.title-items {
  margin-bottom: 25px;

  .title-input {
    height: 50px;
    font-size: 1.5rem;
  }
}

.category-items {
  margin-bottom: 25px;

  .category-title {
    display: block;
  }

  .category-select {
    width: 250px;
  }
}

.tags-items {
  .title-span {
    font-size: 20px;
    color: #8d8d8d;
  }

  margin-bottom: 25px;
}
</style>
