<template>
  <div class="container">
    <!-- 저장 버튼 -->
    <v-row>
      <v-col class="py-0">
        <v-btn @click="saveTeam(arrTeamRepo)" style="font-size: 1.5rem; float: right;" text color="#7C4DFF">
          내용 저장하기
          <v-icon x-large>save</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <!-- drag and drop : github에서 가져온 리스트 -->
    <v-row>
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >My Github Organization</div>
          <hr />
          <draggable
            class="list-group kanban-colum"
            :list="teamGitRepos"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in teamGitRepos"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <img alt="left" id="stat" :src="element.src" @click="link(element.repoUrl)">
            </a>
          </draggable>
        </div>
      </v-col>
      <!-- drag and drop : locker에 저장된 레포지토리 -->
      <v-col>
        <div class="p-2" style="background-color: #EDE7F6; border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >Locker Team Repository</div>
          <hr />
          <draggable class="list-group kanban-colum" :list="arrTeamRepo" group="tasks" style="text-align: center;">
            <a
              class="list-group-item mb-2"
              v-for="element in arrTeamRepo"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <img alt="left" id="stat" :src="element.src">
            </a>
          </draggable>
        </div>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import draggable from "vuedraggable";
import { mapState, mapActions } from 'vuex';
import axios from '../../../lib/axios-common';

export default {
  name: "kanban-board",
  components: {
    draggable
  },
  created() {
    this.showRepo;
    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    // this.userInfo.uid = 'jane399'
    // this.userInfo.uid = 'junhok82'
    // this.userInfo.uid = 'YNNJN'
    // this.userInfo.provider = 'github'
    // this.userInfo.provider = 'google'
    // console.log("userInfo.uid: ", this.userInfo.uid)

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    // this.userInfo.id = 15
    // this.userInfo.id = 21
    this.arrTeamRepo = this.teamLockerRepos;
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    teamRepoInfo(){
      return this.$store.state.teamRepoInfo;
    },
    ...mapState(["userInfo",  "teamGitRepos", "teamLockerRepos"])
  },
  data() {
    return {
      token: "",
      accessToken: "",
      arrTeamRepo: this.teamLockerRepos,
    };
  },
  methods: {
    ...mapActions(["getLockerRepos"]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    },
    link(url){
      window.open(url);
    },
    async saveTeam(repos){
      for(var i=0; i<repos.length; i++){
        await axios.post("/v1/github?name="+repos[i].name+"&repoName="+repos[i].repoName+"&pk="+this.userInfo.id)
      }
      alert("저장되었습니다!")
    }
  }
};
</script>
<style scoped>
.kanban-colum {
  min-height: 300px;
}
</style>