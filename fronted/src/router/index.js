import Vue from "vue";
import VueRouter from "vue-router";
// import store from "../store/index.js"

Vue.use(VueRouter);

const rejectAuthUser = (to, from, next) => {
  // if(store.state.isLogin === true){

  if(localStorage.getItem('X-AUTH-TOKEN') !== null){
    //이미 로그인된 유저니까 막아야함.
    alert('이미 로그인 되었습니다!');
    next("/");
  } else {
    next();
  }
}
const onlyAuthUser = (to, from, next) => {
  // if(store.state.isLogin === false){

  if(localStorage.getItem("X-AUTH-TOKEN") === null){
    //이미 로그인 안된 유저니까 막아야함.
    //alert('로그인이 필요한 기능입니다!');
    next();
  } else {
    next();
  }
}
const routes = [
  {
    path: "/",
    name: "home",
    component: () =>
      import(/* webpackChunkName: "home" */ "../views/Home.vue")
  },
  {
    path: "/login",
    name: "login",
    beforeEnter: rejectAuthUser,
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/Login.vue")
  },
  {
    path: "/mypage",
    name: "mypage",
    beforeEnter: onlyAuthUser,
    component: () =>
      import(/* webpackChunkName: "mypage" */ "../views/mypage.vue")
  },
  {
    path: "/register",
    name: "register",
    component: () =>
      import(/* webpackChunkName: "register" */ "../views/Join.vue")
  },
  {
    path: "/search",
    name: "search",
    component: () =>
      import(/* webpackChunkName: "search" */ "../views/Search.vue")
  },
  {
    path: "/createPost",
    name: "createPost",
    component: () =>
      import(/* webpackChunkName: "createPost" */ "../views/post/createPost.vue")
    
  },
  {
    path: "/readPost",
    name: "readPost",
    component: () =>
      import(/* webpackChunkName: "readPost" */ "../views/post/readPost.vue")
    
  },
  {
    path: "/searchPost",
    name: "searchPost",
    component: () =>
      import(/* webpackChunkName: "searchPost" */ "../views/post/searchPost.vue")
    
  },
  {
    path: "/editPost",
    name: "editPost",
    component: () =>
      import(/* webpackChunkName: "editPost" */ "../views/post/editPost.vue")
    
  },
  {
    path: "/social/kakao/login",
    beforeEnter: function(){
      alert("hello!!!!!")
    }
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
