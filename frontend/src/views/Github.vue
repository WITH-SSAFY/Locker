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
        console.log("token", res.data.data);
        axios
          .post("/v1/signin/github?accessToken=" + res.data.data)
          .then(res => {
            console.log("axios.post 들어감");
            console.log(res.data);
            let token = res.data.data;
            localStorage.setItem("access_token", token);
            next();
          })
          .catch(err => {
            console.log("err", err);
          });
      })
      .catch(function(err) {
        console.log("err", err);
      });
  },
  create() {
    let token = localStorage.getItem("access_token");
    console.log("token", token);
    this.signinWithSocial({ provider: "github", access_token: token });
  },
  methods: {
    ...mapActions(["signinWithSocial"])
  }
};
</script>