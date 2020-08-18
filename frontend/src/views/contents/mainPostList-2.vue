<template>
  <div style="background-color: #eceffc;">

    <div style="margin-left: 3.5rem; margin-top: 3.5rem;">
      <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>유저들이 좋아하는 포스트</p>
      <div class="under-line"></div>
    </div>

    <div class="w-75" style="margin-left: 3.5rem;">
      <v-row>
        <v-col
          v-for="post in postList"
          :key="post.pid"
          cols="12"
          lg="4"
          md="6"
        >   
          <v-card>
            <v-list-item>
              <v-list-item-avatar color="grey"></v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="headline">{{ post.title }}</v-list-item-title>
                <v-list-item-subtitle>by Kurt Wagner</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-img
              src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg"
              height="194"
            ></v-img>

            <v-card-text>
              {{ post.content }}
            </v-card-text>

            <v-card-actions>
              <v-btn
                text
                color="deep-purple accent-4"
              >
                Read
              </v-btn>
              <v-btn
                text
                color="deep-purple accent-4"
              >
                Bookmark
              </v-btn>
              <v-spacer></v-spacer>
              <v-btn icon>
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <v-btn icon>
                <v-icon>mdi-share-variant</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>


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
    // console.log(this.postList)
  },

}
</script>

<style>
  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7C4DFF;
  }
</style>