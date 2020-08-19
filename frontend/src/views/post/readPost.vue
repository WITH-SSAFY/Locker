<template>
  <div>
    <v-container class="mt-md-6">
      <v-row>
        <v-col cols="1" class="p-0">
          <div class="paginate paginate--left" @click="goPrevPage">
            <a class="paginate__link" href="#">
              <i></i>
              <i></i>
            </a>
            <span class="paginate__info">Prev</span>
          </div>
        </v-col>

        <v-col cols="10" class="p-0">
          <div class="mx-5 py-3">
            <div class="row post_title ml-1">{{ title }}</div>
            <div class="under-line"></div>
            <div class="my-6">
              <div id="nick">{{ nickname }}</div>
              <div id="dash">/</div>
              <div id="wdate">{{ created }}</div>
              <v-btn v-if="isUserLiked" icon @click="deleteLike()" style="margin-left: 5px;">
                <v-icon color="red">mdi-heart</v-icon>
              </v-btn>
              <v-btn v-else icon @click="addLike()" style="margin-left: 5px;">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <span>{{likes}}</span>
            </div>
            <div class="float-right">
              <button
                v-if="this.userInfo.id === usr_id"
                class="btn btn-light badge-pill mr-2"
                @click="goEditDetail(pid)"
              >
                <span>edit</span>
              </button>
              <button
                v-if="this.userInfo.id === usr_id"
                class="btn btn-light badge-pill"
                @click="deleteDetail(pid)"
              >
                <span>delete</span>
              </button>
            </div>
            <div id="tag_list">
              <v-chip
                v-for="tag in tags"
                :key="tag.tagid"
                class="mx-1"
                color="#EDE7F6"
                small
                @click="searchTag(tag.tagname)"
              >{{ tag.tagname }}</v-chip>
            </div>
          </div>

          <hr>

          <div class="row mx-5 py-3">
            <!-- 마크다운 뷰어 -->
            <aside id="markdown" contenteditable style="display: none;">{{ viewerText }}</aside>
            <section id="output-html" class="markdown-body" style="display: none;"></section>
            <div id="page" class="markdown-body width: 75%;"></div>

          </div>
          <!-- <div>
            <v-btn @click="goPrevPage">이전</v-btn>
            <v-btn @click="goNextPage">다음</v-btn>
          </div>-->
          <div class="row mx-5 py-3 writer_info">
            <div class="col-md-2 col-sm-1">
              <v-icon v-if="userInfo.picture==null" size="80">mdi-account-circle-outline</v-icon>
              <v-avatar v-else size="80">
                <img :src="userInfo.picture" />
              </v-avatar>
            </div>
            <div class="col-md-7 d-flex-wrap">
              <div class="mx-2">
                <p class="d-inline" style="font-size: 1.8rem">
                  {{ nickname }}
                  <v-icon size="30" class="ml-1" color="#7C4DFF">mdi-arm-flex</v-icon>
                </p>
              </div>
              <p class="ml-2">{{ userInfo.introduction }}</p>
            </div>
          </div>

          <v-row>
            <v-col cols="12" md="10" v-for="comment in viewerComment" :key="comment.rid">
              <v-card flat>
                <!-- 대댓글 없는 경우 -->
                <div v-if="!comment.depth" class="d-flex justify-content-between">
                  <div>
                    <!-- 댓글 보기 왼쪽 -->
                    <span class="mr-5">
                      <strong>{{ comment.replynickname }}</strong>
                    </span>
                    <span>{{ comment.replytext }}</span>

                    <!-- 댓글 수정 -->
                    <v-text-field
                      label="수정 내용을 입력하세요"
                      v-if="comment.rid === btnNum"
                      v-model="editComment"
                      required
                    ></v-text-field>
                    <v-btn
                      v-if="comment.rid === btnNum"
                      dark
                      elevation="0"
                      @click="fetchComment(pid, comment.rid, editComment)"
                    >수정</v-btn>
                  </div>

                  <!-- 댓글 보기 오른쪽 -->
                  <div class="ml-auto">
                    <small class="mr-5">{{ comment.created }}</small>
                    <button
                      v-if="comment.userid === userInfo.id"
                      class="btn btn-sm btn-light mr-2"
                      @click="
                        goEditComment(pid, comment.rid, comment.replytext)
                      "
                    >edit</button>
                    <button
                      v-if="comment.userid === userInfo.id"
                      class="btn btn-sm btn-light mr-2"
                      @click="deleteComment(pid, comment.rid)"
                    >delete</button>
                    <button v-if="!comment.depth" @click="goReply(comment.rid, comment.depth)">
                      <v-icon>mdi-reply</v-icon>
                    </button>
                    <button v-if="comment.depth" @click="showReply(comment.rid)">
                      <v-icon>mdi-chevron-up</v-icon>
                    </button>
                  </div>
                </div>

                <!-- 대댓글 있는 경우 -->

                <div v-if="comment.depth" class="pl-5 d-flex justify-content-between">
                  <div>
                    <!-- 댓글 보기 왼쪽 -->
                    <span class="mr-5">
                      <strong>{{ comment.replynickname }}</strong>
                    </span>
                    <span>{{ comment.replytext }}</span>

                    <!-- 댓글 수정 -->
                    <v-text-field
                      label="수정 내용을 입력하세요"
                      v-if="comment.rid === btnNum"
                      v-model="editComment"
                      required
                    ></v-text-field>
                    <v-btn
                      v-if="comment.rid === btnNum"
                      dark
                      elevation="0"
                      @click="fetchComment(pid, comment.rid, editComment)"
                    >수정</v-btn>
                  </div>

                  <!-- 댓글 보기 오른쪽 -->
                  <div class="ml-auto">
                    <small class="mr-5">{{ comment.created }}</small>
                    <button
                      v-if="comment.userid === userInfo.id"
                      class="btn btn-sm btn-light mr-2"
                      @click="
                        goEditComment(pid, comment.rid, comment.replytext)
                      "
                    >edit</button>
                    <button
                      v-if="comment.userid === userInfo.id"
                      class="btn btn-sm btn-light mr-2"
                      @click="deleteComment(pid, comment.rid)"
                    >delete</button>
                    <button>
                      <div
                        v-if="!comment.depth"
                        @click="
                          goReply(comment.rid, comment.parentid, comment.depth)
                        "
                      >
                        <v-icon>mdi-reply</v-icon>
                      </div>
                      <div v-if="comment.depth">
                        <v-icon>mdi-chevron-up</v-icon>
                      </div>
                    </button>
                  </div>
                </div>

                <!-- 대댓글 없을 시 대댓글 작성 창 보이기-->
                <div v-if="comment.rid === inputNum" class="px-5 py-2">
                  <!-- 대댓글 작성 창 -->
                  <div class="d-flex">
                    <div class="input-group">
                      <v-text-field
                        label="대댓글"
                        outlined
                        v-model="reply"
                        @keyup.enter="postReply(pid, comment.rid)"
                      ></v-text-field>
                    </div>
                    <div class="input-group-append ml-5">
                      <div>
                        <v-btn dark @click="postReply(pid, comment.rid)" height="65%">작성</v-btn>
                      </div>
                    </div>
                  </div>
                </div>
              </v-card>
            </v-col>
          </v-row>

          <!-- 댓글 작성 창 -->
          <div class="row">
            <div class="col-md-10">
              <!-- 댓글 작성 창 -->
              <div class="d-flex">
                <div class="input-group">
                  <v-text-field label="댓글" outlined v-model="text" @keyup.enter="postComment(pid)"></v-text-field>
                </div>
                <div class="input-group-append ml-5">
                  <div>
                    <v-btn dark @click="postComment(pid)" height="65%">작성</v-btn>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </v-col>
        <v-col cols="1" class="p-0">
          <div class="paginate paginate--right" @click="goNextPage">
            <a class="paginate__link" href="#">
              <i></i>
              <i></i>
            </a>
            <span class="paginate__info">Next</span>
          </div>
        </v-col>
      </v-row>

      <!-- 사이드바 -->
      <!-- <v-col
        cols="2"
      >
        <side-bar class="side" text-align="left"></side-bar>
      </v-col>-->
    </v-container>
  </div>
