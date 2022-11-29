<script lang="ts" setup>
import ReviewListCard from "@/components/review/ReviewListCard.vue";
import { useReview } from "@/composables/review/review";
import type { ReviewListType } from "@/modules/types/review/ReviewTypes";

const props = defineProps({
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const { bestReviews, otherReviews, hasBestReviews, hasOtherReviews } =
  useReview();
const hasData: boolean = props.isMain
  ? hasBestReviews.value
  : hasOtherReviews.value;
const reviews: ReviewListType[] = props.isMain
  ? bestReviews.value
  : otherReviews.value;
</script>

<template>
  <ul v-if="hasData">
    <li v-for="(review, idx) in reviews" :key="review.id">
      <ReviewListCard :review="review" :rank="isMain ? idx + 1 : 0" />
      <el-divider />
    </li>
  </ul>
</template>

<style lang="scss" scoped>
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    &:last-child {
      margin-bottom: 0;
    }
  }
}
</style>
