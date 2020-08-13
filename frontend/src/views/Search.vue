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
      <infinite-loading
        slot="append"
        @infinite="infiniteHandler"
        force-use-infinite-wrapper=".el-table__body-wrapper"
      ></infinite-loading>
    </v-col>
  </v-container>
</template>

<script src="https://unpkg.com/vue-infinite-loading@2.4.4/dist/vue-infinite-loading.js"></script>
<script>
import axios from "../lib/axios-common.js"; //axios : 서버와 통신하기 위함

export default {
  data() {
    return {
      q: "", //검색어
      searchList: [],
      page: 1
    };
  },
  methods: {
    search() {
      axios
        .get("v1/post/search", {
          params: {
            q: this.q,
            page: 1
          }
        })
        .then(response => {
          this.searchList = response.data;
        })
        .catch(exp => alert("검색 실패" + exp));
    },
    readPost(pid) {
      this.$store.dispatch("showMyDetail", pid);
    },
    infiniteHandler($state) {
      axios
        .get("v1/post/search", {
          params: {
            q: this.q,
            page: this.page
          }
        })
        .then(({ data }) => {
          if (data.hits.length) {
            this.page += 1;
            this.searchList = this.searchList.concat(data.hits);
            $state.loaded();
          } else {
            $state.complete();
          }
        })
        .catch(exp => alert("로딩 실패" + exp));
    }
  }
};
</script>

<style></style>
