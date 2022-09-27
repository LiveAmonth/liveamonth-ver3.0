<script lang="ts" setup>
import ReviewSearch from "@/modules/class/review/ReviewSearch";
import { Search, Refresh } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import { useReview } from "@/composables/review/review";
import { ElInput } from "element-plus";
import { useSearch } from "@/composables/search/search";
import { useMessageBox } from "@/composables/common/messageBox";

const { isPending, reviewSearchTabs } = useReview();
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

const activeName = ref<string>(reviewSearchTabs[0].code);
const order = ref<string>("id,desc");
const form = reactive<ReviewSearch>(new ReviewSearch(activeName.value));

const submitForm = () => {
  console.log(form.input);
  console.log(form.type);
};

const clear = () => {
  form.clear();
  clearTags();
};
</script>

<template>
  <el-form :model="form" class="mb-4">
    <el-tabs v-model="activeName" class="search-tabs" @tab-click="clear">
      <el-tab-pane
        v-for="tab in reviewSearchTabs"
        :key="tab.code"
        :label="tab.value"
        :name="tab.code"
      >
        <el-form-item class="form-item">
          <el-row class="search-row">
            <el-col :span="20" class="mx-2">
              <el-input
                v-model="form.input"
                :prefix-icon="Search"
                size="large"
              />
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
                {{ tag }}
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
              <el-button :loading="isPending" size="large" @click="clear" text>
                <el-icon>
                  <Refresh />
                </el-icon>
                {{ buttonMsg("reset") }}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-tab-pane>
    </el-tabs>
  </el-form>

  <el-radio-group
    class="order-radio"
    v-model="order"
    :text-color="'#0f6778'"
    :fill="'#0f6778'"
  >
    <el-radio label="id,desc">최신순</el-radio>
    <el-radio label="comment,desc">댓글많은순</el-radio>
    <el-radio label="like,desc">좋아요순</el-radio>
    <el-radio label="view,desc">조회순</el-radio>
  </el-radio-group>
  <el-divider class="mt-0 mb-2" />
</template>

<style lang="scss">
.search-tabs {
  .el-tabs__item {
    &.is-active,
    &:hover {
      color: #006778 !important;
      font-size: 1rem;
    }
  }

  .el-tabs__active-bar {
    background-color: #006778 !important;
  }

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
  }
}

.order-radio {
  .el-radio__input.is-checked {
    & + .el-radio__label {
      color: #006778;
    }

    & .el-radio__inner {
      background-color: #006778;
      border-color: #006778;
    }
  }
}
</style>
