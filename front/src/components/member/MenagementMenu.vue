<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useRouter } from "vue-router";
import { useMyPage } from "@/composables/mypage";
import type { ManagementMenuType } from "@/modules/types/member/MemberType";

defineProps({
  initialMenu: {
    type: String,
    required: false,
    default: "editProfile",
  },
});
const router = useRouter();
const { managementMenu } = useMyPage();
const select = (key: string) => {
  console.log(key);
  const object = JSON.parse(key);
  router.push({
    name: "management",
    params: { category: object.category, menu: object.menu },
  });
};
</script>

<template>
  <el-menu
    active-text-color="#016D7D"
    background-color="#ffffff"
    class="el-menu-vertical-demo"
    :default-active="initialMenu"
    text-color="#111111"
    @select="select"
  >
    <template v-for="(cat, index) in managementMenu" :key="cat">
      <div :class="cat.category.name">
        <SmallTitleSlot :class="`${index ? 'mt-4' : ''}`" :title-line="false">
          <el-icon class="pb-1 me-1">
            <component :is="cat.category.icon" />
          </el-icon>
          {{ $t(`myPage.${cat.category.name}.title`) }}
        </SmallTitleSlot>
        <el-menu-item
          v-for="menu in cat.menus"
          :index="
            JSON.stringify({ category: cat.category.name, menu: menu.value })
          "
          :key="menu.value"
        >
          <span class="ms-2">
            {{ $t(`myPage.${cat.category.name}.${menu.value}`) }}
          </span>
        </el-menu-item>
      </div>
    </template>
  </el-menu>
</template>

<style scoped lang="scss">
.el-menu-item {
  &:hover {
    font-size: 16px;
    color: #fafafa;
    background-color: rgba(108, 153, 163, 0.86);
  }

  &.is-active {
    font-size: 17px;
    font-weight: bold;
  }
}
</style>
