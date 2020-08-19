<template>
  <div style="background-color: #fff; min-height: 100vh">
    <v-container class="mt-md-6">
      <v-row>
        <v-col cols="12" md="12" class="p-0">
          <div class="row align-items-center mx-5 mb-5">
            <!-- 프로필사진 섹션 -->
            <div class="col-md-3 d-none d-sm-block align-self-start">
              <div class="d-flex justify-content-center">
                <v-icon
                  v-if="userInfo.picture==null"
                  size="150"
                  class="mb-5"
                  style="z-index: 1;"
                >mdi-account-circle-outline</v-icon>
                <v-avatar v-else size="150" class="mb-5" style="z-index: 1;">
                  <img :src="userInfo.picture" />
                </v-avatar>
              </div>
            </div>

            <!-- 텍스트 섹션 -->
            <div class="col-md-4 d-flex-wrap align-self-start">
              <div class="d-flex align-items-center justify-content-between p-0">
                <p class="light d-inline my-0" style="font-size: 2.3rem;">
                  <strong>{{ userInfo.nickname }}</strong>
                </p>
              </div>
              <p class="thin" style="font-size: 1.1rem;">{{ userInfo.introduction }}</p>

              <div class="d-inline mt-5">
                <a>
                  <v-icon size="35" style="color: black; padding-right: 1rem;">mdi-github</v-icon>
                </a>
                <a>
                  <v-icon size="35" style="color: black;">mdi-google-plus</v-icon>
                </a>
              </div>
            </div>

            <!-- 스탯 섹션 -->
            <div class="col-md-5">
              <div v-if="userInfo.provider === 'github'">
                <a href="https://github.com/anuraghazra/github-readme-stats">
                  <img align="left" :src="statSrc" />
                </a>
              </div>

              <!-- 깃헙과 연동되지 않았을 때의 화면 디자인 -->
              <div v-else style="min-height: 10rem; background-color: #eceffc; padding: 2rem;">
                <div class="text-center">
                  <p class="medium">
                    깃헙과 연동하면 당신의
                    <strong style="color: #7C4DFF">stats</strong>을
                  </p>
                  <p class="medium">
                    <strong style="color: #7C4DFF">LOCKER</strong>에서 확인할 수 있어요
                  </p>
                  <v-icon color="#7C4DFF" class="mr-2">mdi-menu-up</v-icon>
                  <v-btn
                    depressed
                    text
                    color="#252525"
                    router
                    :to="{ name: 'register' }"
                    style="text-decoration: none;"
                  >
                    <v-icon size="23" style="color: black; padding-right: 1rem;">mdi-github</v-icon>
                    <span class="bolder" style="font-size: 1rem;">깃헙 연동 바로가기</span>
                  </v-btn>
                </div>
              </div>
            </div>
          </div>
          <!-- <hr> -->
        </v-col>
      </v-row>

      <!-- 잔디 섹션 -->
      <div class="jandi">
        <div class="graph">
          <ul class="months">
            <li>Jan</li>
            <li>Feb</li>
            <li>Mar</li>
            <li>Apr</li>
            <li>May</li>
            <li>Jun</li>
            <li>Jul</li>
            <li>Aug</li>
            <li>Sep</li>
            <li>Oct</li>
            <li>Nov</li>
            <li>Dec</li>
          </ul>
          <ul class="days">
            <li></li>
            <li>MON</li>
            <li></li>
            <li>WEB</li>
            <li></li>
            <li>FRI</li>
            <li></li>
          </ul>
          <ul class="squares"></ul>
        </div>
      </div>

      <!-- 탭 -->
      <div class="row" style="margin-top: 5rem;">
        <div class="col-md-3">
          <div>
            <p class="bold" style="font-size: 1.5rem;">
              <strong>LOCKER</strong>열어보기
            </p>
            <div class="under-line"></div>
          </div>
        </div>

        <div class="col-md-9">
          <article id="tab">
            <ul class="p-0">
              <v-icon color="white">mdi-chevron-right</v-icon>
              <li style="cursor: default;">
                <span class="bold" style="color: #727272; font-size: 1.3rem;">반가워요 :)</span>
              </li>

              <router-link to="article">
                <li>
                  <span class="bold" style="color: #eceffc;; font-size: 1.3rem;">포스트</span>
                </li>
              </router-link>

              <router-link to="repository">
                <li>
                  <span class="bold" style="color: #eceffc;; font-size: 1.3rem;">레포지토리</span>
                </li>
              </router-link>

              <router-link to="introduce">
                <li>
                  <span class="bold" style="color: #eceffc;; font-size: 1.3rem;">소개</span>
                </li>
              </router-link>
            </ul>
          </article>
        </div>
      </div>
    </v-container>

    <div style="background-color: #242b33;">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import SideBar from "./SideBar.vue"
import("../assets/css/side-style.css");
import("../assets/css/jandi.css");
import("../assets/css/tab.scss");

export default {
  created() {
    this.userInfo;
    this.$store.dispatch("getMyPostList", this.userInfo.id);
    this.myPostList;
    this.githubId = this.userInfo.login;

    
    // 토큰 값 받아오기
    // let token = localStorage.getItem("access_token");
    // this.token = token;
    this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1OTc4MzY5NTUsImV4cCI6MTU5Nzg0MDU1NX0.e8i9KUGIc1y0-RfUaXGWU3f_M1pyDcs-S8gwTl_JnXg"
    // let accessToken = localStorage.getItem("github_token");
    // this.accessToken = accessToken;
    this.accessToken = "8b0949070511750d5a23aa6d9a81c5a85b67c865"
    
    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    // this.userInfo.uid = 'jane399'
    // this.userInfo.uid = 'junhok82'
    this.userInfo.uid = 'YNNJN'
    this.userInfo.provider = 'github'
    console.log("userInfo.uid: ", this.userInfo.uid);

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    // this.userInfo.id = 15
    this.userInfo.id = 21

    this.getRepos({ token: this.token, accessToken: this.accessToken, uid: this.userInfo.uid, id: this.userInfo.id})

    //likes 배열 초기화 작업
    this.likes.length = this.size;
    for (let i = 0; i < this.size; i++) {
      this.likes.push(false);
    }
  },
  mounted() {
    // Add squares
    const squares = document.querySelector(".squares");
    for (var i = 1; i < 365; i++) {
      const level = Math.floor(Math.random() * 4);
      squares.insertAdjacentHTML(
        "beforeend",
        `<li data-level="${level}"></li>`
      );
    }
  },
  computed: {
    ...mapState(["userInfo", "myPostList"]),
    size() {
      return this.$store.state.myPostList.length;
    },
    likes() {
      return [];
    },
    statSrc() {
      return (
        "https://github-readme-stats.vercel.app/api?username=" +
        this.githubId +
        "&show_icons=true&theme=buefy"
      );
    }
  },
  data() {
    return {
      githubId: null
    };
  },
  components: {
    // SideBar,
  },
  methods: {
    ...mapActions(["getRepos"]),
    showMyDetail(pid) {
      this.$store.dispatch("showMyDetail", pid);
      //this.$router.push({name: "readPost"});
    },
    //좋아요 클릭시
    like(index) {
      if (this.likes[index]) {
        this.likes[index] = false;
        let like = document.getElementById(index);
        like.style.color = "grey";
      } else {
        this.likes[index] = true;
        let like = document.getElementById(index);
        like.style.color = "red";
      }
    }
  }
};
</script>
<style scoped>
.under-line {
  height: 0.3rem;
  width: 3.5rem;
  margin-bottom: 3rem;
  background-color: #7c4dff;
}
</style>
