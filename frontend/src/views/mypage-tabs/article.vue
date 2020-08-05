<template>
  <div class="article">
    article test
    <!-- 포스트 리스트 -->
    <v-row>
      <v-col
        cols="12"
        md="10"
        v-for="(myPost, index) in myPostList"
        :key="myPost.pid"
        style="padding: 2% 8%;"
      >
        <v-card>
          <v-card-title class="title" @click="showMyDetail(myPost.pid)">{{ myPost.title }}</v-card-title>
          
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="like(index)">
              <v-icon :id="index">mdi-heart</v-icon>
            </v-btn>
            <v-btn icon><v-icon>mdi-bookmark</v-icon></v-btn>
            <v-btn icon><v-icon>mdi-share-variant</v-icon></v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapState } from "vuex"

export default {
  components: {

  },
  computed: {
    ...mapState(["userInfo","myPostList"]),
  },
  methods: {
    showMyDetail (pid) {
      this.$store.dispatch('showMyDetail', pid);
      //this.$router.push({name: "readPost"});
    },
  },
  props: {

  },
}
</script>

<style scoped>
.title {
  cursor: pointer;
}
</style>