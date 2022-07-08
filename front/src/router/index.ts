import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CityView from "../views/city/CityView.vue";
import LoginView from "../views/login/LoginView.vue";
import SignUpView from "../views/login/SignUpView.vue";
import WriteView from "../views/review/WriteView.vue";
import ReadView from "../views/review/ReadView.vue";
import EditView from "../views/review/EditView.vue";
import ReviewView from "../views/review/ReviewView.vue";

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
      path: "/signUp",
      name: "sign-up",
      component: SignUpView,
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
  ],
});

export default router;
