<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";
import { useMyPage } from "@/composables/mypage";
import type {
  ManagementMenuType,
  MenuType,
} from "@/modules/types/member/MemberType";

const props = defineProps({
  menu: {
    type: String,
    required: true,
  },
  category: {
    type: String,
    required: true,
  },
});

const { managementMenu, goManagement } = useMyPage();

const findComponent = (): object | undefined => {
  const menus: ManagementMenuType | undefined = managementMenu.find(
    (value) => value.category.code === props.category
  );
  const find: MenuType | undefined = menus?.menus.find(
    (value1) => value1.value === props.menu
  );
  return find?.component;
};
</script>
<template>
  <TitleSlot class="mb-3"> 내 정보</TitleSlot>
  <el-card :body-style="{ padding: 0, minHeight: '600px' }">
    <el-row class="d-flex justify-content-around">
      <el-col :span="6" class="menu">
        <ManagementMenu
          class="pt-3"
          :initial-menu="JSON.stringify({ category: category, menu: menu })"
          @select-menu="goManagement"
        />
      </el-col>
      <el-col :span="2"></el-col>
      <el-col :span="14" class="content">
        <component :is="findComponent()"></component>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>
  </el-card>
</template>
<style scoped lang="scss">
.menu {
  border-right: rgba(194, 192, 192, 0.95) 0.08rem solid;
}
</style>
