<script setup lang="ts">
import MemberScheduleList from "@/components/member/MemberScheduleList.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";

import { Setting } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useMember } from "@/composables/member";
import { useSchedule } from "@/composables/schedule";
import { useRouter } from "vue-router";
import { useMyPage } from "@/composables/mypage";

const props = defineProps({
  post: {
    type: String,
    required: true,
  },
});

const router = useRouter();
const { memberProfile } = useMember();
const { mySchedules, getAdditionalMySchedules } = useSchedule();
const { myPagePostsTabs, profileTabs, getPostCount } = useMyPage();
const listKey = ref<number>(0);
const activeName = ref<string>(props.post);
const initialCount = ref<number>(3);

onMounted(async () => {
  await getAdditionalMySchedules(
    memberProfile.value.loginId,
    initialCount.value,
    null
  );
});

const dialogVisible = ref<boolean>(false);

const clickTab = (tab: string) => {
  activeName.value = tab !== profileTabs[0].name ? tab : activeName.value;
};
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
            {{ $t("myPage.account.editProfile") }}
          </el-button>
          <el-icon @click="dialogVisible = true">
            <Setting />
          </el-icon>
        </div>
        <div class="interactions">
          <div v-for="tab in profileTabs" :key="tab.name" :class="tab.name">
            <span class="name"> {{ $t(`member.${tab.name}`) }} </span>
            <span class="count" @click="clickTab(tab.name)">
              {{ getPostCount(tab.name, memberProfile) }}
            </span>
          </div>
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
    <div class="posts">
      <div class="posts-header">
        <div class="tabs">
          <template v-for="(tab, index) in myPagePostsTabs" :key="tab.name">
            <input
              type="radio"
              :id="`radio-${index + 1}`"
              name="tabs"
              :checked="index === 1"
              :value="tab.name"
              v-model="activeName"
            />
            <label class="tab" :for="`radio-${index + 1}`">
              <el-icon class="me-1">
                <component :is="tab.icon" />
              </el-icon>
              {{ $t(`myPage.posts.${tab.name}`) }}
            </label>
          </template>
          <span class="glider"></span>
        </div>
      </div>
      <el-row class="posts-content">
        <el-col>
          <div v-if="activeName === 'followed'">팔로우한 스케줄 리스트@@</div>
          <MemberScheduleList
            v-else-if="activeName === 'schedule' && mySchedules"
            :key="listKey"
            :initial-count="initialCount"
            @refresh="listKey++"
          />
          <div v-else-if="activeName === 'review'">내 후기글 리스트@@</div>
        </el-col>
      </el-row>
    </div>
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

        .follower,
        .schedule,
        .review {
          margin-right: 25px;

          .name {
            margin-right: 5px;
          }

          &:first-child {
            .count {
              cursor: default;
            }
          }

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
    margin-top: 80px;
    margin-left: 50px;

    .posts-header {
      border-top: #bab7b7 0.5px solid;
      display: flex;
      justify-content: center;
      margin-bottom: 30px;

      .tabs {
        display: flex;
        position: relative;

        * {
          z-index: 2;
        }

        input[type="radio"] {
          display: none;
        }

        .tab {
          display: flex;
          align-items: center;
          justify-content: center;
          height: 40px;
          width: 100px;
          color: #646363;
          font-size: 1rem;
          cursor: pointer;
          transition: color 0.15s ease-in;
        }

        input[type="radio"] {
          &:checked {
            & + label {
              color: rgb(1, 109, 125);
              font-weight: 600;
            }
          }
        }

        input[id="radio-1"] {
          &:checked {
            & ~ .glider {
              transform: translateX(0);
            }
          }
        }

        input[id="radio-2"] {
          &:checked {
            & ~ .glider {
              transform: translateX(100%);
            }
          }
        }

        input[id="radio-3"] {
          &:checked {
            & ~ .glider {
              transform: translateX(200%);
            }
          }
        }

        .glider {
          position: absolute;
          display: flex;
          height: 40px;
          width: 100px;
          z-index: 1;
          border-top: #016d7d 0.2rem solid; // just a high number to create pill effect
          transition: 0.25s ease-out;
        }
      }
    }

    .posts-buttons {
      position: relative;
      top: 0;
      display: flex;
      justify-content: center;

      .el-button {
        padding: 0;
        margin: 0;

        &:first-child {
          margin-right: 45px;
        }

        &:last-child {
          margin-left: 45px;
        }
      }
    }
  }
}

.el-dialog {
  .el-menu {
    border: none;
  }
}
</style>
