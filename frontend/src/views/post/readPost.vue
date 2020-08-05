<template>
  <div>
    <v-container class="mt-md-6">
      <v-row>
        <v-col
          cols="12"
          md="10"
          class="p-0"
        >
          <div class="mx-5 py-3">
            <div class="row post_title ml-1" >{{ title }}
            </div>
            <div class="under-line"></div>
            <div class="my-6">
              <div id="nick">{{ writer }}</div>
              <div id="dash">/</div>
              <div id="wdate">2020.07.29</div>
            </div>
            <div class="float-right">
              <button class="btn btn-light badge-pill mr-2" @click="goEditDetail(pid)"><span>edit</span></button>
              <button class="btn btn-light badge-pill" @click="deleteDetail(pid)"><span>delete</span></button>
            </div>
            <div>
              <v-btn
                class="tag_list"
                v-for="(tag, index) in tags" :key="index"
                
                small
                color="brown lighten-4"
                rounded
                elevation="0"
              >
                {{ tag }}
              </v-btn>
            </div>
          </div>
          <hr>
          <div class="row mx-5 py-3">
            <viewer :initialValue="viewerText" height="100%" />
          </div>
          
          <div class="row mx-5 py-3 writer_info">
            <div class="col-md-2 col-sm-1">
              <v-icon size="80">mdi-account-circle-outline</v-icon>
            </div>
            <div class="col-md-7 d-flex-wrap">
              <div class="mx-2">
                <p class="d-inline" style="font-size: 1.8rem">user_nick
                  <v-icon size="30" class="ml-1" color="#7C4DFF">mdi-arm-flex</v-icon>
                </p>
              </div>  
              <p class="ml-2">병아리에서 벗어나고 싶은 자바 개발자입니다 블라블라</p>
            </div>
          </div>

          <!-- 댓글 창 -->
          <div class="row mr-4">
            <div class="col-md-10">
              <v-text-field
                label="댓글"
                outlined
                v-model="text"
              >
              </v-text-field>
            </div>
            <div class="col-md-1">
              <v-btn 
                style="height: 65%;"
                dark
                elevation="0"
                @click="postComment(pid)"
              >
              작성
              </v-btn>
            </div>
          </div>
        </v-col>

        <!-- 사이드바 -->
        <v-col>
          <side-bar class="side" text-align="left"></side-bar>
        </v-col>

      </v-row>
    </v-container>
  </div>
</template>

<script>
  // import { mapState } from "vuex"
  import SideBar from "../SideBar.vue"
  import { Viewer } from '@toast-ui/vue-editor';
  import('../../assets/css/read-post.css')
  import('../../assets/css/side-style.css')
  //import axios from "../js/axios-common.js"  
  import '@toast-ui/editor/dist/toastui-editor-viewer.css';
  import axios from "../../lib/axios-common.js";
  
  export default {
    created(){
        //서버로 부터 데이터 받아옴
        // axios
        //     .get("/post")
        //     .then( response =>{
        //         this.viewerText = response.data;
        //     })
        //     .catch(exp => alert("글 읽기 실패 "+exp))

        //this.viewerText = "# this is test\r\n![image](https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4sri/image/sLl-6IxU6iwhAmD-kelYRD_nUS4.jpeg)";
      this.viewerText;
    },
    components: {
      Viewer,
      SideBar
    },
    data(){
      return {
        //viewerText : '',
        text: '',
        tags: ["java", "login", "cookie"],
      }
    },
    computed : {
      title(){
        return this.$store.state.myDetailTitle;
      },
      writer(){
        return this.$store.state.writer;
      },
      pid() {
        return this.$store.state.pid;
      },
      viewerText(){
        //  console.log("readMyDetail: ",this.$store.state.myDetail);
         return this.$store.state.myDetail;
      }  
    },
    methods:{
      checkViewerText () {
        console.log("check: "+ this.viewerText);
      },
      goEditDetail (pid) {
        this.$store.dispatch('goEditDetail', pid);
        //this.$router.push({name: "editPost"});  
      },
      deleteDetail (pid) {
        this.$store.dispatch('deleteDetail', pid);
      },
      postComment (pid) {
        console.log("text : " + this.text)
        axios
          .post("v1/comment", { replyText : this.text,
                                pid
          })
          .then(res => {
            console.log(res.data)
          })
          .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
      }
    }
  };
</script>


<style scoped>
    #container {
        width : 65%;/* 반응형으로 수정 필요 */
        margin : 70px auto 0 auto;
    }
</style>