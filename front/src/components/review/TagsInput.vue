<script lang="ts" setup>
import { Refresh } from "@element-plus/icons-vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useSearch } from "@/composables/search/search";
import type { PropType } from "vue";

const props = defineProps({
  dynamicTags: {
    type: Array as PropType<Array<string>>,
    required: true,
  },
});
const emits = defineEmits(["pushTag", "handleClose", "clear"]);

const {
  tagInput,
  tagInputVisible,
  showInput,
  validationCheck,
  duplicationCheck,
} = useSearch();
const { buttonMsg } = useMessageBox();

const handelInputConfirm = () => {
  if (
    tagInput.value &&
    duplicationCheck(props.dynamicTags, tagInput.value) &&
    validationCheck(tagInput.value) &&
    props.dynamicTags.length <= 10
  ) {
    emits("pushTag", tagInput.value);
  }
  tagInputVisible.value = false;
  tagInput.value = "";
};
</script>

<template>
  <el-row class="tag-row">
    <el-col :span="20" class="mx-2">
      <el-tag
        v-for="tag in dynamicTags"
        :key="tag"
        class="me-1"
        size="large"
        closable
        :disable-transitions="false"
        @close="emits('handleClose', tag)"
      >
        {{ `#${tag}` }}
      </el-tag>
      <el-input
        v-if="tagInputVisible"
        ref="InputRef"
        v-model="tagInput"
        class="w-25"
        @keyup.enter="handelInputConfirm"
        @blur="handelInputConfirm"
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
      <el-button class="reset-btn" size="large" @click="emits('clear')" text>
        <el-icon>
          <Refresh />
        </el-icon>
        {{ buttonMsg("reset") }}
      </el-button>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
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
</style>
