<script lang="ts" setup>
import TagsInput from "@/components/review/TagsInput.vue";
import { Search } from "@element-plus/icons-vue";
import { reactive } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import type { ReviewSearchType } from "@/modules/types/review/ReviewTypes";

const emits = defineEmits(["applyOption"]);

const { buttonMsg } = useMessageBox();

const form = reactive<ReviewSearchType>({
  searchWord: "",
  tags: [],
  type: "",
  category: "",
});

const clear = () => {
  form.searchWord = "";
  form.tags = [];
};

const submitForm = () => {
  emits("applyOption", form);
};

const pushTag = (tag: string) => {
  form.tags.push(tag);
};

const handleClose = (tag: string) => {
  form.tags.splice(form.tags.indexOf(tag), 1);
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
      :dynamic-tags="form.tags"
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
}
</style>
