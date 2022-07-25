<script setup lang="ts">
import { useAuthStore } from "@/stores/auth";
import { computed } from "vue";
const store = useAuthStore();

const loggedIn = computed((): boolean => store.loggedIn);
</script>

<template>
  <div class="common-layout">
    <el-row>
      <el-col style="background-color: #383838"> asd</el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="18" style="background-color: #004a55"> aasd</el-col>
      <el-col :span="6">
        <el-card class="d-flex justify-content-center">
          <el-row>
            <el-col>

            </el-col>
          </el-row>
          <el-avatar :src="`/src/assets/image/default.jpg`" :size="100">
          </el-avatar>
          <el-row class="mt-3" :gutter="10">
            <el-col :span="10">
              제목 :
            </el-col>
            <el-col :span="14">
              내용
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="10">
              제목 :
            </el-col>
            <el-col :span="14">
              내용
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="10">
              제목 :
            </el-col>
            <el-col :span="14">
              내용
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss"></style>
