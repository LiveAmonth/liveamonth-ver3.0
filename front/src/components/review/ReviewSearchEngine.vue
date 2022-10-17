<script lang="ts" setup>
import { ref } from "vue";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";
import { useCategory } from "@/composables/common/category";
import ReviewSearchEngineForm from "@/components/review/ReviewSearchEngineForm.vue";
import { usePagination } from "@/composables/common/pagination";
import type { ReviewSearchType } from "@/modules/types/review/ReviewTypes";

defineProps({
  menu: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(["applyOption", "write"]);
const { isPending, cityReviewTabs, request } = useReview();
const { pageable, setSort } = usePagination("REVIEW");
const { buttonMsg, tabMsg, categoryMsg } = useMessageBox();
const { reviewSearchType, reviewSortType } = useCategory();
const activeName = ref<string>(cityReviewTabs[0]);
const order = ref<string>(pageable.value.sort);

const submitForm = async (form: ReviewSearchType) => {
  form.type = activeName.value;
  request.value.setAttr(form);
  await emits("applyOption");
};

const changeSort = async () => {
  await setSort(order.value);
  await emits("applyOption");
};
</script>

<template>
  <el-tabs
    v-if="menu === reviewSearchType[0].code.toLowerCase()"
    v-model="activeName"
    class="search-tabs"
  >
    <el-tab-pane
      v-for="tab in cityReviewTabs"
      :key="tab"
      :label="tabMsg(`review.${tab.toLowerCase()}`)"
      :name="tab"
    />
  </el-tabs>
  <ReviewSearchEngineForm
    class="mt-3"
    :is-pending="isPending"
    @apply-option="submitForm"
  />

  <div class="sort-write-content">
    <el-radio-group
      class="order-radio"
      v-model="order"
      :text-color="'#0f6778'"
      :fill="'#0f6778'"
      @change="changeSort"
    >
      <el-radio
        v-for="order in reviewSortType"
        :key="order.code"
        :label="order.title"
      >
        {{ categoryMsg("review.sort", order.code.toLowerCase()) }}
      </el-radio>
    </el-radio-group>
    <el-button
      :loading="isPending"
      class="write-btn"
      color="#0f6778"
      style="width: 80px"
      @click="emits('write')"
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

  .el-tabs__header {
    margin-bottom: 0;
  }
}

.sort-write-content {
  margin-top: 30px;
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
