<script lang="ts" setup>
import { View } from "@element-plus/icons-vue";
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import { useReview } from "@/composables/review/review";

const props = defineProps({
  id: {
    type: [Number, String],
    required: true,
  },
});

const { currReview, getReview } = useReview();
const router = useRouter();

onMounted(async () => {
  await getReview(Number(props.id));
});

const moveToEdit = () => {
  router.push({ name: "edit", params: { id: props.id } });
};
</script>

<template>
  <el-row v-if="currReview.id">
    <el-col>
      <h2 class="title">{{ currReview.title }}</h2>
      <div class="sub d-flex">
        <div class="avatar">
          <el-avatar :size="20" :src="'/src/assets/image/default.jpg'" />
        </div>
        <div class="writer">
          {{ currReview.profile.nickname }}
        </div>
        <div class="regDate">
          {{ currReview.elapsedTime }}
        </div>
        <div class="views">
          <el-icon class="me-1">
            <View />
          </el-icon>
          {{ $comma(currReview.numberOfHits) }}
        </div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ review.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()"> 수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 8px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub {
  margin-top: 5px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}
</style>
