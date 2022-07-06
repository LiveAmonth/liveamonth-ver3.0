<script setup lang="ts">
import {onMounted, ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";
import dayjs from "dayjs";

const title = ref("");
const content = ref("");
const category = ref("");
const router = useRouter();
const reviewCategories = ref([]);
onMounted(() => {
  axios.get("/lam-api/categories/review").then((res) => {
    reviewCategories.value = res.data.data;
  })
})

const write = function () {
  axios.post("/lam-api/reviews", {
    title: title.value,
    content: content.value,
    category: category.value,
    reviewDateTime: dayjs().format("YYYY-MM-DD HH:mm:ss"),
    writer: "리버먼스"
  }).then(() => {
    router.replace({name: "home"})
  });
}
</script>

<template>
  <div class="d-flex">
    <el-input v-model="title" placeholder="제목을 입력해주세요"/>
    <el-select class="ms-2" v-model="category" placeholder="리뷰 카테고리">
      <el-option
          v-for="item in reviewCategories"
          :key="item.code"
          :label="item.value"
          :value="item.code"
      >
      </el-option>
    </el-select>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"></el-input>
  </div>
  <div class="mt-2">
    <el-button type="primary" @click="write()">글 작성완료</el-button>
  </div>
</template>


<style>

</style>
