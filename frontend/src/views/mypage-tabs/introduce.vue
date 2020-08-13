<template>
  <div class="d-flex justify-content-center" style="margin: 5rem 0;">
    <viewer v-if="axiosFlag" :initialValue="intro" height="100%" />
  </div>
</template>

<script>
import axios from "../../lib/axios-common.js";
import { Viewer } from '@toast-ui/vue-editor';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

export default {
  name: 'introcduce',
  data() {
    return {
      intro: '',
      axiosFlag: false,
    }
  },
  created () {
    this.getIntro();
  
  },
  computed: {

  },
  components: {
    Viewer,
  },
  methods: {
    // access token을 로컬스토리지에서 가져와서 넘겨주기
    getIntro () {
      // let token = localStorage.getItem("github_token");
      // test
      let accessToken = '055bcf6a87cfd6b760b67a2150cfb5948b5b9385';
      axios.get("/v1/github/hidden?accessToken=" + accessToken)
        .then((response) => {
          this.intro = response.data.data
          this.axiosFlag = true
        })
        .catch((exp) => alert("소개글 불러오기 실패" + exp))
    }
  },
}
</script>

<style>

</style>