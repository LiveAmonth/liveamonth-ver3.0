<script setup lang="ts">
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const reviewCategories = ref([]);
onMounted(() => {
  axios.get("/categories/review").then((res) => {
    reviewCategories.value = res.data.data;
  });

  axios.get(`/reviews/${props.reviewId}`).then((response) => {
    review.value = response.data.data;
  });
});

const props = defineProps({
  reviewId: {
    type: [Number, String],
    required: true,
  },
});

const review = ref({
  id: 0,
  title: "",
  content: "",
  writer: "",
  writeDate: "",
  reviewCategory: {
    code: "",
    value: "",
  },
  viewCount: 0,
  reviewDateTime: "",
});

const edit = () => {
  axios
    .patch(`/lam-api/reviews/${props.reviewId}`, {
      title: review.value.title,
      reviewCategory: review.value.reviewCategory.code,
      content: review.value.content,
    })
    .then(() => {
      router.replace({ name: "home" });
    });
};
</script>

<template>
  <div class="d-flex">
    <el-input v-model="review.title" />
    <el-select
      class="ms-2"
      v-model="review.reviewCategory"
      :placeholder="review.reviewCategory.value"
    >
      <el-option
        v-for="item in reviewCategories"
        :key="item.code"
        :label="item.value"
        :value="item"
      >
      </el-option>
    </el-select>
  </div>

  <div class="mt-2">
    <el-input v-model="review.content" type="textarea" rows="15"></el-input>
  </div>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>
