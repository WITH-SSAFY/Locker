<template>

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
  },
  methods: {
    postComment (pid) {
      console.log(pid)
      axios
        .post("v1/comment", { replytext: this.text,
                              replyemail: this.$store.state.userInfo.email,
                              replynickname: this.$store.state.userInfo.nickname,
                              pid,
        })
        .then(() => {
          this.$store.dispatch('getCommentList', pid);
          this.text='';
        })
        .catch(exp => alert("댓글 작성에 실패했습니다" + exp))
    },
  }
}
</script>

<style>

</style>