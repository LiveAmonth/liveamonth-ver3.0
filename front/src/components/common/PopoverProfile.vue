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
const { type } = useMember();
const { profileTabs, getPostCount } = useMyPage();
const {
  error,
  isPending,
  isFollowed,
  isPositiveInteraction,
  interactContent,
  isWriter,
} = useInteraction();
const { buttonMsg, requireLoginMessageBox, openWarningMessage } =
  useMessageBox();

const numberOfFollowers = ref<number>(props.profile.numberOfFollowers);

onMounted(async () => {
  if (isLoggedIn.value) {
    await isPositiveInteraction(type, props.profile.id);
  }
});

const follow = async () => {
  if (isLoggedIn.value) {
    await interactContent(type, props.profile.id);
    if (error.value) {
      openWarningMessage(error.value.message);
    }
    if (isFollowed.value) {
      numberOfFollowers.value++;
    } else {
      numberOfFollowers.value--;
    }
  } else {
    await requireLoginMessageBox();
  }
};
</script>

<template>
  <el-popover
    :width="180"
    popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
  >
    <template #reference>
      <div class="d-flex justify-content-start">
        <div class="avatar me-1">
          <el-avatar :size="20" :src="profile.image" />
        </div>
        <div class="writer pt-1">
          {{ profile.nickname }}
        </div>
      </div>
    </template>
    <template #default>
      <div class="popover-content">
        <div class="image">
          <div class="circle-1"></div>
          <div class="circle-2"></div>
          <el-avatar :size="60" :src="profile.image" />
        </div>
        <p class="nickname">
          {{ profile.nickname }}
        </p>
        <div class="actions" v-if="!isWriter(profile.id)">
          <el-button :loading="isPending" class="btn" text @click="follow">
            @{{ buttonMsg(`${isFollowed ? "unfollow" : "follow"}`) }}
          </el-button>
        </div>
        <div class="ds-info m-1">
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
              {{ getPostCount(tab.code, profile, numberOfFollowers) }}
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
  display: flex;
  justify-content: center;
  flex-direction: column;

  .image {
    position: relative;
    width: 60px;
    height: 60px;
    margin: 7px 15px;

    .circle-1 {
      position: absolute;
      box-sizing: border-box;
      width: 66px;
      height: 66px;
      top: -3px;
      left: -3px;
      border-width: 1px;
      border-style: solid;
      border-color: #4c6e72 #4c6e72 #4c6e72 transparent;
      border-radius: 50%;
      transition: all 1.5s ease-in-out;
    }

    .circle-2 {
      @extend .circle-1;
      width: 72px;
      height: 72px;
      top: -6px;
      left: -6px;
      border-color: #4c6e72 transparent #4c6e72 #4c6e72;
    }

    .el-avatar {
      display: block;
      border-radius: 50%;
      background: #f5e8df;
    }

    &:hover {
      .circle-1 {
        transform: rotate(360deg);
      }

      .circle-2 {
        transform: rotate(-360deg);
      }
    }
  }

  .nickname {
    margin: 3px 0 0 18px;
    font-weight: 500;
  }

  .actions {
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
    justify-content: center;

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
        color: #4c6e72;
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
