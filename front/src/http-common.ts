import axios from "axios";
import qs from "query-string";
import { useMessageBox } from "@/composables/messageBox";
import { useAuth } from "@/composables/auth";
import type { AxiosInstance } from "axios";

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
    const { bearerToken, isLoggedIn, reissue, logout } = useAuth();
    const { openMessageByCode } = useMessageBox();
    if (error.response) {
      console.log(error.response.data);
      if (
        error.response.data.status == 403 &&
        error.response.data.error == "EXPIRED_JWT"
      ) {
        if (isLoggedIn) {
          const axiosRequest = error.config;
          try {
            await reissue();
            axiosRequest.headers["Authorization"] = `${bearerToken}`;
            console.log("access token을 재발급합니다.");
            return axios(axiosRequest);
          } catch (err) {
            console.log(err);
            console.log("refresh token이 만료되었습니다. 다시 로그인 해주세요");
            openMessageByCode("auth.reissue.login");
            await logout();
          }
        }
      }
    }
    return Promise.reject(error);
  }
);

export default apiClient;