</template>

<script>
// import SideBar from "../SideBar.vue"
import { mapState } from "vuex";
import { Viewer } from "@toast-ui/vue-editor";
import("../../assets/css/read-post.css");
import("../../assets/css/side-style.css");
import "@toast-ui/editor/dist/toastui-editor-viewer.css";
import axios from "../../lib/axios-common.js";

export default {
  created() {
    this.userInfo;
    this.pid;
    this.usr_id;
    console.log(this.usr_id);
    this.flag = false;
    this.viewerText;
    this.$store.dispatch("getCommentList", this.pid);
    this.viewerComment;
    this.getTags();
    this.getLikes();
    this.checkUserLiked();
    this.flag = true;
  },
  components: {
    Viewer
    // SideBar
  },
  data() {
    return {
      tags: [],
      flag: false,
      btnNum: null,
      editComment: null,
      inputNum: null,
      showList: false,
      listNum: null,
      depth: null,
      text: "",
      reply: "",
      parentid: null,
      likes: 0, //좋아요 수
      isUserLiked: false //유저가 좋아요를 표시 했는가
    };
  },
  computed: {
    ...mapState(["userInfo"]),
    title() {
      return this.$store.state.myDetailTitle;
    },
    nickname() {
      return this.$store.state.nickname;
    },
    usr_id() {
      return this.$store.state.usr_id;
    },
    pid() {
      return this.$store.state.pid;
    },
    email() {
      return this.$store.state.email;
    },

    created() {
      return this.$store.state.created;
    },
    viewerText() {
      return this.$store.state.myDetail;
    },
    viewerComment() {
      return this.$store.state.commentList;
    }
  },

  async beforeRouteUpdate(from, to, next) {
    this.flag = false;
    this.userInfo;
    this.pid;
    await this.viewerText;
    this.$store.dispatch("getCommentList", this.pid);
    this.viewerComment;
    this.getTags();
    this.getLikes();
    this.checkUserLiked();
    this.flag = true;
    next();
  },

  methods: {
    goEditDetail(pid) {
      this.$store.dispatch("goEditDetail", pid);
      //this.$router.push({name: "editPost"});
    },
    deleteDetail(pid) {
      this.$store.dispatch("deleteDetail", pid);
    },
    goEditComment(pid, rid, text) {
      // TODO: 본인 댓글만 수정 가능 조건 추가
      this.btnNum = rid;
      this.editComment = text;
    },
    deleteComment(pid, rid) {
      // TODO: 본인 댓글만 삭제 가능 조건 추가
      axios
        .delete("/v1/comment/" + pid + "/" + rid)
        .then(() => {
          this.$store.dispatch("getCommentList", pid);
        })
        .catch(exp => alert("내 댓글 삭제에 실패했습니다" + exp));
    },
    fetchComment(pid, rid, editComment) {
      axios
        .put("/v1/comment", { pid, rid, replytext: editComment })
        .then(() => {
          alert("댓글 등록이 완료되었습니다");
          this.btnNum = null;
          this.$router.push("/readPost");
          this.$store.dispatch("getCommentList", pid);
        })
        .catch(exp => alert("내 댓글 수정에 실패했습니다 " + exp));
    },
    goReply(rid, depth) {
      this.inputNum = rid;
      this.$store.commit("goreply", { depth, rid });
    },

    postComment(pid) {
      //댓글 작성
      axios
        .post("/v1/comment", {
          replytext: this.text,
          replyemail: this.$store.state.userInfo.email,
          replynickname: this.$store.state.userInfo.nickname,
          pid,
          userid: this.userInfo.id
        })
        .then(() => {
          this.$store.dispatch("getCommentList", pid);
          this.text = "";
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp));
    },

    postReply(pid, rid) {
      this.parentid = rid;
      axios
        .post("/v1/comment", {
          replytext: this.reply,
          replyemail: this.$store.state.userInfo.email,
          replynickname: this.$store.state.userInfo.nickname,
          pid,
          userid: this.userInfo.id,
          parentid: this.parentid
        })
        .then(() => {
          this.$store.dispatch("getCommentList", pid);
          this.reply = "";
          this.inputNum = -1;
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp));
    },
    async getTags() {
      //서버로 부터 해당 포스트에 등록된 태그 받아옴
      console.log("getTags pid: ", this.pid);
      let response = await axios.get("/v1/tag/allitem?pid=" + this.pid);
      this.tags = response.data;
      console.log("getTags pid: ", this.tags);

      // axios
      //   .get("/v1/tag/all/" + this.$store.state.pid)
      //   .then(response => {
      //     this.tags = response.data;
      //   })
      //   .catch(exp => alert("태그 불러오기 실패" + exp));
    },
    async getLikes() {
      //서버로 부터 해당 포스트의 좋아요 수 받아옴
      let response = await axios.get("/v1/postlike/all/" + this.pid);
      this.likes = response.data;
    },
    async checkUserLiked() {
      //포스트 상세포기 실행시 유저가 해당 포스트에 좋아요를 눌렀는지 검사
      let response = await axios.get(
        "/v1/postlike/check?pid=" + this.pid + "&id=" + this.userInfo.id
      );

      if (response.data == 1) {
        this.isUserLiked = true;
      }
    },
    addLike() {
      //포스트에 좋아요수 +1 해줌
      this.addLikePost();
      //유저가 포스트에 좋아요 해준 사실 추가
      this.addUserLikePost();
      //좋아요 수 +1
      this.likes++;
      //좋아요 하트 색 변경
      this.isUserLiked = true;
    },
    async addLikePost() {
      //포스트에 좋아요수 +1 해줌
      await axios.put("/v1/postlike/add?pid=" + this.pid);
    },
    async addUserLikePost() {
      await axios.post("/v1/postlike", { pid: this.pid, id: this.userInfo.id });
    },

    deleteLike() {
      //포스트에 좋아요수 -1해줌
      this.deleteLikePost();
      //유저가 포스트에 좋아요해준 사실 삭제
      this.deleteUserLikePost();
      //좋아요 수 하나 뺌
      this.likes--;
      //좋아요 하트 색 변경
      this.isUserLiked = false;
    },
    async deleteLikePost() {
      await axios.put("/v1/postlike/delete?pid=" + this.pid);
    },
    async deleteUserLikePost() {
      await axios.delete(
        "/v1/postlike?pid=" + this.pid + "&id=" + this.userInfo.id
      );
    },
    searchTag(tagname) {
      tagname;
      this.$store.state.tagname = tagname;
      this.$router.push({ name: "search" });
    },
    goNextPage() {
      axios
        .get("/v1/post/nextPage?pid=" + this.pid)
        .then(response => {
          let nextPid = response.data;
          console.log("nextPid: ", nextPid);
          this.$store.dispatch("showMyDetail", nextPid);
        })
        .catch(() => alert("마지막 페이지 입니다."));
    },
    goPrevPage() {
      axios
        .get("/v1/post/prevPage?pid=" + this.pid)
        .then(response => {
          let prevPid = response.data;
          this.$store.dispatch("showMyDetail", prevPid);
        })
        .catch(() => alert("첫 페이지 입니다."));
    }
  },
  updated () {
    function parseMd(md){

      //ul
      // md = md.replace(/^\s*\n\-\s/gm, '<ul>\n');
      // md = md.replace(/^(\-.+)\s*([^\-])/gm, '$1\n</ul>\n\n$2');
      md = md.replace(/^(\-\s)(.+)/gm, '<li>$2</li>');
      
      //ol
      md = md.replace(/^\s*\n\d\./gm, '<ol>\n1.');
      md = md.replace(/^(\d\..+)\s*\n([^\d\.])/gm, '$1\n</ol>\n\n$2');
      md = md.replace(/^\d\.(.+)/gm, '<li>$1</li>');
      
      //blockquote
      md = md.replace(/^\>(.+)/gm, '<blockquote>$1</blockquote>');
      
      //h
      md = md.replace(/[\#]{6}\s(.+)/g, '<h6>$1</h6>');
      md = md.replace(/[\#]{5}\s(.+)/g, '<h5>$1</h5>');
      md = md.replace(/[\#]{4}\s(.+)/g, '<h4>$1</h4>');
      md = md.replace(/[\#]{3}\s(.+)/g, '<h3>$1</h3>');
      md = md.replace(/[\#]{2}\s(.+)/g, '<h2>$1</h2>');
      md = md.replace(/[\#]{1}\s(.+)/g, '<h1>$1</h1>');
      
      //images
      md = md.replace(/\!\[([^\]]+)\]\(([^\)]+)\)/g, '<img src="$2" alt="$1" />');
      
      //links
      md = md.replace(/[\[]{1}([^\]]+)[\]]{1}[\(]{1}([^\)\"]+)(\"(.+)\")?[\)]{1}/g, '<a href="$2" title="$4">$1</a>');
      
      //font styles
      md = md.replace(/[\*\_]{2}([^\*\_]+)[\*\_]{2}/g, '<b>$1</b>');
      md = md.replace(/[\*\_]{1}([^\*\_]+)[\*\_]{1}/g, '<i>$1</i>');
      md = md.replace(/[\~]{2}([^\~]+)[\~]{2}/g, '<del>$1</del>');
      
      //pre
      md = md.replace(/^\s*\n\`\`\`\s(([^\s]+))?/gm, '<pre class="$2">');
      md = md.replace(/^\`\`\`\s*\n/gm, '</pre>\n\n');
      
      //code
      md = md.replace(/[\`]{1}([^\`]+)[\`]{1}/g, '<code>$1</code>');
      
      //hr
      md = md.replace(/\-\-\-+/gm, '<hr>')

      //p
      md = md.replace(/^\s*(\n)?(.+)/gm, function(m){
        return  /\<(\/)?(h\d|ul|ol|li|blockquote|pre|img)/.test(m) ? m : '<p>'+m+'</p>';
      });
      
      //strip p from pre
      md = md.replace(/(\<pre.+\>)*\<p\>(.+)\<\/p\>/gm, '$1$2');
      
      return md;

    }

    var mdEl = document.querySelector('#markdown')
    var outputEl = document.querySelector('#output-html')
    var parse = function () {
      outputEl["innerText"] = parseMd(mdEl.innerText);
      };

    parse();
    document.querySelector('#page').innerHTML = document.querySelector('#output-html').innerText

  },  
};
</script>

<style scoped>
#container {
  width: 65%; /* 반응형으로 수정 필요 */
  margin: 70px auto 0 auto;
}

body {
  padding: 40px;
}

.paginate {
  position: absolute;
  margin: 0;
  top: 40%;
  width: 80px;
  height: 80px;
  cursor: pointer;
  transform: translate3d(0, 0, 0);
  background: none;
  border: 0;
}
.paginate__link {
  display: block;
}
.paginate i {
  position: absolute;
  top: 38%;
  left: 0;
  width: 40px;
  height: 2px;
  border-radius: 1px;
  background: black;
  transition: all 0.15s ease;
}
.paginate__info {
  display: inline-block;
  position: absolute;
  top: 42%;
  transform: translateY(-50%);
  text-transform: uppercase;
  opacity: 0;
  width: 0%;
  visability: hidden;
  overflow: hidden;
}
.paginate--left .paginate__info {
  left: 80px;
}
.paginate--right .paginate__info {
  right: 80px;
}
.paginate:hover .paginate__info {
  opacity: 1;
  width: auto;
  visability: visible;
  transition: width 0ms ease-in-out, opacity 300ms linear;
}
.paginate.paginate--left {
  right: 60px;
}
.paginate.paginate--left i {
  transform-origin: 0% 50%;
}
.paginate.paginate--left i:first-child {
  transform: translate(0, -1px) rotate(52deg);
}
.paginate.paginate--left i:last-child {
  transform: translate(0, 1px) rotate(-52deg);
}
.paginate.paginate--left:hover i:first-child {
  transform: translate(0, -1px) rotate(44deg);
}
.paginate.paginate--left:hover i:last-child {
  transform: translate(0, 1px) rotate(-44deg);
}
.paginate.paginate--left:active i:first-child {
  transform: translate(1px, -1px) rotate(40deg);
}
.paginate.paginate--left:active i:last-child {
  transform: translate(1px, 1px) rotate(-40deg);
}
.paginate.paginate--left[data-state="disabled"] i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.paginate--left[data-state="disabled"] i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.paginate--left[data-state="disabled"]:hover i:first-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.paginate--left[data-state="disabled"]:hover i:last-child {
  transform: translate(-5px, 0) rotate(0deg);
}
.paginate.paginate--right {
  left: 60px;
}
.paginate.paginate--right i {
  transform-origin: 100% 50%;
}
.paginate.paginate--right i:first-child {
  transform: translate(0, 1px) rotate(52deg);
}
.paginate.paginate--right i:last-child {
  transform: translate(0, -1px) rotate(-52deg);
}
.paginate.paginate--right:hover i:first-child {
  transform: translate(0, 1px) rotate(44deg);
}
.paginate.paginate--right:hover i:last-child {
  transform: translate(0, -1px) rotate(-44deg);
}
.paginate.paginate--right:active i:first-child {
  transform: translate(1px, 1px) rotate(40deg);
}
.paginate.paginate--right:active i:last-child {
  transform: translate(1px, -1px) rotate(-40deg);
}
.paginate.paginate--right[data-state="disabled"] i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.paginate--right[data-state="disabled"] i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.paginate--right[data-state="disabled"]:hover i:first-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate.paginate--right[data-state="disabled"]:hover i:last-child {
  transform: translate(5px, 0) rotate(0deg);
}
.paginate[data-state="disabled"] {
  opacity: 0.3;
  cursor: default;
}
</style>
