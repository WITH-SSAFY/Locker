<template>
  <v-container>
    <v-col cols="12" sm="6">
      <v-text-field label="검색어를 입력하세요" v-model="q" @keyup.enter="search"></v-text-field>

      <v-card
        class="mx-auto"
        max-width="344"
        outlined
        v-for="post in searchList"
        :key="post.pid"
        @click="readPost(post.pid)"
      >
        <v-card-title>{{post.title}}</v-card-title>
      </v-card>
    </v-col>
  </v-container>
</template>

<script>
import axios from "../lib/axios-common.js"; //axios : 서버와 통신하기 위함

export default {
  data() {
    return {
      q: "", //검색어
      searchList: []
    };
  },
  methods: {
    search() {
      axios
        .get("v1/post/search?q=" + this.q)
        .then(response => {
          this.searchList = response.data;
        })
        .catch(exp => alert("검색 실패" + exp));
    },
    readPost(pid) {
      this.$store.dispatch("showMyDetail", pid);
    }
  }
};
</script>

<style></style>
