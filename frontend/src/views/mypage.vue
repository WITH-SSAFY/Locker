<template>

  <div>
    <v-container class="mt-md-6">
      <v-row>
        <v-col
          cols="12"
          md="10"
          class="p-0"
        >
          <div class="row align-items-center mx-5 mb-5">
            <div class="col-md-4 d-none d-sm-block align-self-start">
              <div class="d-flex justify-content-center" style="background-color: #f2f3f4;">
                <v-icon size="250" class="mb-5" style="z-index: 1;">mdi-account-circle-outline</v-icon>
              </div>
              <!-- 장식 박스 -->
              <div>
                <v-row class="no-gutters">
                  <v-col sm="6">
                    <v-card
                      style="height: 7rem;"
                      tile
                      color="#7C4DFF"
                    >
                    </v-card>
                  </v-col>
                  <v-col
                    sm="6"
                    offset-sm="6"
                  >
                    <v-card
                      style="height: 7rem;"
                      tile
                      color="#7C4DFF"
                    >
                    </v-card>
                  </v-col>
                </v-row>

              </div>
            </div>

            <!-- 텍스트 섹션 -->
            <div class="col-md-8 d-flex-wrap align-self-start">
              <div class="d-flex align-items-center justify-content-between p-0">
                <p class="lead d-inline my-0" style="font-size: 5rem;"><strong>{{ userInfo.nickname }}</strong></p>
                <!-- <a href="" class="d-inline btn btn-outline-dark btn-sm">Edit Profile</a> -->
                <div class="d-inline-flex ml-5">
                  <v-icon size="60" style="color: #EDE7F6;">mdi-arm-flex</v-icon>
                  <p class="d-inline"><small>1F_병아리</small></p>
                </div>
              </div>
              <p class="ml-2">병아리에서 벗어나고 싶은 자바 개발자입니다 블라블라</p>
              <div class="d-inline">
                <a><v-icon size="35" style="color: black; padding-right: 1rem;">mdi-github</v-icon></a>
                <a><v-icon size="35" style="color: black;">mdi-google-plus</v-icon></a>
              </div>
              <!-- <div>
                <strong class="d-inline ml-2"></strong><p class="d-inline mr-4">posts</p>
                <a href="" class="d-inline text-decoration-none text-reset" data-toggle="modal" data-target="#followerModal">
                  <strong class="d-inline px-1"></strong>
                  <p class="d-inline mr-4">followers</p>
                </a>
                <a href="" class="d-inline text-decoration-none text-reset" data-toggle="modal" data-target="#followingModal">
                  <strong class="d-inline px-1"></strong>
                  <p class="d-inline mr-4">following</p>
                </a>
              </div> -->
            </div>
          </div>
          <hr>
        </v-col>

        <!-- 사이드바 -->
        <v-col>
          <side-bar class="side" text-align="left"></side-bar>
        </v-col>
      </v-row>

      <!-- 탭 -->
      <v-row class="no-gutters">
        <v-col
          cols="12"
          md="10"
        >
          <v-tabs
            v-model="activeTab"
            background-color="transparent"
            color="#7C4DFF"
            grow
          >
          
            <v-tab key="" to="/">
              <p><strong>Article</strong></p>
            </v-tab>
            <v-tab key="repository" to="/repository">
              <p><strong>Repository</strong></p>
            </v-tab>
            <v-tab key="" to="/">
              <p><strong>Introduce</strong></p>
            </v-tab>

            <v-tab-item id="/repository">
              <router-view v-if="activeTab === 'repository'"/>
            </v-tab-item>

          </v-tabs>
        </v-col>
      </v-row>

      <!-- 포스트 리스트 -->
      <v-row>
        <v-col
          cols="12"
          md="10"
          v-for="(myPost, index) in myPostList"
          :key="myPost.pid"
          style="padding: 2% 8%;"
        >
          <v-card>
            <v-card-title class="title" @click="showMyDetail(myPost.pid)">{{ myPost.title }}</v-card-title>
            
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn icon @click="like(index)">
                <v-icon :id="index">mdi-heart</v-icon>
              </v-btn>
              <v-btn icon><v-icon>mdi-bookmark</v-icon></v-btn>
              <v-btn icon><v-icon>mdi-share-variant</v-icon></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      <!-- <category/> -->
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex"
// import category from "./category.vue"
import SideBar from "./SideBar.vue"
import('../assets/css/side-style.css')

export default {
    created(){
      this.$store.dispatch('getMyPostList');
      this.userInfo;
      this.myPostList;
      //likes 배열 초기화 작업
      this.likes.length=this.size;
      for (let i = 0; i < this.size; i++) {
        this.likes.push(false);
      }
    },
    computed:{
        ...mapState(["userInfo","myPostList"]),
        size(){
          return this.$store.state.myPostList.length;
        },
        likes(){
          return [];
        }
    },
    data() {
      return {
        tab: null,
        items: [
          '글',
          '카테고리',
          '소개'
        ],
      }
    },
    components: {
      SideBar,
      // category,
    },
    methods:{
      showMyDetail (pid) {
        this.$store.dispatch('showMyDetail', pid);
        //this.$router.push({name: "readPost"});
      },
       //좋아요 클릭시
      like(index){
        if(this.likes[index]){
          this.likes[index] = false;
          let like = document.getElementById(index);
          like.style.color = "grey";
        }else{
          this.likes[index] = true;
          let like = document.getElementById(index);
          like.style.color = "red";
        
        }
      },
    }
    
}
</script>
<style scoped>
.title {
  cursor: pointer;
}
</style>