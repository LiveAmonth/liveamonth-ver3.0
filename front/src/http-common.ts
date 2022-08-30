import type { AxiosInstance } from "axios";
import axios from "axios";
import qs from "query-string";
import { useAuthStore } from "@/stores/auth";
import { useMessageBox } from "@/composables/messageBox";
import i18n from "@/i18n";
import { useAuth } from "@/composables/auth";

const apiClient: AxiosInstance = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-type": "application/json;charset=utf-8",
  },
  paramsSerializer: (params) => {
    return qs.stringify(params, { arrayFormat: "comma" });
  },
});

export const getSearchTypes = (domain: string) =>
  apiClient.get(`categories/search-types/${domain}`);

export const getFilterTypes = (domain: string) =>
  apiClient.get(`categories/filter-types/${domain}`);

export const getSortTypes = (domain: string) =>
  apiClient.get(`categories/sort-types/${domain}`);
apiClient.interceptors.request.use(
  (config) => {
    const { isLoggedIn, bearerToken } = useAuth();
    if (config.headers && isLoggedIn) {
      config.headers.Authorization = `${bearerToken.value}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
apiClient.interceptors.response.use(
  function (response) {
    // 응답 데이터로 처리
    return response;
  },
  async function (error) {
    // 응답 오류에 대한 처리
    const store = useAuthStore();
    const { openMessageBox } = useMessageBox();
    if (error.response) {
      console.log(error.response.data);
      if (
        error.response.data.status == 403 &&
        error.response.data.error == "EXPIRED_JWT"
      ) {
        if (store.loggedIn) {
          const axiosRequest = error.config;
          try {
            await store.reissue();
            axiosRequest.headers[
              "Authorization"
            ] = `${store.grantTye} ${store.accessToken}`;
            return axios(axiosRequest);
          } catch (err) {
            await openMessageBox(i18n.global.tc("auth.reissue.login"));
            await store.logout();
          }
        }
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
