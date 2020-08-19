<template>
<v-container>
  <div style="width: 100%;">
    <v-row style="min-height: 300px;">
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >My Locker Repository</div>
          <hr />
          <div
            class="list-group kanban-colum"
            :list="myLockerRepos"
            group="tasks"
            style="text-align: center;"
            >
            <a
              class="list-group-item mb-2"
              v-for="(element, index ) in myLockerRepos"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <img alt="left" id="stat" :src="element.src">
              <v-btn
                depressed
                color="#7C4DFF"
                text
                icon
                @click="del(index, element.name, element.repoName)"
                >
                <v-icon>mdi-trash-can-outline</v-icon>
              </v-btn>
            </a>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</v-container>
</template>
<script>
import { mapState } from 'vuex';
import axios from '../../../lib/axios-common';

export default {
  created() {
    this.showRepo;

    // 토큰 값 받아오기
    let token = localStorage.getItem("access_token");
    this.token = token;
    // this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1OTc4MzY5NTUsImV4cCI6MTU5Nzg0MDU1NX0.e8i9KUGIc1y0-RfUaXGWU3f_M1pyDcs-S8gwTl_JnXg"
    let accessToken = localStorage.getItem("github_token");
    this.accessToken = accessToken;
    // this.accessToken = "8b0949070511750d5a23aa6d9a81c5a85b67c865"
    
    // this.userInfo.uid = 'YNNJN'
    // this.userInfo.provider = 'github'
    // this.userInfo.provider = 'google'
    // console.log("userInfo.uid: ", this.userInfo.uid)

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    // this.userInfo.id = 15
    // this.userInfo.id = 21
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["myLockerRepos","userInfo"])
  },
  data: () => {
    return {
    };
  },
  methods: {
    // ...mapActions(["getRepos", "getLockerRepos"]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    },
    del(index, name, repoName){
      // alert("name: "+name+" / repoName: "+repoName)
      var result = confirm(repoName+"을/를 삭제 하시겠어요?");
      if (result) {
        axios
          .delete("/v1/github?name="+name+"&repoName="+repoName)
          .then((res)=> {
            console.log("res", res);
            // this.$store.dispatch('getLockerRepos',{id: this.userInfo.id});
            // console.log("index", index);
            this.myLockerRepos.splice(index, 1);
          })
          .catch((err)=> {
            console.log("err", err);
          })
      }
    }
  }
};
</script>
<style scoped>
</style>
