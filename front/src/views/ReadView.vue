<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

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
  viewCount: 0,
});
const router = useRouter();

const moveToEdit = () => {
  router.push({name: "edit", params: {reviewId: props.reviewId}});
}
onMounted(() => {
  axios.get(`/lam-api/reviews/${props.reviewId}`).then((response) => {
    review.value = response.data.data;
  });
})
</script>

<template>
  <h2>{{ review.title }}</h2>
  <div>{{ review.content }}</div>
  <div>{{ review.writer }}</div>
  <div>{{ review.writeDate }}</div>
  <div>{{ review.viewCount }}</div>

  <el-button type="warning" @click="moveToEdit()">
    수정
  </el-button>
</template>
