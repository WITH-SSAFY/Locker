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
    this.arrGitRepo;
    // this.myRepoInfo;
    // this.teamRepoInfo;

    // 토큰 값 받아오기
    // let token = localStorage.getItem("access_token");
    // this.token = token;
    this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5NzcxNzg5MCwiZXhwIjoxNTk3NzIxNDkwfQ.lkP9dN43Xmtnc9aIZWwJj_ThVP3riufFVQp9apPCBy8"
    // let accessToken = localStorage.getItem("github_token");
    // this.accessToken = accessToken;
    this.accessToken = "6c731bf01db740678ed804584fe8b9acf3f777c6"

    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    this.getRepos({ token: this.token, accessToken: this.accessToken})

  },
  mounted(){
    console.log("arrGitRepo: ", this.arrGitRepo);
    this.userInfo.login = 'junhok82';
    console.log("userInfo:", this.userInfo);

    // 팀 레포 리스트, 내 레포 리스트 구별하기
    var myCnt = 0;
    var teamCnt = 0;
    for(var i=0; i < this.arrGitRepo.length; i++){
      if(this.arrGitRepo[i].name !== this.userInfo.login){
        // teamTemp[teamCnt] = { name: this.arrGitRepo[i].name, repoUrl: this.arrGitRepo[i].repoUrl, src: this.arrGitRepo[i].src};
        this.teamRepoInfo[teamCnt] = { name: this.arrGitRepo[i].name, repoUrl: this.arrGitRepo[i].repoUrl, src: this.arrGitRepo[i].src};
        teamCnt++;
      } else {
        // myTemp[myCnt] = {name: this.arrGitRepo[i].name, repoUrl: this.arrGitRepo[i].repoUrl, src: this.arrGitRepo[i].src};
        this.myRepoInfo[myCnt] = {name: this.arrGitRepo[i].name, repoUrl: this.arrGitRepo[i].repoUrl, src: this.arrGitRepo[i].src};
        myCnt++;
      }
    }
    // console.log("mine- repoInfo:", this.myRepoInfo);
    // console.log("team - repoInfo:", this.teamRepoInfo);
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    arrGitRepo(){
      return this.$store.state.arrGitRepo;
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