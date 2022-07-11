<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";

const reviews = ref([]);
onMounted(() => {
  axios.post(`/reviews/search?sort=id,desc`).then((response) => {
    reviews.value = response.data.data.content;
  });
});
</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id">
      <div class="title">
        <router-link :to="{ name: 'read', params: { reviewId: review.id } }">
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
