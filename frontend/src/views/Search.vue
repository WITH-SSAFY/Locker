<template>
  <v-container>
    <v-row alignment="center" justify="center">
      <v-col cols="12" sm="8">
        <v-text-field
          label="검색어를 입력하세요"
          v-model="q"
          @keyup.enter="search"
        ></v-text-field>
        <v-card
          class="mx-auto"
          outlined
          v-for="post in searchList"
          :key="post.pid"
          @click="readPost(post.pid)"
          max-height="250px"
        >
          <v-card-text>
            <p class="postNickName">{{ post.nickname }}</p>
            <p class="display-1 text--primary">
              {{ post.title }}
            </p>
            <div class="postContent text--primary">
              {{ post.content }}
            </div>
            <br />
            <p class="postUpdated">{{ post.updated }}</p>
          </v-card-text>

          <!-- <v-card-text class="postNickname"> {{ post.nickname }} </v-card-text>
          <v-card-title>{{ post.title }}</v-card-title>
          <v-card-text class="postContent"> {{ post.content }} </v-card-text>
          <br />
          <v-card-text class="postUptated"> {{ post.updated }} </v-card-text> -->
        </v-card>
        <div
          v-infinite-scroll="loadMore"
          infinite-scroll-disabled="busy"
          infinite-scroll-distance="10"
        ></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "../lib/axios-common.js"; //axios : 서버와 통신하기 위함
import infiniteScroll from "vue-infinite-scroll";

export default {
  data() {
    return {
      q: "", //검색어
      searchList: [],
      page: 1,
    };
  },
  directives: { infiniteScroll },
  methods: {
    search() {
      this.page = 1;
      console.log("search", this.q);

      if (this.q != "") {
        axios
          .get("/v1/post/search", {
            params: {
              q: this.q,
              page: 1,
            },
          })
          .then((response) => {
            this.searchList = response.data;
            this.page += 1;
          })
          .catch((exp) => alert("검색 실패" + exp));
      }
    },
    readPost(pid) {
      this.$store.dispatch("showMyDetail", pid);
    },
    loadMore() {
      this.busy = true;

      if (this.q != "") {
        axios
          .get("/v1/post/search", {
            params: {
              q: this.q,
              page: this.page,
            },
          })
          .then((response) => {
            setTimeout(() => {
              if (response.data.length > 0) {
                this.searchList = this.searchList.concat(response.data);
                this.page += 1;
              }
            }, 700);
          })
          .catch((exp) => alert("로딩 실패" + exp));
        this.busy = false;
      }
    },
  },
};
</script>

<style scoped>
.postContent {
  text-overflow: ellipsis;
}
.mx-auto {
  margin-bottom: 20px;
}
</style>
