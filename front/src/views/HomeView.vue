<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";

const reviews = ref([]);
onMounted(() => {
  axios.post(`/lam-api/reviews/search?sort=id,desc`).then((response) => {
    reviews.value = response.data.data.content;
  });
})

</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id">
      <div>
        {{ review.title }}
      </div>
      <div>
        {{ review.content }}
      </div>
      <div>
        {{ review.writer }}
      </div>
      <div>
        {{ review.writeDate }}
      </div>
    </li>
  </ul>
</template>

<style scoped>
li {
  margin-bottom: 1rem;
}

li:last-child {
  margin-bottom: 0;

}
</style>
