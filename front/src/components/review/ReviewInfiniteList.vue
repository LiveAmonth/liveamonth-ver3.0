<script lang="ts" setup>
import { useMember } from "@/composables/member/member";
import { useReview } from "@/composables/review/review";
import { computed, ref } from "vue";
import ReviewListCard from "@/components/review/ReviewListCard.vue";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
});

const { memberProfile } = useMember();
const { myReviews, getMyReviews } = useReview();

const size = ref<number>(2);
const count = ref<number>(props.initialCount);
const loading = ref<boolean>(false);
const noMore = computed(
  () => count.value >= memberProfile.value.numberOfReviews
);
const disabled = computed(() => loading.value || noMore.value);

const load = async () => {
  loading.value = true;
  await getMyReviews(size.value, myReviews.value[count.value - 1].id);
  setTimeout(() => {
    count.value += size.value;
    loading.value = false;
  }, 500);
};
</script>

<template>
  <div class="infinite-list-wrapper" style="overflow: auto">
    <el-row class="d-flex justify-content-center">
      <el-col :span="20">
        <ul
          v-infinite-scroll="load"
          :infinite-scroll-disabled="disabled"
          class="list"
        >
          <li v-for="review in myReviews" :key="review.id" class="list-item">
            <ReviewListCard :review="review" :is-my-page="true" />
            <el-divider />
          </li>
        </ul>
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
.infinite-list-wrapper {
  height: 600px;

  .list {
    padding: 0;
    list-style: none;

    .list-item {
      margin-bottom: 50px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}
</style>
