<script lang="ts" setup>
import ImageIcon from "@/components/common/ImageIcon.vue";
import { useReview } from "@/composables/review/review";

const { otherReviews } = useReview();
</script>

<template>
  <ul>
    <li v-for="review in otherReviews" :key="review.id">
      <el-row class="review-item px-3" :gutter="10">
        <el-col :span="20" class="review">
          <div class="title">
            <router-link
              :to="{ name: 'read-review', params: { id: review.id } }"
            >
              {{ review.title }}
            </router-link>
          </div>
          <div class="content">
            {{ review.content }}
          </div>
          <div class="sub d-flex">
            <div class="writer">
              {{ review.writer }}
            </div>
            <div class="regDate">
              {{ review.elapsedTime }}
            </div>
          </div>
        </el-col>
        <el-col :span="2" class="comment-like">
          <div class="comment-count">
            <span class="count">
              {{ review.viewCount }}
            </span>
            <span class="label"> 댓글 </span>
          </div>
          <div class="like-count">
            <ImageIcon
              :height="30"
              :url="`/src/assets/image/icon/love-fill.png`"
              :width="30"
              class="ms-2"
            />
            <span class="likes">
              {{ review.viewCount }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-divider />
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;
    &:last-child {
      margin-bottom: 0;
    }

    .review {
      margin-right: 40px;
      .title {
        margin-bottom: 15px;
        font-size: 1.3rem;

        a {
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
        font-size: 0.85rem;
        margin-top: 8px;
        color: #737373;
      }

      .sub {
        margin-top: 20px;
        font-size: 0.78rem;

        .regDate {
          margin-left: 10px;
          color: #6b6b6b;
        }
      }
    }

    .comment-like {
      display: flex;
      flex-direction: column;

      .comment-count {
        margin-bottom: 10px;
        display: flex;
        width: 60px;
        height: 60px;
        text-align: center;
        justify-content: center;
        flex-direction: column;
        border: #acacac 1px solid;
        border-radius: 50%;
      }

      .like-count {
        display: flex;
        justify-content: center;
      }
    }
  }
}
</style>
