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
    console.log("this.$route", this.$route);
    axios
      .get(
        "http://i3a606.p.ssafy.io/login/github?code=" +
          this.$route.query.code +
          "&state=" +
          this.$route.query.state
      )
      .then(function(res) {
        console.log("res!", res);
        if (!res.data) {
          alert("something went wrong. can't get access token.");
          // redirect("/");
        }
        redirect("/user?token=" + res.data + "&service=github");
      })
      .catch(function(err) {
        alert("something went wrong. request failed.");
        console.log(err);
        // redirect("/");
      });
  },
  beforeRouteEnter(to, from, next) {
    console.log("to from next", { to, from, next });
    if (to.query) {
      if (to.query.code && to.query.state) {
        next();
      } else {
        alert("there's no query data.");
        // next("/");
      }
    } else {
      alert("there's no query data.");
      // next("/");
    }
  }
};
</script>