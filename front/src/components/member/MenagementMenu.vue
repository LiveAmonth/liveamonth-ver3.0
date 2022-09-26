<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useMyPage } from "@/composables/member/mypage";

defineProps({
  initialMenu: {
    type: String,
    required: false,
    default: "editProfile",
  },
});
const emits = defineEmits(["selectMenu"]);
const { managementMenu } = useMyPage();
const select = (key: string) => {
  emits("selectMenu", key);
};
</script>

<template>
  <el-menu
    active-text-color="#016D7D"
    background-color="#ffffff"
    class="management-menu"
    :default-active="initialMenu"
    text-color="#111111"
    @select="select"
  >
    <template v-for="(cat, index) in managementMenu" :key="cat">
      <div :class="cat.category.code">
        <SmallTitleSlot
          class="ps-2"
          :class="`${index ? 'mt-4' : ''}`"
          :title-line="false"
        >
          <el-icon class="pb-1 me-1">
            <component :is="cat.category.icon" />
          </el-icon>
          {{ $t(`myPage.${cat.category.code}.title`) }}
        </SmallTitleSlot>
        <el-menu-item
          class="menu-item"
          v-for="menu in cat.menus"
          :index="
            JSON.stringify({ category: cat.category.code, menu: menu.value })
          "
          :key="menu.value"
        >
          <span class="ms-2">
            {{ $t(`myPage.${cat.category.code}.${menu.value}`) }}
          </span>
        </el-menu-item>
      </div>
    </template>
  </el-menu>
</template>

<style scoped lang="scss">
.management-menu {
  border: none;
  min-height: 600px;

  .menu-item {
    font-size: 16px;

    &:hover {
      font-size: 18px;
      color: #fafafa;
      background-color: rgba(108, 153, 163, 0.86);
    }

    &.is-active {
      font-size: 20px;
      font-weight: bold;
    }

    span {
      padding-left: 20px;
    }
  }
}
</style>
