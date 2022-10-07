<script lang="ts" setup>
import { Search, Refresh } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useSearch } from "@/composables/search/search";

defineProps({
  isPending: {
    type: Boolean,
    required: true,
  },
});
const emits = defineEmits(["applyOption"]);

const {
  dynamicTags,
  tagInput,
  tagInputVisible,
  showInput,
  handleClose,
  handleInputConfirm,
  clearTags,
} = useSearch();
const { buttonMsg } = useMessageBox();
const input = ref<string>();
const submitForm = () => {
  emits("applyOption", input.value, dynamicTags.value);
};
</script>

<template>
  <el-form-item class="form-item">
    <el-row class="search-row">
      <el-col :span="20" class="mx-2">
        <el-input v-model="input" :prefix-icon="Search" size="large" />
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
    <el-row class="tag-row">
      <el-col :span="20" class="mx-2">
        <el-tag
          v-for="tag in dynamicTags"
          :key="tag"
          class="me-1"
          size="large"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
        >
          {{ `#${tag}` }}
        </el-tag>
        <el-input
          v-if="tagInputVisible"
          ref="InputRef"
          v-model="tagInput"
          class="w-25"
          @keyup.enter="handleInputConfirm"
          @blur="handleInputConfirm"
        />
        <el-button
          v-else
          class="button-new-tag ml-1"
          size="small"
          @click="showInput"
        >
          {{ buttonMsg("newTag") }}
        </el-button>
      </el-col>
      <el-col :span="3">
        <el-button size="large" @click="clearTags" text>
          <el-icon>
            <Refresh />
          </el-icon>
          {{ buttonMsg("reset") }}
        </el-button>
      </el-col>
    </el-row>
  </el-form-item>
</template>

<style lang="scss" scoped>
.form-item {
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
}
</style>
