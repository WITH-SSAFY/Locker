<template>
  <div class="mt-10" style="width: 100%;">
    <v-row style="min-height: 300px;">
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >Team Blog Repository</div>
          <hr />
          <div
            class="list-group kanban-colum"
            :list="teamLockerRepos"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in teamLockerRepos"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <img alt="left" id="stat" :src="element.src">
              <v-btn
                class="ml-5"
                depressed
                color="#7C4DFF"
                dark
                rounded
                style="font-weight: bolder;"
                @click="getDetail(element.name, element.repoName)"
              >
                <!-- router :to="{ name: 'repoDetail' }" -->
              상세보기
              </v-btn>
              <v-btn
                class="ml-6"
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
</template>
<script>
import { mapState, mapActions } from 'vuex';
import axios from '../../../lib/axios-common';

export default {
  created() {
    this.showRepo;
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["teamLockerRepos", "userInfo"]),
  },
  data: () => {
    return {
    };
  },
  methods: {
    ...mapActions(["getRepoDetail"]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    },
    del(index, name, repoName){
      var result = confirm(repoName+"을/를 삭제 하시겠어요?");
      if (result) {
        axios
          .delete("/v1/github?name="+name+"&repoName="+repoName)
          .then((res)=> {
            console.log("res", res);
            this.teamLockerRepos.splice(index, 1);
            this.getRepos({ token: this.token, accessToken: this.accessToken, uid: this.userInfo.uid, id: this.userInfo.id})
          })
          .catch((err)=> {
            console.log("err", err);
          })
      }
    },
    getDetail(name, repoName){
      // alert("name: "+name+"/ repoName : "+repoName);
      this.getRepoDetail({name, repoName});
    }
  }
};
</script>