<script lang="ts" setup>
defineProps({
  avatarUrl: {
    type: String,
    required: true,
  },
  isReply: {
    type: Boolean,
    required: true,
  },
});

const like = (e: Event) => {
  console.log(e.path[0]);
};
const unLike = (e) => {
  console.log(e);
};
</script>
<template>
  <div class="sub d-flex justify-content-start">
    <i v-if="isReply" class="bi bi-arrow-return-right me-2"></i>
    <div :class="`avatar ${isReply ? 'pt-1' : ''}`">
      <el-avatar :size="25" :src="avatarUrl" />
    </div>
    <div class="writer">
      <slot name="writer"></slot>
    </div>
    <div :class="`elapsedTime ${isReply ? 'pb-1' : 'pb-2'}`">
      <slot name="elapsedTime"></slot>
    </div>
    <div class="like ms-2">
      <i class="bi bi-hand-thumbs-up me-2" @click="like"> </i>
      <i class="bi bi-hand-thumbs-down" @click="unLike"></i>
    </div>
  </div>
  <div class="content">
    <slot name="content"></slot>
  </div>
</template>

<style lang="scss" scoped>
.sub {
  margin-top: 7px;
  font-size: 1rem;
  align-items: center;

  .avatar {
    margin-left: 0;
  }

  .writer {
    margin-left: 10px;
  }

  .elapsedTime {
    margin-left: 10px;
    color: #6b6b6b;
    font-size: 0.7rem;
  }

  .like {
    font-size: 0.85rem;

    .bi-hand-thumbs-up:hover,
    .bi-hand-thumbs-down:hover {
      cursor: pointer;
    }
  }
}

.content {
  font-size: 1rem;
  margin-top: 8px;
  color: #737373;
}
</style>
