<template>
  <div>hello</div>
</template>

<script>
import axios from "../lib/axios-common.js";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      token: null
    };
  },
  methods: {
    ...mapActions(["signinWithSocial"])
  },
  beforeRouteUpdate(to, from, next) {
    console.log("to, from, next", { to, from, next });
    axios
      .get("/social/github/token")
      .then(function(res) {
        console.log("token", res.data.data);
        this.signinWithSocial({
          provider: "github",
          access_token: res.data.data
        });
      })
      .catch(function(err) {
        console.log("err", err);
      });
  }
};
</script>