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
              <div id="wdate">{{ created }}</div>
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
              <p class="ml-2">{{ userInfo.introduction }}</p>
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
                  <div class="ml-auto">
                    <small class="mr-5">{{ comment.created }}</small>
                    <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="goEditComment(pid, comment.rid, comment.replytext)">edit</button>
                    <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="deleteComment(pid, comment.rid)">delete</button>
                    <button v-if="!comment.depth" @click="goReply(comment.rid, comment.parentid, comment.depth)">
                      <v-icon>mdi-reply</v-icon>
                    </button>
                    <button v-if="comment.depth" @click="showReply(comment.rid)">
                      <v-icon>mdi-chevron-up</v-icon>
                    </button>              
                  </div>
                </div>

                <!-- 대댓글 없을 시 대댓글 작성 창 보이기-->
                <div v-if="comment.rid === inputNum" class="px-5 py-2">
                  <commentCreate/>
                </div>
                
                <!-- 대댓글 있으면 해당 parentid에 대한 댓글리스트 불러오기 -->
                <div v-if="comment.rid === listNum" class="px-5 py-2">
                  <commentList/>
                </div>

              </v-card>
            </v-col>
          </v-row>

          <div class="row">
            <div class="col-md-10">
              <commentCreate/>
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
  // import SideBar from "../SideBar.vue"
  import { mapState } from "vuex"
  import commentCreate from "../commentCreate.vue"
  import commentList from "../commentList.vue"
  import { Viewer } from '@toast-ui/vue-editor';
  import('../../assets/css/read-post.css')
  import('../../assets/css/side-style.css')
  import '@toast-ui/editor/dist/toastui-editor-viewer.css';
  import axios from "../../lib/axios-common.js";
  
  export default {
    created(){
      this.userInfo;
      this.viewerText;
      this.$store.dispatch('getCommentList', this.$store.state.pid)
      this.viewerComment;
    },
    components: {
      Viewer,
      commentCreate,
      commentList,
      // SideBar
    },
    data(){
      return {
        tags: ["java", "login", "cookie"],
        btnNum: null,
        showButton: true,
        editComment: null,
        rid: null,
        showInput: false,
        inputNum: null,
        showList: false,
        listNum: null,
        depth: null,
      }
    },
    computed : {
      ...mapState(["userInfo"]),
      title(){
        return this.$store.state.myDetailTitle;
      },
      nickname(){
        return this.$store.state.nickname;
      },
      pid() {
        return this.$store.state.pid;
      },
      created() {
        return this.$store.state.created;
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
      goEditComment (pid, rid, text) {
        // TODO: 본인 댓글만 수정 가능 조건 추가
        this.btnNum = rid
        this.showButton = false;
        this.editComment = text;
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
            this.showButton = true;
          })
          .catch(
            exp => alert("내 댓글 수정에 실패했습니다 " + exp)
          );
      },
      goReply (rid, parentid, depth) {
        this.inputNum = rid;
        this.showButton = false;
        this.showInput = !this.showInput;
        this.$store.commit('goreply', { rid, parentid, depth })   
      },
      showReply (rid, parentid) {
        console.log(parentid)
        this.listNum = rid;
        this.showButton = false;
        this.showList = !this.showList;
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
