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

const { managementMenu } = useMyPage();

const findComponent = (): object | undefined => {
  const menus: ManagementMenuType | undefined = managementMenu.find(
    (value) => value.category.name === props.category
  );
  const find: MenuType | undefined = menus?.menus.find(
    (value1) => value1.value === props.menu
  );
  return find?.component;
};
</script>
<template>
  <TitleSlot class="mb-3"> 내 정보</TitleSlot>
  <el-card :body-style="{ padding: 0 }">
    <el-row :gutter="5">
      <el-col :span="4">
        <ManagementMenu
          class="pt-3"
          :initial-menu="JSON.stringify({ category: category, menu: menu })"
        />
      </el-col>
      <el-col :span="20" class="content">
        <component :is="findComponent()"></component>
      </el-col>
    </el-row>
  </el-card>
</template>
<style scoped lang="scss">
.el-menu-item {
  &:hover {
    background-color: #6c99a3d3;
  }
}
</style>
