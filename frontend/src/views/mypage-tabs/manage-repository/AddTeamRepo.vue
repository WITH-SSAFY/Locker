<template>
  <div class="container">
    <!-- 저장 버튼 -->
    <v-row>
      <v-col class="py-0">
        <v-btn style="font-size: 1.5rem; float: right;" text color="#7C4DFF">
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
import { mapState } from 'vuex';

export default {
  name: "kanban-board",
  components: {
    draggable
  },
  created() {
    this.showRepo;
    this.teamRepoInfo;
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
      arrMyRepo: [],
      myRepoInfo: [],
    };
  },
  methods: {
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
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