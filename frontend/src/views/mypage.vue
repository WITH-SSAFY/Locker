<template>
  <div style="background-color: #fff; min-height: 100vh">
    <v-container class="mt-md-6">
      <v-row>
        <v-col cols="12" md="12" class="p-0">
          <div class="row align-items-center mx-5 mb-5">
            <div class="col-md-4 d-none d-sm-block align-self-start">
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
            <div class="col-md-8 d-flex-wrap align-self-start">
              <div class="d-flex align-items-center justify-content-between p-0">
                <p class="lead d-inline my-0" style="font-size: 2.3rem;">
                  <strong>{{ userInfo.nickname }}</strong>
                </p>
                <!-- <a href="" class="d-inline btn btn-outline-dark btn-sm">Edit Profile</a> -->
              </div>
              <p class="ml-2">{{ userInfo.introduction }}</p>
              <div class="d-inline">
                <a>
                  <v-icon size="35" style="color: black; padding-right: 1rem;">mdi-github</v-icon>
                </a>
                <a>
                  <v-icon size="35" style="color: black;">mdi-google-plus</v-icon>
                </a>
              </div>
            </div>
          </div>
          <hr />
        </v-col>
      </v-row>

      <!-- 중첩 라우터 -->
      <p class="d-flex justify-content-around">
        <router-link to="article" class="p-3 text-decoration-none">
          <span class="tab">article</span>
        </router-link>
        <router-link to="repository" class="p-3 text-decoration-none">
          <span class="tab">repository</span>
        </router-link>
        <router-link to="introduce" class="p-3 text-decoration-none">
          <span class="tab">introduce</span>
        </router-link>
      </p>
      <!-- <p>
        <router-link to="myRepository" class="p-3 text-decoration-none">
          <span class="tab">MyReo</span>
        </router-link>
      </p>-->
      <router-view></router-view>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";
// import SideBar from "./SideBar.vue"
import("../assets/css/side-style.css");

export default {
  created() {
    this.userInfo;
    this.$store.dispatch("getMyPostList", this.userInfo.email);
    this.myPostList;
    //likes 배열 초기화 작업
    this.likes.length = this.size;
    for (let i = 0; i < this.size; i++) {
      this.likes.push(false);
    }
  },
  computed: {
    ...mapState(["userInfo", "myPostList"]),
    size() {
      return this.$store.state.myPostList.length;
    },
    likes() {
      return [];
    }
  },
  data() {
    return {
      tab: null,
      items: ["글", "카테고리", "소개"]
    };
  },
  components: {
    // SideBar,
    // category,
  },
  methods: {
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
@import url("https://fonts.googleapis.com/css?family=Oswald");

.tabs {
}

.tab {
  color: black;
  font-family: "Oswald", sans-serif;
  font-size: 20px;
  text-decoration: none;
}
</style>
