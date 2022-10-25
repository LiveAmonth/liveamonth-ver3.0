<script lang="ts" setup>
import type { PropType } from "vue";
import type { SimpleProfileType } from "@/modules/types/member/MemberTypes";
import { useMessageBox } from "@/composables/common/messageBox";
import { useInteraction } from "@/composables/interaction/interaction";
import { useMember } from "@/composables/member/member";
import { onMounted, ref } from "vue";
import { useAuth } from "@/composables/member/auth";

const props = defineProps({
  profile: {
    type: Object as PropType<SimpleProfileType>,
    required: true,
  },
  isList: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const { type } = useMember();
const { isLoggedIn } = useAuth();
const {
  error,
  isPending,
  isFollowed,
  isPositiveInteraction,
  reactContent,
  isWriter,
} = useInteraction();
const { buttonMsg, tabMsg, requireLoginMessageBox, openWarningMessage } =
  useMessageBox();

const numberOfFollowers = ref<number>(props.profile.numberOfFollowers);

onMounted(async () => {
  if (isLoggedIn.value) {
    await isPositiveInteraction(type, props.profile.id);
  }
});

const follow = async () => {
  if (isLoggedIn.value) {
    await reactContent(type, props.profile.id);
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
  <el-row class="center">
    <el-col :span="16" class="profile">
      <div class="image" :class="!isList ? 'mt-3' : ''">
        <div class="circle-1"></div>
        <div class="circle-2"></div>
        <el-avatar :size="70" :src="`/src/assets/image/default.jpg`" />
      </div>

      <div class="name">{{ profile.nickname }}</div>

      <div class="actions" v-if="!isWriter(profile.id) && !isList">
        <el-button class="btn" @click="follow" :loading="isPending">
          {{ buttonMsg(`${isFollowed ? "unfollow" : "follow"}`) }}
        </el-button>
      </div>
    </el-col>
    <el-col :span="8" class="stats">
      <div class="box">
        <span class="value">{{ $count(profile.numberOfSchedules) }}</span>
        <span class="parameter">{{ tabMsg("profile.schedule") }}</span>
      </div>
      <div class="box">
        <span class="value">{{ $count(profile.numberOfReviews) }}</span>
        <span class="parameter">{{ tabMsg("profile.review") }}</span>
      </div>
      <div class="box">
        <span class="value">{{ $count(numberOfFollowers) }}</span>
        <span class="parameter">{{ tabMsg("profile.follower") }}</span>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
$brown: #4c6e72;

.center {
  display: flex;
  justify-items: center;
  align-items: center;
  background: #fff;
  overflow: hidden;
}

.profile {
  text-align: center;

  .image {
    position: relative;
    width: 70px;
    height: 70px;
    margin: 0 auto;

    .circle-1 {
      position: absolute;
      box-sizing: border-box;
      width: 76px;
      height: 76px;
      top: -3px;
      left: -3px;
      border-width: 1px;
      border-style: solid;
      border-color: $brown $brown $brown transparent;
      border-radius: 50%;
      transition: all 1.5s ease-in-out;
    }

    .circle-2 {
      @extend .circle-1;
      width: 82px;
      height: 82px;
      top: -6px;
      left: -6px;
      border-color: $brown transparent $brown $brown;
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

  .name {
    font-size: 15px;
    font-weight: 600;
    margin-top: 20px;
    cursor: default;
  }

  .actions {
    margin-top: 18px;

    .btn {
      display: block;
      width: 120px;
      height: 30px;
      margin: 0 auto 10px auto;
      background: none;
      border: 1px solid #648b93;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 600;
      box-sizing: border-box;
      transition: all 0.3s ease-in-out;
      color: #648b93;

      &:hover {
        background: #648b93;
        color: #fff;
      }
    }
  }
}

.stats {
  .box {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    box-sizing: border-box;
    background: #87b3bc;
    height: 66px;
    text-align: center;
    transition: all 0.4s ease-in-out;
    cursor: default;
    color: $brown;

    &:hover {
      background: #648b93;
      color: #ffffff;
      text-shadow: -1px 0 $brown, 0 1px $brown, 1px 0 $brown, 0 -1px $brown;
    }

    &:nth-child(2) {
      margin: 1px 0;
    }
  }

  span {
    display: block;
  }

  .value {
    font-size: 18px;
    font-weight: 600;
  }

  .parameter {
    font-size: 11px;
  }
}
</style>
