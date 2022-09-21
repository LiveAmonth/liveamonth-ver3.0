<script setup lang="ts">
import ScheduleInfiniteList from "@/components/schedule/list/ScheduleInfiniteList.vue";
import ManagementMenu from "@/components/member/MenagementMenu.vue";
import ContentTabsSlot from "@/components/common/ConentTabsSlot.vue";
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
const { mySchedules, getInfiniteSchedules } = useSchedule();
const { myPagePostsTabs, profileTabs, getPostCount } = useMyPage();
const listKey = ref<number>(0);
const activeName = ref<string>(props.post);
const initialSize = ref<number>(3);

onMounted(async () => {
  await getInfiniteSchedules(
    memberProfile.value.loginId,
    initialSize.value,
    null,
    true
  );
});

const dialogVisible = ref<boolean>(false);

const clickTab = (tab: string) => {
  activeName.value = tab !== profileTabs[0].code ? tab : activeName.value;
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
          <div v-for="tab in profileTabs" :key="tab.code" :class="tab.code">
            <span class="name"> {{ tab.value }} </span>
            <span class="count" @click="clickTab(tab.code)">
              {{ getPostCount(tab.code, memberProfile) }}
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
    <ContentTabsSlot
      class="content-tab"
      v-model:active-name="activeName"
      :tabs="myPagePostsTabs"
    >
      <template v-slot:tab-1>
        <ScheduleInfiniteList
          v-if="activeName === myPagePostsTabs[0].code && mySchedules.length"
          :key="listKey"
          :login-id="memberProfile.loginId"
          :max-count="memberProfile.numberOfSchedules"
          :initial-count="initialSize"
          :is-my-page="true"
          @refresh="listKey++"
        />
      </template>
      <template v-slot:tab-2>
        <div v-if="activeName === myPagePostsTabs[1].code">
          내 후기글 리스트@@
        </div>
      </template>
    </ContentTabsSlot>
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

  .content-tab {
    margin-top: 80px;
    margin-left: 50px;
  }
}

.el-dialog {
  .el-menu {
    border: none;
  }
}
</style>
