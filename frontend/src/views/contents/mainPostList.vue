<template>
  <v-container>
    <div class="row">
      <div class="col-md-9">
        <v-flex style="margin: 0;">
          <h1 class="mb-5" style="font-size: 3.3rem;"><strong>LOCKER</strong></h1>
          <!-- <h1 class="ml-2 mb-5" style="posigion: absolute; font-size: 4rem;"><strong>LOCKER</strong></h1> -->
          <div class="main-under-line"></div>
        </v-flex>

        <div class="row no-gutters">
          <div class="col-md-3 offfset-md-3" style="height: 25vh;"></div>
          <div class="box col-md-3"></div>
          <div class="box col-md-3"></div>
          <div class="box col-md-3"></div>
        </div>
        <div class="row no-gutters">
          <div class="box col-md-3"></div>
          <div class="col-md-3 offfset-md-3" style="height: 25vh;"></div>
          <div class="box col-md-3"></div>
          <div class="box col-md-3"></div>
        </div>
        
      </div>

      <div class="col-md-3">
        <p style="background-color: #333; color: #fff;">전체 글 목록 (임시)</p>
        <ul class="list-unstyled">
          <li
            v-for="post in postList"
            :key="post.pid"
            class="my-3"
          >
            <strong>{{ post.nickname }}</strong>
            <span>{{ post.title }}</span>
            <span>{{ post.content }}</span>
          </li>
        </ul>
      </div>

    </div>
  </v-container>
</template>

<script>
import { mapState } from "vuex"

export default {
  name: 'mainPostList',
  components: {

  },
  computed: {
    ...mapState(["postList"])
  },
  watch: {

  },
  data: function () {
    return {

    }
  },
  created() {
    this.$store.dispatch('getPostList')
    this.postList
  },
}
</script>

<style>
.box {
  box-sizing: border-box;
  border: 5px solid transparent;
  background-clip: padding-box;
  background-color: #333;
}
</style>