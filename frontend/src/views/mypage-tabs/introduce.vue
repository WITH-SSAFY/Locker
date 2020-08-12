<template>
  <div>
    introduce test
    {{ intro }}
  </div>
</template>

<script>
import axios from "../../lib/axios-common.js";

export default {
  data() {
    return {
      intro: '',
    }
  },
  created () {
    this.getIntro()
  },
  computed: {
    
  },
  methods: {
    // access token을 로컬스토리지에서 가져와서 넘겨주기
    getIntro () {
      // let token = localStorage.getItem("github_token");
      // test
      let accessToken = '055bcf6a87cfd6b760b67a2150cfb5948b5b9385';
      console.log(accessToken)
      axios
        .get("/v1/github/hidden?accessToken=" + accessToken)
        .then((response) => {
          this.intro = response.data;
        })
        .catch((exp) => alert("소개글 불러오기 실패" + exp))
    }
  },
}
</script>

<style>

</style>