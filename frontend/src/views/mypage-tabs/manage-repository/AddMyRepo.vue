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
            :list="myRepoInfo"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in myRepoInfo"
              :key="element.repoUrl"
              style="border-radius: 10px;"
              :href="element.repoUrl"
            >
              <img alt="left" id="stat" :src="element.src">
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
              :href="element.repoUrl"
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
    this.myRepoInfo;
    console.log("myRepoInfo:", this.myRepoInfo);
    this.userInfo.id = 15
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    myRepoInfo() {
      return this.$store.state.myRepoInfo;
    },
    ...mapState(["userInfo"]),
  },
  data() {
    return {
      token: "",
      accessToken: "",
    };
  },
  methods: {
    ...mapActions([]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    },
    saveMine(repos){
      // console.log("repos여기", repos);
      for(var i=0; i<repos.length; i++){
        console.log("repos name여기!", repos[i].name)
        console.log("repos repoName여기!", repos[i].repoName)
        console.log("userInfo id여기!", this.userInfo.id)
        axios
            .post("/v1/github?name="+repos[i].name+"&repoName="+repos[i].repoName+"&pk="+this.userInfo.id)
            .then((response) => {
              console.log("myRepo 추가 - response", response)
            })
            .catch((err) => {
              console.log("myRepo 추가 - err", err)
            });
        // this.getLockerRepos( {id: this.userInfo.id} );
      }
    }
  }
};
</script>
<style scoped>
.kanban-colum {
  min-height: 300px;
}
</style>