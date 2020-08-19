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
import { mapState, mapActions } from 'vuex';
import axios from '../../../lib/axios-common';

export default {
  created() {
    this.showRepo;

    // 토큰 값 받아오기
    let token = localStorage.getItem("access_token");
    this.token = token;
    // this.token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNyIsInJvbGVzIjpbXSwiaWF0IjoxNTk3ODQyNDQ1LCJleHAiOjE1OTc4NDYwNDV9.wFfyI0CCXGTOTWz_NlY8bo0ni4G9Ybt6yqTKsl0TGrU"
    let accessToken = localStorage.getItem("github_token");
    this.accessToken = accessToken;
    // this.accessToken = "3397bf60370c85be291256743cf6f6ea86744dba"
    
    // this.userInfo.uid='jane399'
    // this.userInfo.uid='junhok82'
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
    ...mapActions(["getRepos"]),
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
            this.myLockerRepos.splice(index, 1);
            this.getRepos({ token: this.token, accessToken: this.accessToken, uid: this.userInfo.uid, id: this.userInfo.id})
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
