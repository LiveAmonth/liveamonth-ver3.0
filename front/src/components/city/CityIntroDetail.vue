<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useCityStore } from "@/stores/city";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
const props = defineProps({
  name: {
    type: String,
    required: true,
  },
});
const store = useCityStore();

const cityIntroDetail = ref<ImageContentType[]>();

onMounted(async () => {
  await store.setCity(props.name);
  console.log("디테일 : ",props.name);
  cityIntroDetail.value = store.introDetail;
});
</script>

<template>
  <el-row>
    <template v-for="introDetail in cityIntroDetail" :key="introDetail">
      <el-col :span="6">
        <el-image
          style="width: auto; height: auto; box-shadow: var(--el-box-shadow)"
          :src="`/src/assets/image/intro/${introDetail.image}`"
          fit="cover"
        />
      </el-col>
      <el-col :span="18">
        <div class="content">
          {{ introDetail.content }}
        </div>
      </el-col>
    </template>
  </el-row>
</template>

<style scoped lang="scss">
.content {
  font-size: 0.95rem;
  margin-left: 10px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.8;
}
</style>
