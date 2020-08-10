<template>
  <!-- 대댓글 보기 창 -->
  <v-row>
    <v-col
      cols="12"
      md="10"
      v-for="comment in viewerReply"
      :key="comment.rid"
    >
      <v-card flat>
        <div class="d-flex justify-content-between">
          <div>
            <span class="mr-5"><strong>{{ comment.replynickname }}</strong></span>
            <span>{{ comment.replytext }}</span>
            <!-- <v-text-field
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
            </v-btn> -->
          </div>
          <div class="ml-auto">
            <small class="mr-5">{{ comment.created }}</small>
            <!-- <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="goEditComment(pid, comment.rid, comment.replytext)">edit</button>
            <button class="btn btn-sm btn-light mr-2" v-if="showButton" @click="deleteComment(pid, comment.rid)">delete</button>
            <button v-if="!comment.depth" @click="goReply(comment.rid, comment.parentid)">
              <v-icon>mdi-reply</v-icon>
            </button>
            <button v-if="comment.depth" @click="showReply(comment.rid)">
              <v-icon>mdi-chevron-up</v-icon>
            </button>               -->
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>

</template>

<script>
export default {
  name: 'commentList',
  data() {
    return {

    }
  },
  created() {
    this.viewerReply;
  },
  computed: {
    parentid() {
      return this.$store.state.parentid;
    },
    depth() {
      return this.$store.state.depth;
    },
    rid() {
      return this.$store.state.rid;
    },
    viewerReply() {
      // if () { // parentid가 rid와 일치하는 댓글만을 불러옴
      //   // 대댓글을 한정해서 가져오기
      // }
      return this.$store.state.commentList;
    },
  },
}
</script>

<style>

</style>