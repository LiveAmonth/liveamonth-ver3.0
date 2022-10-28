<script lang="ts" setup>
import ReviewListCard from "@/components/review/ReviewListCard.vue";
import { useReview } from "@/composables/review/review";

defineProps({
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const { otherReviews } = useReview();
</script>

<template>
  <ul v-if="otherReviews">
    <li v-for="(review, idx) in otherReviews" :key="review.id">
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
