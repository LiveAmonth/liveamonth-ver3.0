import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CityView from "../views/city/CityView.vue";
import LoginView from "../views/login/LoginView.vue";
import SignUpView from "../views/login/SignUpView.vue";
import FindPwView from "../views/login/FindPwView.vue";
import FindIdView from "../views/login/FindIdView.vue";
import WriteView from "../views/review/WriteView.vue";
import ReadView from "../views/review/ReadView.vue";
import EditView from "../views/review/EditView.vue";
import ReviewView from "../views/review/ReviewView.vue";
import ReadScheduleView from "../views/schedule/ReadScheduleView.vue";
import ScheduleListView from "../views/schedule/ScheduleListView.vue";
import OtherScheduleView from "../views/schedule/OtherScheduleView.vue";
import MyScheduleView from "../views/schedule/MyScheduleView.vue";
import MyPageView from "../views/member/MyPageView.vue";
import { useAuthStore } from "@/stores/auth";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/city",
      name: "city",
      component: CityView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/sign-up",
      name: "sign-up",
      component: SignUpView,
    },
    {
      path: "/find-id",
      name: "find-id",
      component: FindIdView,
    },
    {
      path: "/find-pw",
      name: "find-pw",
      component: FindPwView,
    },
    {
      path: "/review",
      name: "review",
      component: ReviewView,
    },
    {
      path: "/write",
      name: "write",
      component: WriteView,
    },
    {
      path: "/read/:reviewId",
      name: "read",
      component: ReadView,
      props: true,
    },
    {
      path: "/edit/:reviewId",
      name: "edit",
      component: EditView,
      props: true,
    },
    {
      path: "/schedule",
      component: OtherScheduleView,
      children: [
        { path: "", name: "schedule-list", component: ScheduleListView },
        {
          path: "/:id/detail",
          name: "read-schedule",
          component: ReadScheduleView,
          props: true,
        },
      ],
    },
    {
      path: "/my-schedule/:loginId",
      name: "my-schedule",
      component: MyScheduleView,
      props: true,
    },
    {
      path: "/my-page",
      name: "my-page",
      component: MyPageView,
    },
  ],
});
router.beforeEach(async (to, from, next) => {
  const store = useAuthStore();
  const authenticationPages = ["my-page", "my-schedule"];
  if (
    authenticationPages.includes(<string>to.name) &&
    to.name !== "login" &&
    !store.loggedIn
  )
    next({ name: "login" });
  else next();
});
export default router;
