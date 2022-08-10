<script setup lang="ts">
import { computed } from "vue";

const props = defineProps({
  numberOfPages: {
    type: Number,
    required: true,
  },
  currentPage: {
    type: Number,
    required: true,
  },
  pageLimit: {
    type: Number,
    required: true,
  },
  contentLimit: {
    type: Number,
    required: true,
  },
  pageGroup: {
    type: Number,
    required: true,
  },
});
const emit = defineEmits(["click"]);
const onClick = (page: number) => {
  emit("click", page);
};
const numberOfPageGroup = computed(() => {
  return Math.ceil(props.numberOfPages / props.pageLimit);
});
</script>

<template>
  <div class="paging">
    <ul class="paging_point">
      <template v-if="currentPage > pageLimit">
        <li class="paging-side">
          <button class="btn-paging first" @click="onClick(1)" type="button">
            처음
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging prev"
            @click="onClick((pageGroup - 1) * pageLimit + 1)"
            type="button"
          >
            이전 5개
          </button>
        </li>
      </template>
      <li
        v-for="page in pageGroup !== numberOfPageGroup - 1
          ? pageLimit
          : numberOfPages & pageLimit"
        :key="page"
        :class="currentPage % pageLimit === page ? 'on' : ''"
      >
        <a
          @click="onClick(page + pageGroup * pageLimit)"
          :title="page + pageGroup * pageLimit + '페이지 선택'"
          style="cursor: pointer"
        >
          {{ page + pageGroup * pageLimit }}</a
        >
      </li>
      <template v-if="pageGroup !== numberOfPageGroup - 1">
        <li class="paging-side">
          <button
            class="btn-paging next"
            @click="onClick((pageGroup + 1) * pageLimit + 1)"
            type="button"
          >
            다음 5개
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging end"
            @click="onClick((numberOfPageGroup - 1) * pageLimit + 1)"
            type="button"
          >
            끝
          </button>
        </li>
      </template>
    </ul>
  </div>
</template>

<style scoped></style>
