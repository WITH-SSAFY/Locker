<template>
  <div class="hello">
    <v-conatiner>
    <v-row>
      <v-btn
        @click="handleClickLogin"
        :disabled="!isInit"
      >get authCode</v-btn>
      <v-btn
        @click="handleClickSignIn"
        v-if="!isSignIn"
        :disabled="!isInit"
      >sign in</v-btn>
      <v-btn
        @click="handleClickSignOut"
        v-if="isSignIn"
        :disabled="!isInit"
      >sign out</v-btn>
      <v-btn
        @click="handleClickDisconnect"
        :disabled="!isInit"
      >disconnect</v-btn>
      </v-row>
      <v-row>
        <p>isInit: {{isInit}}</p><br>
        <p>isSignIn: {{isSignIn}}</p>
      </v-row>
      <v-row>
      <v-btn
        @click="handleClickUpdateScope"
        :disabled="!isInit"
      >update scope</v-btn>
    </v-row>
  </v-conatiner>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: "HelloWorld",
  props: {
    msg: String
  },
  data() {
    return {
      isInit: false,
      isSignIn: false
    };
  },
  methods: {
    async handleClickUpdateScope() {
      const option = new window.gapi.auth2.SigninOptionsBuilder();
      option.setScope("email https://www.googleapis.com/auth/drive.file");
      const googleUser = this.$gAuth.GoogleAuth.currentUser.get();
      try {
        await googleUser.grant(option);
        console.log("success");
      } catch (error) {
        console.error(error);
      }
    },

    handleClickLogin() {
      this.$gAuth
        .getAuthCode()
        .then(authCode => {
          //on success
          console.log("authCode", authCode);
        })
        .catch(error => {
          //on fail do something
        });
    },

    async handleClickSignIn() {
      try {
        const googleUser = await this.$gAuth.signIn();
        console.log("googleUser", googleUser);
        console.log("getId", googleUser.getId());
        console.log("getBasicProfile", googleUser.getBasicProfile());
        console.log("getAuthResponse", googleUser.getAuthResponse());
        console.log(
          "getAuthResponse",
          this.$gAuth.GoogleAuth.currentUser.get().getAuthResponse()
        );
        this.isSignIn = this.$gAuth.isAuthorized;
      } catch (error) {
        //on fail do something
        console.error(error);
      }
    },

    async handleClickSignOut() {
      try {
        await this.$gAuth.signOut();
        this.isSignIn = this.$gAuth.isAuthorized;
        console.log("isSignIn", this.$gAuth.isAuthorized);
      } catch (error) {
        console.error(error);
      }
    },

    handleClickDisconnect() {
      window.location.href = `https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=${window.location.href}`;
    }
  },

  created() {
    let that = this;
    let checkGauthLoad = setInterval(function() {
      console.log("setInterval!!!")
      // console.log("that.$gAuth.isInit", that.$gAuth.isInit);
      that.isInit = that.$gAuth.isInit;
      that.isSignIn = that.$gAuth.isAuthorized;
      if (that.isInit) clearInterval(checkGauthLoad);
    }, 1000);
  }
};
</script>
