<template>
  <div class="container">
    <v-row>
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div style="font-size: 1.5rem; font-weight: bold;" class="pl-5 pt-2">My Github Repository</div>
          <hr />
          <draggable
            class="list-group kanban-colum"
            :list="arrGitRepo"
            group="tasks"
            style="text-align: center;"
          >
            <a
              href
              class="list-group-item mb-2"
              v-for="element in arrGitRepo"
              :key="element.herf"
              style="border-radius: 10px;"
            >
              <img align="left" :src="element.src" />
            </a>
          </draggable>
        </div>
      </v-col>
      <v-col>
        <div class="p-2" style="background-color: #EDE7F6; border-radius: 10px;">
          <div style="font-size: 1.5rem; font-weight: bold;" class="pl-5 pt-2">My Locker Repository</div>
          <hr />
          <draggable class="list-group kanban-colum" :list="arrMyRepo" group="tasks">
            <a
              :href="element.href"
              class="list-group-item mb-2"
              v-for="element in arrMyRepo"
              :key="element.name"
              style="border-radius: 10px;"
            >
              <img align="left" :src="element.src" />
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

export default {
  name: "kanban-board",
  components: {
    draggable
  },
  created() {
    this.showRepo;
    this.arrGitRepo;
    // let token = localStorage.getItem("access_token");
    // this.token = token;
    this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1OTc2NjI2OTMsImV4cCI6MTU5NzY2NjI5M30.8JUjNudKn8MfVEGxB8_8IEW287ORXUXU-f171b52YI4"
    // let accessToken = localStorage.getItem("github_token");
    // this.accessToken = accessToken;
    this.accessToken = "01f6d998d01ec81bf8aee089a72f808064ffc519"
    console.log("arrGitRepo", this.arrGitRepo)
    this.getRepos({ token: this.token, accessToken: this.accessToken})
    this.userInfo.login = 'jane399';
    // var temp = [];
    // for(var i=0; i < this.arrGitRepo.length; i++){
    //   console.log("arrrrrrrrrrrrrrr", this.arrGitRepo[i].name)
    //   if(this.arrGitRepo[i].name === this.userInfo.login){
    //     temp[i]= { repoUrl: this.arrGitRepo[i].repoUrl, src: this.arrGitRepo[i].src}
    //   }
    // }
    // this.repoInfo = temp;
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    arrGitRepo(){
      return this.$store.state.arrGitRepo;
    }
  },
  data() {
    return {
      ...mapState(["userInfo"]),
      token: "",
      accessToken: "",
      arrMyRepo: [],
      repoInfo: []
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
<style scoped>
.kanban-colum {
  min-height: 300px;
  /* height: auto; */
  /* min-height: 200px; */
}
</style>