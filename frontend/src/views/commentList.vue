<template>
  <!-- 대댓글 보기 창 -->
  <v-row>
    
    <v-col
      cols="12"
      md="10"
      v-for="comment in viewerReply"
      :key="comment.rid"
    >
      <v-card flat v-if="commenRid == comment.parentid">
        <div class="d-flex justify-content-between">
          <div>
            <span class="mr-5"><strong>{{ comment.replynickname }}</strong></span>
            <span>{{ comment.replytext }}</span>

          </div>
          <div class="ml-auto">
            <small class="mr-5">{{ comment.created }}</small>
            {{ comment.parentid }}
            {{ comment.rid }}
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
      commentRid: this.$store.state.parentid,
    }
  },
  created() {
    this.viewerReply;
  },
  computed: {
    // parentid() {
    //   return this.$store.state.parentid;
    // },
    // depth() {
    //   return this.$store.state.depth;
    // },
    // rid() {
    //   return this.$store.state.rid;
    // },

    
    viewerReply() {
      
      // if () { // parentid가 rid와 일치하는 댓글만을 불러옴
      //   // 대댓글을 한정해서 가져오기
      // }
      return this.$store.state.commentList;
    },
  },
  methods: {
    // fetchReply () {
    
    // },
  }
}
</script>

<style>

</style>