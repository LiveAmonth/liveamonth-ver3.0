<script lang="ts" setup>
import { useMyPage } from "@/composables/member/mypage";
import type { PropType } from "vue";
import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import { useMessageBox } from "@/composables/common/messageBox";
import { useInteraction } from "@/composables/interaction/interaction";
import { useAuth } from "@/composables/member/auth";
import { onMounted, ref } from "vue";
import { useMember } from "@/composables/member/member";

const props = defineProps({
  profile: {
    type: Object as PropType<SimpleProfileType>,
    required: true,
  },
});
const { isLoggedIn } = useAuth();
const { type, simpleProfile } = useMember();
const { profileTabs, getPostCount } = useMyPage();
const { reactContent, isFollow } = useInteraction();
const { buttonMsg } = useMessageBox();
const isFollowed = ref<boolean>(false);

onMounted(async () => {
  if (isLoggedIn.value) {
    isFollowed.value = await isFollow(props.profile.id);
  }
});

const follow = async () => {
  await reactContent(type, props.profile.id);
};
</script>

<template>
  <el-popover
    :width="180"
    popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
  >
    <template #reference>
      <el-avatar :size="80" :src="`/src/assets/image/default.jpg`" />
    </template>
    <template #default>
      <div
        class="popover-content"
        style="display: flex; gap: 5px; flex-direction: column"
      >
        <el-avatar
          :size="60"
          :src="`/src/assets/image/default.jpg`"
          style="margin-bottom: 8px"
        />
        <p class="nickname" style="margin: 0; font-weight: 500">
          {{ profile.nickname }}
        </p>
        <span
          class="mention"
          @click="follow"
          v-if="profile.id !== simpleProfile.id"
        >
          @{{ buttonMsg(`${isFollowed ? "unfollow" : "follow"}`) }}
        </span>
        <div class="ds-info d-flex justify-content-center m-1">
          <div
            v-for="tab in profileTabs"
            :key="tab.code"
            :class="tab.code"
            class="ds"
          >
            <h6>
              {{ tab.value }}
              <el-icon>
                <component :is="tab.icon" />
              </el-icon>
            </h6>
            <p>
              {{ getPostCount(tab.code, profile) }}
            </p>
          </div>
        </div>
      </div>
    </template>
  </el-popover>
</template>

<style lang="scss" scoped>
.popover-content {
  overflow: hidden;

  .mention {
    margin: 0;
    font-size: 14px;
    color: var(--el-color-info);
    text-decoration: none;
    font-style: italic;

    &:hover {
      font-weight: bold;
      cursor: pointer;
    }

    &:visited {
      color: inherit;
    }
  }

  .ds-info {
    margin: auto;
    top: 100px;
    bottom: 0;
    right: 0;
    left: 0;
    width: inherit;
    height: 40px;
    display: flex;

    .follower,
    .schedule,
    .review {
      position: relative;
      left: -300px;
      width: 33%;
      text-align: center;
      color: black;
      animation: fadeInMove 1s;
      animation-fill-mode: forwards;

      h6 {
        margin: 0;
        text-transform: uppercase;
        color: #004a55;
      }

      p {
        margin-top: 0;
        font-size: 12px;
      }
    }

    .ds {
      &:nth-of-type(2) {
        animation-delay: 0.5s;
      }

      &:nth-of-type(1) {
        animation-delay: 1s;
      }
    }
  }
}
</style>
