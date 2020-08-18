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


    <!-- <v-card
      max-width="344"
      class="mx-auto"
      v-for="post in postList"
      :key="post.pid"
    >

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
    </v-card> -->

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