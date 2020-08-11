<template>

  <!-- 댓글 작성 창 -->
  <div class="d-flex">
    <div class="input-group">
      <v-text-field
        label="댓글"
        outlined
        v-model="text"
      >
      </v-text-field>
    </div>
    <div class="input-group-append ml-5" >
      <div v-if="parentid == 0">
        <v-btn 
          dark
          @click="postComment(pid)"
          height="65%"
        >
        작성
        </v-btn>
      </div>

      <!-- 대댓글 -->
      <div v-if="parentid != 0">
        <v-btn 
          dark
          @click="postReply(pid, parentid)"
          height="65%"
        >
        작성
        </v-btn>
      </div>


    </div>
  </div>
  
</template>

<script>
import axios from "../lib/axios-common.js";

export default {
  name: 'commentCreate',
  data() {
    return {
      text: '',
    }
  },
  computed: {
    pid() {
      return this.$store.state.pid;
    },
    parentid() {
      return this.$store.state.parentid;
    },
    depth() {
      return this.$store.state.depth;
    },
    rid() {
      return this.$store.state.rid;
    }
  },
  created() {
    this.parentid
    this.depth
  },
  methods: {
    postComment (pid) {
      
      axios
        .post("v1/comment", { replytext: this.text,
                              replyemail: this.$store.state.userInfo.email,
                              replynickname: this.$store.state.userInfo.nickname,
                              pid
        })
        .then(() => {

          this.$store.dispatch('getCommentList', pid);
          this.text = '';
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
    },

    postReply (pid, parentid) {
      axios
        .post("v1/comment", { replytext: this.text,
                              replyemail: this.$store.state.userInfo.email,
                              replynickname: this.$store.state.userInfo.nickname,
                              pid, parentid
        })
        .then(() => {

          this.$store.dispatch('getCommentList', pid);
          this.text = '';
          this.$store.state.parentid = null;
          // 대댓 작성 창 없애는 로직 필요
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
    },
  }
}
</script>

<style>

</style>