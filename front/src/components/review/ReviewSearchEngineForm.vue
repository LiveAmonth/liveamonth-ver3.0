<script lang="ts" setup>
import TagsInput from "@/components/review/TagsInput.vue";
import { Search } from "@element-plus/icons-vue";
import { reactive } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useSearch } from "@/composables/search/search";
import type { ReviewSearchType } from "@/modules/types/review/ReviewTypes";

defineProps({
  isPending: {
    type: Boolean,
    required: true,
  },
});
const emits = defineEmits(["applyOption"]);

const { dynamicTags, pushTag, handleClose, clearTags } = useSearch();
const { buttonMsg } = useMessageBox();

const form = reactive<ReviewSearchType>({
  searchWord: null,
  tags: dynamicTags.value,
  type: null,
  category: null,
});

const clear = () => {
  form.searchWord = "";
  clearTags();
};

const submitForm = () => {
  emits("applyOption", form);
};
</script>

<template>
  <el-form :model="form" class="form">
    <el-row class="search-row">
      <el-col :span="20" class="mx-2">
        <el-form-item>
          <el-input
            v-model="form.searchWord"
            :prefix-icon="Search"
            size="large"
          />
        </el-form-item>
      </el-col>
      <el-col :span="2" class="ms-2">
        <el-button
          :loading="isPending"
          size="large"
          color="#0f6778"
          style="width: 80px"
          @click="submitForm"
        >
          {{ buttonMsg("search") }}
        </el-button>
      </el-col>
    </el-row>
    <TagsInput
      :dynamic-tags="dynamicTags"
      @push-tag="pushTag"
      @handle-close="handleClose"
      @clear="clear"
    />
  </el-form>
</template>

<style lang="scss" scoped>
.form {
  display: flex;
  flex-direction: column;

  .el-row {
    display: flex;
    justify-content: start;
    width: 100%;
  }

  .search-row {
    margin-bottom: 5px;
  }

  .tag-row {
    .el-tag {
      --el-tag-bg-color: rgba(16, 120, 118, 0.15);
      --el-tag-border-color: rgba(101, 157, 168, 0.83);
      --el-tag-hover-color: #0f6778;
      --el-tag-text-color: #0f6778;
      color: #0f6778;
      background-color: rgba(16, 120, 118, 0.15);
      border-color: rgba(101, 157, 168, 0.83);
    }

    .button-new-tag {
      border: none;

      &:focus,
      &:hover {
        color: #0f6778;
        background-color: rgba(16, 120, 118, 0.15);
      }
    }

    .reset-btn {
      margin-left: 3px;
      margin-top: -5px;
    }
  }
}
</style>
