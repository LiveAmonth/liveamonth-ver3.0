<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const reviews = ref([]);
onMounted(() => {
  axios.post(`/lam-api/reviews/search?sort=id,desc`).then((response) => {
    reviews.value = response
        .data.data.content;
  });
})

</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id">
      <div>
        <router-link :to="{name:'read',params:{reviewId: review.id}}">
          {{ review.title }}
        </router-link>
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
