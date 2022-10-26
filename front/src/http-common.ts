import type { AxiosInstance } from "axios";
import axios from "axios";
import qs from "query-string";
import { useAuth } from "@/composables/member/auth";

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
    return response;
  },
  async function (error) {
    const { bearerToken, reissue, logoutBtn } = useAuth();
    if (error.response) {
      const status = error.response.data.status;
      const code = error.response.data.error;
      const axiosRequest = error.config;
      if (status == 403 && code == "EXPIRED_JWT") {
        console.log("access token을 재발급합니다.");
        await reissue()
          .then(() => {
            axiosRequest.headers["Authorization"] = `${bearerToken.value}`;
            console.log("재발급 된 토큰 ", bearerToken.value);
            return apiClient(axiosRequest);
          })
          .catch(async (error) => {
            console.log("refresh token이 만료되었습니다. 다시 로그인 해주세요");
            return Promise.reject(error);
          });
        return Promise.reject(error);
      }
      if (status == 401) {
        console.log("refresh token이 만료되었습니다. 다시 로그인 해주세요");
        await logoutBtn();
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
