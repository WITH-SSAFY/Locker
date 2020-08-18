<template>
  <div style="margin-left:20px;">
    <!-- {{showRepo}} -->
    <v-row>
      <v-col>
        <v-btn
          tile
          class="mx-2"
          elevation="0"
          x-large
          color="#7C4DFF"
          dark
          @click="showAction(0)"
          style="font-size: 1.5rem;"
        >내 블로그</v-btn>
        <v-btn
          @click="showAction(2)"
          tile
          class="mx-2"
          elevation="0"
          x-large
          color="#7C4DFF"
          dark
          style="font-size: 1.5rem;"
        >팀 블로그</v-btn>
      </v-col>
    </v-row>
    <v-row class="mt-10" v-if="showRepo[0]">
      <v-col cols="8">
        <h2>개인 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn
          style="position:relative; float: right;"
          depressed
          @click="showAction(1)"
        >Github에서 가져오기</v-btn>
      </v-col>
    </v-row>
    <v-row class="mt-10" v-if="showRepo[1]">
      <v-col cols="8">
        <h2>개인 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn style="position:relative; float: right;" depressed @click="showAction(0)">뒤로</v-btn>
      </v-col>
    </v-row>

    <v-row class="mt-10" v-if="showRepo[2]">
      <v-col cols="8">
        <h2>팀 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn
          style="position:relative; float: right;"
          depressed
          @click="showAction(3)"
        >Github에서 가져오기</v-btn>
      </v-col>
    </v-row>
    <v-row class="mt-10" v-if="showRepo[3]">
      <v-col cols="8">
        <h2>팀 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn style="position:relative; float: right;" depressed @click="showAction(2)">뒤로</v-btn>
      </v-col>
    </v-row>

    <hr style="margin:5px;" />
    <v-row v-if="showRepo[0]">
      <my-repository></my-repository>
    </v-row>
    <v-row v-if="showRepo[1]">
      <add-my-repo></add-my-repo>
    </v-row>
    <v-row v-if="showRepo[2]">
      <team-repository></team-repository>
    </v-row>
    <v-row v-if="showRepo[3]">
      <add-team-repo></add-team-repo>
    </v-row>
  </div>
</template>

<script>
import "../../assets/css/repository.scss";
import MyRepository from "./manage-repository/MyRepository.vue";
import AddMyRepo from "./manage-repository/AddMyRepo.vue";
import TeamRepository from "./manage-repository/TeamRepository.vue";
import AddTeamRepo from "./manage-repository/AddTeamRepo.vue";
import { mapActions, mapState } from 'vuex';

export default {
  components: {
    MyRepository,
    AddMyRepo,
    TeamRepository,
    AddTeamRepo
  },
  created() {
    this.showRepo;

    // 토큰 값 받아오기
    let token = localStorage.getItem("access_token");
    this.token = token;
    // this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1OTc3MzY2NzMsImV4cCI6MTU5Nzc0MDI3M30.2sgnDaCArO1bNMAM9IYUTlPYyb8P4Hyfy_3zj6Q_MSc"
    let accessToken = localStorage.getItem("github_token");
    this.accessToken = accessToken;
    // this.accessToken = "677305b88de533573aa5144c7faf15fe839f7404"

    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    // this.userInfo.login = 'jane399'
    console.log("userInfo.login: ", this.userInfo.login)
    this.getRepos({ token: this.token, accessToken: this.accessToken, login: this.userInfo.login})

  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["userInfo", "myRepoInfo", "teamRepoInfo"]),
  },
  data: () => {
    return {
    };
  },
  methods: {
    ...mapActions(["getRepos"]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    }
  }
};
</script>

<style>
</style>