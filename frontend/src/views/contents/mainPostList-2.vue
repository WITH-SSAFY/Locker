<template>
  <div>
    <ul>
      <li
        v-for="post in postList"
        :key="post.pid"
      >
        {{ post.title }}
        {{ post.content }}
      </li>    
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex"

export default {
  name: 'mainPostList2',
  data: function () {
    return {

    }
  },
  computed: {
    ...mapState(["postList"])
  },
  created () {
    this.$store.dispatch('getPostList')
    this.postList
  },

}
</script>

<style>

</style>