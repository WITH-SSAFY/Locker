<template>
  <v-app>
    <blog-header
      :isNotMain="isNotMain"
    ></blog-header>
    <router-view></router-view>
  </v-app>
</template>

<script>
import { mapState } from "vuex"
import BlogHeader from  "./views/Header.vue"

  export default {
    name: 'header',
    components: {
      BlogHeader,
    },
    created() {
      let url = this.$route.name;
      this.checkUrl(url);
    },
    computed: {
      ...mapState(["isLogin"])
    },
    props: {
      source: String,
    },
    data: function () {
      return {
        drawer: null,
        isNotMain: true,
        testUrl: this.$route.name
      }
    },
    watch: {
      $route(to){
        // alert("watch test : "+to.name)
        this.checkUrl(to.name)
      }
    },
    methods: {
      checkUrl(url) {
        // console.log("url : ", url)
        if(url === "home"){
          this.isNotMain = false;
        } else {
          this.isNotMain = true;
        }
      }
    }
  }
</script>
