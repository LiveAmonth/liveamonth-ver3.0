<script lang="ts" setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";
import { useCategory } from "@/composables/common/category";

defineProps({
  initialMenu: {
    type: String,
    required: true,
  },
});

const router = useRouter();
const { request, getReviewMenu } = useReview();
const { reviewMenuGroup } = useCategory();
const { menuMsg } = useMessageBox();
const selectMenu = (key: string) => {
  request.value.tags = [];
  router.replace({ name: "review-list", params: { menu: key } });
};
const reviewMenu = ref([
  getReviewMenu(reviewMenuGroup.value[0], "Place"),
  getReviewMenu(reviewMenuGroup.value[1], "Paperclip"),
]);
</script>

<template>
  <el-menu
    active-text-color="#006778"
    background-color="#ffffff"
    class="review-menu"
    text-color="#111111"
    :default-active="initialMenu"
    @select="selectMenu"
  >
    <template v-for="group in reviewMenu" :key="group.category.code">
      <h5 class="menu-title mb-0 py-0 mt-3">
        <el-icon class="me-1">
          <component :is="group.category.icon" />
        </el-icon>
        {{ group.category.value }}
      </h5>
      <el-menu-item
        v-for="menu in group.menus"
        :key="menu.name"
        :index="menu.name"
        class="menu-item"
      >
        <span class="my-0">{{ menuMsg(`review.${menu.name}`) }}</span>
      </el-menu-item>
    </template>
  </el-menu>
</template>

<style lang="scss" scoped>
.review-menu {
  font-family: "Do hyeon", sans-serif;
  border: none;
  background-color: inherit;

  .menu-title {
    display: flex;
    justify-content: start;
    font-size: 0.9rem;
    color: #a2a1a1;
  }

  .menu-item {
    font-size: 1.1rem;
    background-color: inherit;
    height: 40px;

    &:hover {
      font-size: 1.25rem;
      background-color: rgba(108, 153, 163, 0.86);
      color: #fafafa;
    }

    &.is-active {
      font-size: 1.25rem;
    }

    span {
      height: 30px;
      padding-bottom: 25px;
      padding-left: 10px;
    }
  }
}
</style>
