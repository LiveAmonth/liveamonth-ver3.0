<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useReview } from "@/composables/review/review";
import { useSearch } from "@/composables/search/search";
import { useMessageBox } from "@/composables/common/messageBox";
import { useCategory } from "@/composables/common/category";
import { ReviewSearchCond } from "@/modules/types/review/ReviewTypes";
import ReviewSearchEngineForm from "@/components/review/ReviewSearchEngineForm.vue";

defineProps({
  menu: {
    type: String,
    required: true,
  },
});
const { clearTags } = useSearch();
const { isPending, cityReviewTabs } = useReview();
const { buttonMsg, tabMsg } = useMessageBox();
const { reviewSearchType } = useCategory();
const activeName = ref<string>(cityReviewTabs[0]);
const order = ref<string>("id,desc");
const form = reactive<ReviewSearchCond>(new ReviewSearchCond());

const submitForm = (word: string, tags: string[]) => {
  console.log("word: ", word);
  console.log("tags: ", tags);
};

const writeReview = () => {
  console.log("글쓰기");
};
</script>

<template>
  <el-form :model="form" class="mb-4">
    <el-tabs
      v-if="menu === reviewSearchType[0].code.toLowerCase()"
      v-model="activeName"
      class="search-tabs"
      @tab-click="clearTags"
    >
      <el-tab-pane
        v-for="tab in cityReviewTabs"
        :key="tab"
        :label="tabMsg(`review.${tab}`)"
        :name="tab"
      >
        <ReviewSearchEngineForm
          :is-pending="isPending"
          @apply-option="submitForm"
        />
      </el-tab-pane>
    </el-tabs>
    <ReviewSearchEngineForm
      v-else
      class="mt-3"
      :is-pending="isPending"
      @apply-option="submitForm"
    />
  </el-form>

  <div class="sort-write-content">
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
    <el-button
      :loading="isPending"
      class="write-btn"
      color="#0f6778"
      style="width: 80px"
      @click="writeReview"
    >
      {{ buttonMsg("write") }}
    </el-button>
  </div>

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
}

.sort-write-content {
  display: flex;
  justify-content: space-between;

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

  .write-btn {
    margin-bottom: 5px;
    margin-right: 5px;
  }
}
</style>
