<script lang="ts" setup>
import { ref } from "vue";
import { useCity } from "@/composables/city/city";
import { useMessageBox } from "@/composables/common/messageBox";

const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});

const { categoryMsg, titleMsg, labelMsg } = useMessageBox();
const { cityGridInfos, calcTransportScore } = useCity();
const cityGridInfo = cityGridInfos.value[props.index];
const score = ref(calcTransportScore(cityGridInfo.transportScore));
</script>
<template>
  <div class="flip-card">
    <div class="flip-card-inner">
      <div class="flip-card-front">
        <el-image
          :src="`/src/assets/image/intro/${cityGridInfo.image}`"
          alt="Avatar"
          fit="cover"
          sh
          style="width: auto; height: 200px"
        />
      </div>
      <div class="flip-card-back">
        <h1>{{ categoryMsg("city.name", cityGridInfo.name.code) }}</h1>
        <div class="ms-4">
          <div class="d-flex justify-content-start">
            <p>{{ titleMsg("city.weather") }} :</p>
            <p class="ms-1">{{ cityGridInfo.averageDegree }}&#8451;</p>
          </div>
          <div class="d-flex justify-content-start">
            <p>{{ labelMsg("city.transport.score") }} :</p>
            <el-rate
              v-model="score"
              class="ms-1 pt-2"
              disabled
              text-color="#ff9900"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.flip-card {
  padding: 0;
  background-color: transparent;
  width: 90%;
  height: 200px;
  perspective: 1000px; /* Remove this if you don't want the 3D effect */
}

/* This container is needed to position the front and back side */
.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
}

/* Do an horizontal flip when you move the mouse over the flip box container */
.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}

/* Position the front and back side */
.flip-card-front,
.flip-card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden; /* Safari */
  backface-visibility: hidden;
  border-radius: 10px;
}

/* Style the front side (fallback if image is missing) */
.flip-card-front {
  background-color: #bbb;
  color: black;
}

/* Style the back side */
.flip-card-back {
  background-color: rgba(0, 74, 85, 0.99);
  color: white;
  transform: rotateY(180deg);
}

.el-image {
  border-radius: 10px;
}

.el-rate {
  //padding-top: 10px;
}
</style>
