<template>
  <div class="container">
    <!-- 저장 버튼 -->
    <v-row>
      <v-col class="py-0">
        <v-btn @click="saveMine(arrMyRepo)" style="font-size: 1.5rem; float: right;" text color="#7C4DFF">
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
          >My Github Repository</div>
          <hr />
          <draggable
            class="list-group kanban-colum"
            :list="myGitRepos"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in myGitRepos"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <h1>{{element.id}}</h1>
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
          >Want To Add</div>
          <hr />
          <draggable class="list-group kanban-colum" :list="arrMyRepo" group="tasks" style="text-align: center;">
            <a
              class="list-group-item mb-2"
              v-for="element in arrMyRepo"
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
    this.userInfo.uid = 'junhok82'
    // this.userInfo.uid = 'YNNJN'
    this.userInfo.provider = 'github'
    // this.userInfo.provider = 'google'
    console.log("userInfo.uid: ", this.userInfo.uid)

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    this.userInfo.id = 15
    // this.userInfo.id = 21
    this.arrMyRepo = this.myLockerRepos;

  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    myRepoInfo() {
      return this.$store.state.myRepoInfo;
    },
    ...mapState(["userInfo", "myGitRepos", "myLockerRepos"]),
  },
  data() {
    return {
      token: "",
      accessToken: "",
      arrMyRepo: this.myLockerRepos,
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
    async saveMine(repos){
      for(var i=0; i<repos.length; i++){
        // console.log("repos name여기!", repos[i].name)
        // console.log("repos repoName여기!", repos[i].repoName)
        // console.log("userInfo id여기!", this.userInfo.id)
        await axios.post("/v1/github?name="+repos[i].name+"&repoName="+repos[i].repoName+"&pk="+this.userInfo.id)

        
        // .then((response) => {
        //   console.log("myRepo 추가 - response", response)
        //   console.log("i : ", i)
        // })
        // .catch((err) => {
        //   console.log("myRepo 추가 - err", err)
        // });
          // this.$router.push({name: "mypage"});
      }
      this.getLockerRepos({id: this.userInfo.id, uid: this.userInfo.uid})
      alert("저장되었습니다!")
    },
    link(url){
      window.open(url);
    }
  }
};
</script>
<style scoped>
.kanban-colum {
  min-height: 300px;
}
</style>