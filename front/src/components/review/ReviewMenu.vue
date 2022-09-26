<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useCity } from "@/composables/city/city";

const { cityNames } = useCity();
</script>

<template>
  <el-menu
    active-text-color="#016D7D"
    background-color="#ffffff"
    class="review-menu"
    text-color="#111111"
  >
    <SmallTitleSlot :title-line="false" class="ps-2">
      <el-icon class="pb-1 me-1">
        <Place />
        <!--            <component :is="cat.category.icon" />-->
      </el-icon>
      <!--      {{ $t(`myPage.${cat.category.code}.title`) }}-->
      도시
    </SmallTitleSlot>
    <el-menu-item v-for="city in cityNames" :key="city.code" class="menu-item">
      <span class="ms-2">{{ city.value }}</span>
    </el-menu-item>
  </el-menu>
</template>

<style lang="scss" scoped>
.review-menu {
  border: none;
  min-height: 600px;
  background-color: inherit;

  .menu-item {
    font-size: 1rem;
    background-color: inherit;

    &:hover {
      font-size: 1.1rem;
      background-color: rgba(108, 153, 163, 0.86);
    }

    &.is-active {
      font-size: 1.1rem;
      font-weight: bold;
    }

    span {
      padding-left: 20px;
    }
  }
}
</style>
