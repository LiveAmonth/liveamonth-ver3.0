<script lang="ts" setup>
import ReviewMenu from "@/components/review/ReviewMenu.vue";
import ReviewSearchEngine from "@/components/review/ReviewSearchEngine.vue";
import { onMounted, watch } from "vue";
import { useCategory } from "@/composables/common/category";
import ReviewList from "@/components/review/ReviewList.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import { usePagination } from "@/composables/common/pagination";
import { useReview } from "@/composables/review/review";

const props = defineProps({
  menu: {
    type: String,
    required: true,
  },
});
const category = "REVIEW";
const { hasReviewCategory, getReviewCategories } = useCategory();
const { pageable, mappingPagination, movePage } = usePagination(category);
const { isPending, request, reviewPage, getReviews } = useReview();

onMounted(async () => {
  request.value.type = props.menu.toUpperCase();
  await getReviewCategories();
  await settingReviews();
});

const pageClick = async (page: number) => {
  movePage(page);
  await settingReviews();
};

const settingReviews = async () => {
  await getReviews(pageable.value);
  mappingPagination(reviewPage.value);
};

watch(
  () => props.menu,
  async (value) => {
    request.value.type = value.toUpperCase();
    movePage(1);
    await settingReviews();
  }
);
</script>

<template>
  <el-row :gutter="40" class="container">
    <el-col :span="4">
      <ReviewMenu v-if="hasReviewCategory" :initial-menu="menu" />
    </el-col>
    <el-col :span="16" class="main-content">
      <el-row>
        <el-col :span="22">
          <ReviewSearchEngine
            v-if="hasReviewCategory"
            :menu="menu"
            @apply-option="pageClick(1)"
          />
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <ReviewList v-if="!isPending" />
        </el-col>
      </el-row>
      <CustomPagination :pagination-type="category" @click="pageClick" />
    </el-col>
    <el-col :span="4" class="px-0">
      <el-card> 추천 태그</el-card>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: start;

  .main-content {
    display: flex;
    flex-direction: column;
  }
}
</style>
