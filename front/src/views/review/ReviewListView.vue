<script setup lang="ts">
import { ref } from "vue";

const reviews = ref([
  {
    id: 1,
    title: "제목",
    viewCount: 2,
    content: "내용입니다@#!@#!@#!@#!@#!@#",
    writer: "리버먼스",
    writeDate: "2022-09-14",
  },
  {
    id: 2,
    title: "제목",
    viewCount: 2,
    content: "내용입니다@#!@wfw#!@#!@#!@#!@#",
    writer: "리버먼스",
    writeDate: "2022-09-14",
  },
  {
    id: 3,
    title: "제목",
    viewCount: 2,
    content: "내용입니다@#!@#!@#!fsadfsadf@#!@#!@#",
    writer: "리버먼스",
    writeDate: "2022-09-14",
  },
  {
    id: 4,
    title: "제목",
    viewCount: 2,
    content: "내용입니다@#!@#!sadfasd@#!@#!@#!@#",
    writer: "리버먼스",
    writeDate: "2022-09-14",
  },
  {
    id: 5,
    title: "제목",
    viewCount: 2,
    content: "내용입니다@#!wefwefwefwefwef@#!@#!@#!@#!@#",
    writer: "리버먼스",
    writeDate: "2022-09-14",
  },
]);
</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id">
      <div class="title">
        <router-link :to="{ name: 'read-review', params: { id: review.id } }">
          {{ review.title }}
          <span class="view-count"> ({{ review.viewCount }}) </span>
        </router-link>
      </div>
      <div class="content">
        {{ review.content }}
      </div>
      <div class="sub d-flex">
        <div class="writer">
          {{ review.writer }}
        </div>
        <div class="regDate">
          {{ review.writeDate }}
        </div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;

        .view-count {
          font-size: 1rem;
        }
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      color: #737373;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 7px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>
