import Vue from "vue";
import VueRouter from "vue-router";
// import store from "../store/index.js"

import mypage from "../views/mypage.vue";
import article from "../views/mypage-tabs/article.vue";
import repository from "../views/mypage-tabs/repository.vue";
import introduce from "../views/mypage-tabs/introduce.vue";
import GithubLogin from "../views/GithubLogin.vue";

Vue.use(VueRouter);

const rejectAuthUser = (to, from, next) => {
  // if(store.state.isLogin === true){

  if (localStorage.getItem("X-AUTH-TOKEN") !== null) {
    //이미 로그인된 유저니까 막아야함.
    alert("이미 로그인 되었습니다!");
    next("/");
  } else {
    next();
  }
};
const onlyAuthUser = (to, from, next) => {
  // if(store.state.isLogin === false){

  if (localStorage.getItem("X-AUTH-TOKEN") === null) {
    //이미 로그인 안된 유저니까 막아야함.
    //alert('로그인이 필요한 기능입니다!');
    next();
  } else {
    next();
  }
};
const routes = [
  {
    path: "/",
    name: "home",
    component: () => import(/* webpackChunkName: "home" */ "../views/Home.vue"),
  },
  {
    path: "/login",
    name: "login",
    beforeEnter: rejectAuthUser,
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/Login.vue"),
  },
  {
    path: "/mypage",
    name: "mypage",
    beforeEnter: onlyAuthUser,
    component: mypage,
    children: [
      { path: "/", component: article },
      { path: "/article", component: article },
      { path: "/repository", component: repository },
      { path: "/introduce", component: introduce },
    ],
  },
  {
    path: "/register",
    name: "register",
    component: () =>
      import(/* webpackChunkName: "register" */ "../views/Join.vue"),
  },
  {
    path: "/search",
    name: "search",
    component: () =>
      import(/* webpackChunkName: "search" */ "../views/Search.vue"),
  },
  {
    path: "/createPost",
    name: "createPost",
    component: () =>
      import(
        /* webpackChunkName: "createPost" */ "../views/post/createPost.vue"
      ),
  },
  {
    path: "/readPost",
    name: "readPost",
    component: () =>
      import(/* webpackChunkName: "readPost" */ "../views/post/readPost.vue"),
  },
  {
    path: "/searchPost",
    name: "searchPost",
    component: () =>
      import(
        /* webpackChunkName: "searchPost" */ "../views/post/searchPost.vue"
      ),
  },
  {
    path: "/editPost",
    name: "editPost",
    component: () =>
      import(/* webpackChunkName: "editPost" */ "../views/post/editPost.vue"),
  },
  {
    path: "/userSetting",
    name: "userSetting",
    component: () =>
      import(/* webpackChunkName: "userSetting" */ "../views/UserSetting.vue"),
  },
  {
    path: "/verify/success",
    name: "verifySuccess",
    component: () =>
      import(
        /* webpackChunkName: "verifySuccess" */ "../views/verifySuccess.vue"
      ),
  },
  {
    // path: "/githublogin",
    path: "/api/social/login/github",
    name: "githublogin",
    component: GithubLogin,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
