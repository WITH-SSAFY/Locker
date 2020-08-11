<template>
  <div>
    <h1>GITHUBLOGIN</h1>
  </div>
</template>

<script>
/* eslint-disable no-console */
import axios from "axios";

export default {
  methods: {
    redirect: function(url) {
      window.location.href = url;
    }
  },
  created() {
    // const redirect = this.redirect;
    // redirect("/api/social/login/github");
    // alert(
    //   "query.code : " +
    //     this.$route.query.code +
    //     "/ state :" +
    //     this.$route.query.state
    // );
    console.log("this.$route : ", this.$route);
    axios
      .get(
        // "http://localhost:8080/github/login?code=" +
        "http://i3a606.p.ssafy.io:8000/api/social/login/github?code=" +
          this.$route.query.code +
          "&state=" +
          this.$route.query.state
      )
      .then(function(res) {
        if (!res.data) {
          alert("something went wrong. can't get access token.");
          // redirect("/");
        }
        // redirect("/user?token=" + res.data + "&service=github");
        console.log("res.data", res.data);
      })
      .catch(function(err) {
        alert("something went wrong. request failed.");
        console.log(err);
        // redirect("/");
      });
  },
  // beforeRouteEnter(to, from, next) {
  beforeRouteLeave(to, from, next) {
    console.log("to, from, next", { to, from, next });

    if (to.query) {
      if (to.query.code && to.query.state) {
        next();
      } else {
        alert("there's no query data.1");
        next("/");
      }
    } else {
      alert("there's no query data.2");
      next("/");
    }
  }
};
</script>