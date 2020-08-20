<template>
  <v-app>
    <blog-header :isNotMain="isNotMain"></blog-header>
    <router-view></router-view>
  </v-app>
</template>

<script>
import { mapState } from "vuex";
import BlogHeader from "./views/Header.vue";

export default {
  name: "header",
  components: {
    BlogHeader
  },
  created() {
    // let url = this.$route.name;
    // this.route
    // alert("created url : " + url);
    // this.checkUrl(url);
  },
  computed: {
    ...mapState(["isLogin"])
  },
  props: {
    source: String
  },
  data: function() {
    return {
      drawer: null,
      isNotMain: true,
      testUrl: this.$route.name
    };
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
    }
  },
  methods: {
    checkUrl(url) {
      if (url === "home") {
        this.isNotMain = false;
      } else {
        this.isNotMain = true;
      }
    }
  }
};
</script>
<style>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);
body {
  font: 24px/30px "Noto Sans CJK KR", Noto Sans KR, sans-serif;
}
.thin {
  font-weight: 100;
}
.light {
  font-weight: 300;
}
.regular {
  font-weight: 400;
}
.medium {
  font-weight: 500;
}
.bold {
  font-weight: 700;
}
.bolder {
  font-weight: 900;
}
</style>