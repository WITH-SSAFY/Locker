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
              <div id="nick">{{ nickname }}</div>
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
                <p class="d-inline" style="font-size: 1.8rem">{{ nickname }}
                  <v-icon size="30" class="ml-1" color="#7C4DFF">mdi-arm-flex</v-icon>
                </p>
              </div>  
              <p class="ml-2">병아리에서 벗어나고 싶은 자바 개발자입니다 블라블라</p>
            </div>
          </div>

          <!-- 댓글 보기 창 -->
          <v-row>
            <v-col
              cols="12"
              md="10"
              v-for="comment in viewerComment"
              :key="comment.rid"
            >
              <v-card flat>
                <div class="d-flex justify-content-between">
                  <div>
                    <span class="mr-5"><strong>{{ comment.replynickname }}</strong></span>
                    <span>{{ comment.replytext }}</span>
                    <v-text-field
                      label="수정 내용을 입력하세요"
                      v-if="comment.rid === btnNum"
                      v-model="editComment"
                      required
                    >
                    </v-text-field>
                    <v-btn
                      v-if="comment.rid === btnNum"
                      dark
                      elevation="0"
                      @click="fetchComment(pid, comment.rid, editComment)"
                    >
                    수정
                    </v-btn>
                  </div>

                  <div>
                    <small class="mr-5">{{ comment.created }}</small>
                    <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="goEditComment(pid, comment.rid)">edit</button>
                    <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="deleteComment(pid, comment.rid)">delete</button>
                  </div>
                </div>
              </v-card>
            </v-col>
          </v-row>

          <!-- 댓글 작성 창 -->
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
      </v-row>

      <!-- 사이드바 -->
      <!-- <v-col>
        <side-bar class="side" text-align="left"></side-bar>
      </v-col> -->
      
    </v-container>
  </div>
</template>

<script>
  // import { mapState } from "vuex"
  // import SideBar from "../SideBar.vue"
  import { Viewer } from '@toast-ui/vue-editor';
  import('../../assets/css/read-post.css')
  import('../../assets/css/side-style.css')
  import '@toast-ui/editor/dist/toastui-editor-viewer.css';
  import axios from "../../lib/axios-common.js";
  
  export default {
    created(){
      this.viewerText;
      this.$store.dispatch('getCommentList', this.$store.state.pid)
      this.viewerComment;
    },
    components: {
      Viewer,
      // SideBar
    },
    data(){
      return {
        //viewerText : '',
        text: '',
        tags: ["java", "login", "cookie"],
        btnNum: null,
        showButton: true,
        test: "nothing",
        editComment: null,
        rid: null,
      }
    },
    computed : {
      title(){
        return this.$store.state.myDetailTitle;
      },
      nickname(){
        return this.$store.state.nickname;
      },
      pid() {
        return this.$store.state.pid;
      },
      viewerText(){
        return this.$store.state.myDetail;
      },
      viewerComment(){
        return this.$store.state.commentList;
      },
    },
    methods:{
      goEditDetail (pid) {
        this.$store.dispatch('goEditDetail', pid);
        //this.$router.push({name: "editPost"});  
      },
      deleteDetail (pid) {
        this.$store.dispatch('deleteDetail', pid);
      },
      postComment (pid) {
        axios
          .post("v1/comment", { replytext: this.text,
                                replyemail: this.$store.state.userInfo.email,
                                replynickname: this.$store.state.userInfo.nickname,
                                pid,
          })
          .then(() => {
            this.$store.dispatch('getCommentList', pid);
            this.text='';
            // this.$router.push('/readPost')
          })
          .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
      },
      goEditComment (pid, rid) {
        // TODO: 본인 댓글만 수정 가능 조건 추가
        this.btnNum = rid
        this.showButton = false;
        axios
          .get("v1/comment/" + pid + '/' + rid)
          .then(response => {
            console.log(response.data)
            this.editComment = response.data.replytext
          })
          .catch(
            exp => alert("내 댓글 가져오기에 실패했습니다 " + exp)
          );
      },
      deleteComment (pid, rid) {
        // TODO: 본인 댓글만 삭제 가능 조건 추가
        axios
          .delete("v1/comment/" + pid + '/' + rid)
          .then(() => {
            this.$store.dispatch('getCommentList', pid)
          })
          .catch(exp => alert("내 댓글 삭제에 실패했습니다" + exp))
      },
      fetchComment (pid, rid, editComment) {
        axios
          .put("v1/comment", {pid, rid, replytext: editComment})
          .then(() => {
            alert("댓글 등록이 완료되었습니다")
            this.btnNum = null
            this.$router.push('/readPost');
            this.$store.dispatch('getCommentList', pid)
          })
          .catch(
            exp => alert("내 댓글 수정에 실패했습니다 " + exp)
          );
      },
    }
  };
</script>

<style scoped>
    #container {
        width : 65%;/* 반응형으로 수정 필요 */
        margin : 70px auto 0 auto;
    }
</style>
