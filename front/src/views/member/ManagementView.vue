<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";
import { useMyPage } from "@/composables/member/mypage";
import { useMessageBox } from "@/composables/common/messageBox";

defineProps({
  category: {
    type: String,
    required: true,
  },
  menu: {
    type: String,
    required: true,
  },
});

const { goManagement, findComponent } = useMyPage();
const { titleMsg } = useMessageBox();
</script>
<template>
  <TitleSlot class="mb-3" :title="titleMsg('member.myPage')" />
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
        <component :is="findComponent(category, menu)"></component>
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
