<script lang="ts" setup>
import { useRouter } from "vue-router";
import { useReview } from "@/composables/review/review";
import { useMessageBox } from "@/composables/common/messageBox";

defineProps({
  initialMenu: {
    type: String,
    required: true,
  },
});

const router = useRouter();
const { reviewMenus } = useReview();
const { menuMsg } = useMessageBox();

const selectMenu = (key: string) => {
  router.push({ name: "review-list", params: { menu: key } });
};
</script>

<template>
  <el-menu
    active-text-color="#016d7d"
    background-color="#ffffff"
    class="review-menu"
    text-color="#111111"
    :default-active="initialMenu"
    @select="selectMenu"
  >
    <template v-for="cat in reviewMenus" :key="cat.category.code">
      <h5 class="menu-title mb-0 py-0 mt-3">
        <el-icon class="me-1">
          <component :is="cat.category.icon" />
        </el-icon>
        {{ cat.category.value }}
      </h5>
      <el-menu-item
        v-for="menu in cat.menus"
        :key="menu.name"
        :index="menu.name"
        class="menu-item"
      >
        <span class="my-0">{{ menuMsg(`board.${menu.name}`) }}</span>
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
    color: #878787;
  }

  .menu-item {
    font-size: 1.1rem;
    background-color: inherit;
    height: 40px;

    &:hover {
      font-size: 1.5rem;
      background-color: rgba(108, 153, 163, 0.86);
      color: #fafafa;
    }

    &.is-active {
      font-size: 1.5rem;
    }

    span {
      height: 30px;
      padding-bottom: 25px;
      padding-left: 10px;
    }
  }
}
</style>
