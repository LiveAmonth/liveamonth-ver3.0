<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";
import { useMyPage } from "@/composables/member/mypage";
import type {
  CategoryMenuType,
  MyPageMenuType,
} from "@/modules/types/common/MenuTypes";

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
  const menus: CategoryMenuType<MyPageMenuType> | undefined =
    managementMenu.find((value) => value.category.code === props.category);
  const find: MyPageMenuType | undefined = menus?.menus.find(
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
          :initial-menu="JSON.stringify({ category: category, menu: menu })"
          class="pt-3"
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
<style lang="scss" scoped>
.menu {
  border-right: rgba(194, 192, 192, 0.95) 0.08rem solid;
}
</style>
