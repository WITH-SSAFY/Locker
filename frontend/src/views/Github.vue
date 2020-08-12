<template>
  <div>hello</div>
</template>

<script>
import axios from "../lib/axios-common.js";

export default {
  beforeRouteEnter(to, from, next) {
    console.log("to, from, next", { to, from, next });
    axios
      .get("/social/github/token")
      .then(function(res) {
        console.log("token", res.data.data);
      })
      .catch(function(err) {
        console.log("err", err);
      });
  }
};
</script>