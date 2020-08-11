<template>
  <div>hello</div>
</template>

<script>
import axios from "../lib/axios-common.js";
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions(["signinWithSocial"])
  },
  beforeRouteEnter() {
    axios
      .get("/social/github/token")
      .then(function(res) {
        console.log("res", res.data);
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