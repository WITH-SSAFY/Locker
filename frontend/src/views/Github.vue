<template>
  <div>hello</div>
</template>

<script>
import axios from "../lib/axios-common.js";

export default {
  beforeRouteEnter(to, from, next) {
    console.log("to from next", { to, from, next });
    axios
      .get("/api/social/github/token")
      .then(function(res) {
        console.log("res", res.data);
        //res.data.token
      })
      .catch(function(err) {
        console.log("err", err);
      });
  }
};
</script>