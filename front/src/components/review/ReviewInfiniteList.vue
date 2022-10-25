<script lang="ts" setup>
import ReviewListCard from "@/components/review/ReviewListCard.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { Right } from "@element-plus/icons-vue";
import { useMember } from "@/composables/member/member";
import { useReview } from "@/composables/review/review";
import { computed, ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["refresh"]);

const { memberProfile } = useMember();
const { myReviews, hasMyReviews, getMyReviews, deleteReview } = useReview();
const { labelMsg, buttonMsg } = useMessageBox();

const size = ref<number>(2);
const count = ref<number>(
  myReviews.value.length < props.initialCount
    ? myReviews.value.length
    : props.initialCount
);
const loading = ref<boolean>(false);
const noMore = computed(
  () => count.value >= memberProfile.value.numberOfReviews
);
const disabled = computed(() => loading.value || noMore.value);

const load = async () => {
  loading.value = true;
  await getMyReviews(
    memberProfile.value.loginId,
    size.value,
    myReviews.value[count.value - 1].id
  );
  setTimeout(() => {
    count.value =
      myReviews.value.length - count.value < size.value
        ? myReviews.value.length
        : count.value + size.value;
    loading.value = false;
  }, 500);
};

const handleDelete = async (reviewId: number) => {
  await deleteReview(reviewId);
  await getMyReviews(memberProfile.value.loginId, props.initialCount, null);
  memberProfile.value.numberOfReviews--;
  count.value--;
  loading.value = false;
  emits("refresh");
};
</script>

<template>
  <div v-if="hasMyReviews" class="infinite-list-wrapper" style="overflow: auto">
    <el-row class="d-flex justify-content-center">
      <el-col :span="20">
        <ul
          v-infinite-scroll="load"
          :infinite-scroll-disabled="disabled"
          class="list"
        >
          <li v-for="review in myReviews" :key="review.id" class="list-item">
            <ReviewListCard
              :review="review"
              :is-my-page="isMyPage"
              @delete-review="handleDelete(review.id)"
            />
            <el-divider />
          </li>
        </ul>
      </el-col>
    </el-row>
  </div>
  <div v-else class="empty-post">
    <SmallTitleSlot
      class="d-flex justify-content-center"
      :title="labelMsg('review.empty')"
      :title-line="false"
    />
    <LinkSlot class="link" link="/reviews/write">
      <span>
        {{ buttonMsg("review.go") }}
      </span>
      <el-icon>
        <Right />
      </el-icon>
    </LinkSlot>
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

.empty-post {
  display: flex;
  justify-content: center;
  flex-direction: column;
  color: #808080;

  .link {
    display: flex;
    justify-content: center;
    margin-top: 5px;
    color: #016d7d;
  }
}
</style>
