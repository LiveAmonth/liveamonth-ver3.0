<script setup lang="ts">
import MemberScheduleList from "@/components/member/MemberScheduleList.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";

import { Setting } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useMember } from "@/composables/member";
import { useSchedule } from "@/composables/schedule";
import { useRouter } from "vue-router";

const router = useRouter();
const { memberProfile } = useMember();
const { mySchedules, getAdditionalMySchedules } = useSchedule();

const listKey = ref<number>(0);
const activeName = ref<string>("schedule");
const initialCount = ref<number>(3);

onMounted(async () => {
  await getAdditionalMySchedules(
    memberProfile.value.loginId,
    initialCount.value,
    null
  );
});

const dialogVisible = ref<boolean>(false);
</script>
<template>
  <div class="container">
    <el-row class="profile">
      <el-col :span="4"></el-col>
      <el-col :span="6" class="image">
        <el-avatar
          fit="cover"
          :size="150"
          :src="`/src/assets/image/default.jpg`"
        />
      </el-col>
      <el-col :span="12" class="info">
        <div class="nickname">
          <span>{{ memberProfile.nickname }}</span>
          <el-button
            @click="
              router.push({
                name: 'management',
                params: { menu: 'editProfile' },
              })
            "
          >
            프로필 편집
          </el-button>
          <el-icon @click="dialogVisible = true">
            <Setting />
          </el-icon>
        </div>
        <div class="interactions">
          <div class="schedule">
            <span class="name"> 스케줄 </span>
            <span class="count">
              {{ memberProfile.numberOfSchedules }}
            </span>
          </div>
          <span class="follower">
            <span class="name"> 팔로워 </span>
            <span class="count">
              {{ memberProfile.numberOfFollowers }}
            </span>
          </span>
          <span class="follow">
            <span class="name"> 팔로우 </span>
            <span class="count">
              {{ memberProfile.numberOfFollows }}
            </span>
          </span>
        </div>
        <div class="more">
          <span>
            {{ memberProfile.name }}
          </span>
          <span>
            {{ memberProfile.email }}
          </span>
        </div>
      </el-col>
    </el-row>
    <el-row class="posts">
      <el-col>
        <el-tabs v-model="activeName" class="demo-tabs">
          <el-tab-pane :label="$t('schedule.title.own')" name="schedule">
            <MemberScheduleList
              v-if="mySchedules"
              :key="listKey"
              :initial-count="initialCount"
              @refresh="listKey++"
            />
          </el-tab-pane>
          <el-tab-pane label="내 후기글" name="review">Config</el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>

  <el-dialog v-model="dialogVisible" :title="$t('myPage.menu')" width="300px">
    <ManagementMenu />
  </el-dialog>
</template>
<style scoped lang="scss">
.container {
  display: flex;
  flex-flow: column nowrap;

  .profile {
    margin-right: 100px;
    margin-top: 20px;
    display: flex;
    justify-content: start;
    align-items: center;

    .image {
      display: flex;
      justify-content: start;
      cursor: pointer;

      .avatar {
        &:hover {
          background-color: rgba(56, 56, 56, 0.67);
        }
      }
    }

    .info {
      .nickname {
        display: flex;
        justify-content: start;

        span {
          margin-right: 30px;
          font-size: 1.8rem;
        }

        .el-icon {
          padding-top: 2px;
          margin-left: 15px;
          font-size: 1.6rem;
          cursor: pointer;
        }
      }

      .interactions {
        margin-top: 30px;
        margin-bottom: 30px;
        display: flex;
        justify-content: start;
        font-size: 1.2rem;

        .schedule,
        .follower,
        .follow {
          margin-right: 25px;

          .count {
            font-weight: bold;
            cursor: pointer;
          }
        }
      }

      .more {
        span {
          display: block;
          font-style: italic;

          &:last-child {
            margin-top: 10px;
          }
        }
      }
    }
  }

  .posts {
    margin-top: 50px;

    .el-tabs {
      margin-left: 100px;
    }
  }
}

.el-dialog {
  .el-menu {
    border: none;
  }
}
</style>
