<script lang="ts" setup>
import ReviewMenu from "@/components/review/ReviewMenu.vue";
import ReviewSearchEngine from "@/components/review/ReviewSearchEngine.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import ReviewList from "@/components/review/ReviewList.vue";
import { onMounted, watch } from "vue";
import { useCategory } from "@/composables/common/category";
import { usePagination } from "@/composables/common/pagination";
import { useReview } from "@/composables/review/review";
import { useRoute, useRouter } from "vue-router";
import { useMessageBox } from "@/composables/common/messageBox";
import { useAuth } from "@/composables/member/auth";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";

const props = defineProps({
  menu: {
    type: String,
    required: true,
  },
});

const category = "REVIEW";
const { isLoggedIn } = useAuth();
const { hasReviewCategory, getReviewCategories } = useCategory();
const { pageable, mappingPagination, movePage } = usePagination(category);
const {
  isPending,
  request,
  reviewPage,
  recommendationTags,
  getReviews,
  getRecommendationTags,
} = useReview();
const { labelMsg, requireLoginMessageBox } = useMessageBox();
const route = useRoute();
const router = useRouter();

onMounted(async () => {
  request.value.type = props.menu.toUpperCase();
  await getReviewCategories();
  await settingReviews();
  await getRecommendationTags();
});

const pageClick = async (page: number) => {
  movePage(page);
  await settingReviews();
};

const settingReviews = async () => {
  await getReviews(pageable.value);
  mappingPagination(reviewPage.value);
};

const goWriteReview = () => {
  const writeReviewPath = "/reviews/write";
  if (isLoggedIn.value) {
    router.push({ path: writeReviewPath });
  } else {
    requireLoginMessageBox(writeReviewPath);
  }
};

const searchTag = (tag: string) => {
  request.value.pushTag(tag);
  pageClick(1);
};

watch(
  () => props.menu,
  async (value) => {
    request.value.type = value.toUpperCase();
    if (route.query) {
      await router.replace(route.path);
    }
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
            @write="goWriteReview"
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
      <el-card>
        <SmallTitleSlot :title="labelMsg('review.recommendation')" />
        <el-tag
          v-for="tag in recommendationTags"
          :key="tag"
          class="me-1"
          @click="searchTag(tag.name)"
        >
          {{ `#${tag.name}` }}
        </el-tag>
      </el-card>
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

  .el-tag {
    color: #0f6778;
    background: none;
    border: none;
    font-size: 0.85em;
    cursor: pointer;
    margin: 7px 0;

    &:hover {
      font-weight: 600;
    }
  }
}
</style>
