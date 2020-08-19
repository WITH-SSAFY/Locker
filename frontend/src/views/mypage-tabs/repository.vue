<template>
<v-container>
  <div v-if="userInfo.provider === 'github'" style="margin-left:20px;">
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
      <v-col cols="8" style="color: white;">
        <h2>내 블로그</h2>
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
        <h2 style="color:white;">내 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn style="position:relative; float: right;" depressed @click="showAction(0)">뒤로</v-btn>
      </v-col>
    </v-row>

    <v-row class="mt-10" v-if="showRepo[2]">
      <v-col cols="8" style="color: white;">
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
  <div v-else style="margin-left:20px; color: white;">
    <div style="border-radius: 10px;"><h1>깃헙으로 로그인해주세요!</h1></div>
  </div>
</v-container>
</template>

<script>
import "../../assets/css/repository.scss";
import MyRepository from "./manage-repository/MyRepository.vue";
import AddMyRepo from "./manage-repository/AddMyRepo.vue";
import TeamRepository from "./manage-repository/TeamRepository.vue";
import AddTeamRepo from "./manage-repository/AddTeamRepo.vue";
import { mapActions, mapState } from 'vuex';

export default {
  name: 'repository',
  components: {
    MyRepository,
    AddMyRepo,
    TeamRepository,
    AddTeamRepo
  },
  created() {
    this.showRepo;
    this.showAction(0);
    // // 토큰 값 받아오기
    // // let token = localStorage.getItem("access_token");
    // // this.token = token;
    // this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNSIsInJvbGVzIjpbXSwiaWF0IjoxNTk3ODMzMTQyLCJleHAiOjE1OTc4MzY3NDJ9._bMlfpFFbt0orBkz5Md7x3FWGF0YFktQECtTrqJHuWA"
    // // let accessToken = localStorage.getItem("github_token");
    // // this.accessToken = accessToken;
    // this.accessToken = "f6b356c729bc9afb7ee22ef42c8a1858c666c585"

    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    // this.userInfo.uid = 'jane399'
    // this.userInfo.uid = 'junhok82'
    // this.userInfo.uid = 'YNNJN'
    // this.userInfo.provider = 'github'
    // this.userInfo.provider = 'google'
    console.log("userInfo.uid: ", this.userInfo.uid)

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    // this.userInfo.id = 15
    // this.userInfo.id = 21
},
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["userInfo", "myGitRepo", "teamGitRepo"]),
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
    }, 
  }
};
</script>

<style>
</style>