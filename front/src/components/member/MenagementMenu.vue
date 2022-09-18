<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useRouter } from "vue-router";
import { useMember } from "@/composables/member";

defineProps({
  initialMenu: {
    type: String,
    required: false,
    default: "editProfile",
  },
});

const router = useRouter();
const { managementMenu } = useMember();
const select = (key: string) => {
  router.push({ name: "management", params: { menu: key } });
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
      <div :class="cat[0].category">
        <SmallTitleSlot :class="`${index ? 'mt-4' : ''}`" :title-line="false">
          <el-icon class="pb-1 me-1">
            <component :is="cat[0].category.icon" />
          </el-icon>
          {{ $t(`myPage.${cat[0].category.cat}.title`) }}
        </SmallTitleSlot>
        <el-menu-item v-for="menu in cat" :index="menu.value" :key="menu.value">
          <span class="ms-2">
            {{ $t(`myPage.${menu.category.cat}.${menu.value}`) }}
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
