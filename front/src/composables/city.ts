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

  const getTotalCityIntro = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getTotalCityIntro(cityName);
      error.value = null;
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  const getCityFoodAndView = async (cityName: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getCityFoodAndView(cityName);
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
    getTotalCityIntro,
    getCityFoodAndView,
  };
};
