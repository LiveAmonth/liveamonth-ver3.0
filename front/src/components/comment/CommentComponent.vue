<script setup lang="ts">
import CustomPagination from "@/components/common/CustomPagination.vue";
import TitleSlot from "@/components/common/TitleSlot.vue";
import { onMounted } from "vue";
import { usePagination } from "@/composables/pagination";
import { useComment } from "@/composables/comment";
import { useCommentStore } from "@/stores/comment";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
  path: {
    type: String,
    required: true,
    validator(value: string): boolean {
      return value === "schedule" || value === "review";
    },
  },
});
const store = useCommentStore();
const { isPending, comments, getComments } = useComment();

const { pageable, mappingPagination, movePage } = usePagination();

onMounted(async () => {
  await getComments(props.path, Number(props.id), pageable.value).then(() => {
    mappingPagination(store.pageableComments);
  });
});

const pageClick = async (page: number) => {
  movePage(page);
  await getComments(props.path, Number(props.id), pageable.value).then(() => {
    mappingPagination(store.pageableComments);
  });
};
</script>

<template>
  <ul>
    <li v-for="comment in comments" :key="comment.commentId">
      <div class="sub d-flex justify-content-start">
        <div class="avatar">
          <el-avatar :size="25" :src="`/src/assets/image/default.jpg`" />
        </div>
        <div class="writer">
          {{ comment.profile.nickname }}
        </div>
        <div class="regDate">
          {{ comment.elapsedTime }}
        </div>
      </div>
      <div class="content">
        {{ comment.content }}
      </div>
      <el-divider class="mt-3" />
      <ul class="reply">
        <li v-for="reply in comment.commentReplies" :key="reply.commentId">
          <div class="sub d-flex justify-content-start">
            <div class="avatar">
              <el-avatar :size="25" :src="`/src/assets/image/default.jpg`" />
            </div>
            <div class="writer">
              {{ reply.profile.nickname }}
            </div>
            <div class="regDate">
              {{ reply.elapsedTime }}
            </div>
          </div>
          <div class="content">
            {{ reply.content }}
          </div>
          <el-divider class="mt-3" />
        </li>
      </ul>
    </li>
  </ul>
  <CustomPagination @click="pageClick" />
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;

        .view-count {
          font-size: 1rem;
        }
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 1rem;
      margin-top: 8px;
      color: #737373;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 7px;
      font-size: 1rem;

      .avatar {
        margin-left: 0;
        padding-top: 0;
      }

      .writer {
        margin-left: 10px;
        padding-top: 6px;
      }

      .regDate {
        margin-left: 10px;
        padding-top: 6px;
        color: #6b6b6b;
      }
    }

    .reply {
      margin-left: 40px;
    }
  }

}
</style>
