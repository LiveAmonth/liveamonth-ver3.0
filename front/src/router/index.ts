import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import WriteReviewView from "../views/review/WriteReviewView.vue";
import ReadReviewView from "../views/review/ReadReviewView.vue";
import EditReviewView from "../views/review/EditReviewView.vue";
import ReviewView from "../views/review/ReviewView.vue";
import ReadScheduleView from "../views/schedule/ReadScheduleView.vue";
import ScheduleListView from "../views/schedule/ScheduleListView.vue";
import MyScheduleView from "../views/schedule/MyScheduleView.vue";

import { useAuthStore } from "@/stores/member/auth";

const loadView = (name: string, dir: string, view: string) => {
  return () =>
    import(
      /* webpackChunkName: "name-[request]" */ `../views/${dir}/${view}View.vue`
    );
};

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
      component: loadView("city", "city", "City"),
    },
    {
      path: "/login",
      name: "login",
      component: loadView("login", "login", "Login"),
    },
    {
      path: "/sign-up",
      name: "sign-up",
      component: loadView("sign-up", "login", "SignUp"),
    },
    {
      path: "/find-id",
      name: "find-id",
      component: loadView("find-id", "login", "FindId"),
    },
    {
      path: "/find-pw",
      name: "find-pw",
      component: loadView("find-pw", "login", "FindPw"),
    },
    {
      path: "/reviews/:menu",
      name: "review-list",
      component: ReviewView,
      props: true,
    },
    {
      path: "/reviews/write",
      name: "write-review",
      component: WriteReviewView,
    },
    {
      path: "/reviews/:id/read",
      name: "read-review",
      component: ReadReviewView,
      props: true,
    },
    {
      path: "/reviews/:id/edit",
      name: "edit-review",
      component: EditReviewView,
      props: true,
    },
    { path: "/schedules", name: "schedule-list", component: ScheduleListView },
    {
      path: "/schedules/:id/detail",
      name: "read-schedule",
      component: ReadScheduleView,
      props: true,
    },
    {
      path: "/my-schedule/:loginId",
      name: "my-schedule",
      component: MyScheduleView,
      props: true,
    },
    {
      path: "/my-page/:post",
      name: "profile",
      component: loadView("profile", "member", "Profile"),
      props: true,
    },
    {
      path: "/my-page/management/:category/:menu",
      name: "management",
      component: loadView("management", "member", "Management"),
      props: true,
    },
  ],
});
router.beforeEach(async (to, from, next) => {
  const store = useAuthStore();
  const authenticationPages = [
    "management",
    "profile",
    "my-schedule",
    "edit-review",
    "write-review",
  ];
  if (authenticationPages.includes(<string>to.name) && !store.loggedIn)
    next({ name: "login" });
  else next();
});

export default router;
