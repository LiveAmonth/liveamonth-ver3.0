import { ref } from "vue";
import { useCityStore } from "@/stores/city";

export const useCity = () => {
  const store = useCityStore();
  const error = ref();
  const isPending = ref(false);

  const getCityNames = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityNames();
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const getCityIntro = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityIntro(cityName);
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const getExtraCityInfo = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getExtraCityInfo(cityName);
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };
  const getCityGridInfo = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityGridInfo();
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };
  return {
    error,
    isPending,
    getCityNames,
    getCityGridInfo,
    getCityIntro,
    getExtraCityInfo,
  };
};
