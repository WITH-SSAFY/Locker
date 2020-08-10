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
      <v-btn 
        dark
        @click="postComment(pid, parentid)"
        height="65%"
      >
      작성
      </v-btn>
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
    }
  },
  methods: {
    postComment (pid, parentid) {
      axios
        .post("v1/comment", { replytext: this.text,
                              replyemail: this.$store.state.userInfo.email,
                              replynickname: this.$store.state.userInfo.nickname,
                              parentid, pid,
        })
        .then(() => {
          this.$store.dispatch('getCommentList', pid);
          this.text = '';
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
    },
  }
}
</script>

<style>

</style>