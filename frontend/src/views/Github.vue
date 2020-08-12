<template>
  <div>hello</div>
</template>

<script>
import axios from "../lib/axios-common.js";
import { mapActions } from "vuex";

export default {
  beforeRouteEnter(to, from, next) {
    console.log("to, from, next", { to, from, next });
    axios
      .get("/social/github/token")
      .then(function(res) {
        let github_token = res.data.data;
        console.log("github_token", github_token);
        localStorage.setItem("github_token", github_token);
        // axios
        //   .post("/v1/signin/github?accessToken=" + github_token)
        //   .then(res => {
        //     console.log(res.data);
        //     let token = res.data.data;
        //     localStorage.setItem("temp", token);
        //     next();
        //   })
        //   .catch(err => {
        //     console.log("err", err);
        //   });
        next();
      })
      .catch(function(err) {
        console.log("err", err);
      });
  },
  created() {
    let github_token = localStorage.getItem("github_token");
    console.log("created - github_token", github_token);
    this.signinWithSocial({ access_token: github_token, provider: "github" });
  },
  methods: {
    ...mapActions(["signinWithSocial"])
  }
};
</script>
