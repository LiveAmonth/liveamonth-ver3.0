<script lang="ts" setup>
import ReviewMenu from "@/components/review/ReviewMenu.vue";
import ReviewSearch from "@/components/review/ReviewSearch.vue";
import { onMounted } from "vue";
import { useCategory } from "@/composables/common/category";

defineProps({
  menu: {
    type: String,
    required: true,
  },
});

const {
  getReviewCategory,
  getReviewSearchType,
  getReviewSortType,
  hasReviewCategories,
} = useCategory();
onMounted(async () => {
  if (!hasReviewCategories()) {
    await getReviewCategory();
    await getReviewSearchType();
    await getReviewSortType();
  }
});
</script>

<template>
  <el-row :gutter="40" class="container">
    <el-col :span="4">
      <ReviewMenu v-if="hasReviewCategories" :initial-menu="menu" />
    </el-col>
    <el-col :span="16" class="main-content">
      <el-row>
        <el-col :span="22">
          <ReviewSearch v-if="hasReviewCategories" />
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <RouterView />
        </el-col>
      </el-row>
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
