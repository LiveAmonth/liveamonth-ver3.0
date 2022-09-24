<script setup lang="ts">
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

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
  writeDateTime: "",
  viewCount: 0,
});

const router = useRouter();

const moveToEdit = () => {
  router.push({ name: "edit", params: { reviewId: props.reviewId } });
};
onMounted(() => {
  axios.get(`/reviews/${props.reviewId}`).then((response) => {
    review.value = response.data.data;
  });
});
</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ review.title }}</h2>
      <div class="sub d-flex">
        <div class="writer">
          {{ review.writer }}
        </div>
        <div class="regDate">
          {{ review.writeDateTime }}
        </div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ review.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()"> 수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 8px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub {
  margin-top: 5px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}
</style>
