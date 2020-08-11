<template>
  <v-container fill-height style="max-width:450px;" name="test">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-alert type="error" :value="isLoginError">아이디와 비밀번호를 확인해주세요.</v-alert>
        <v-alert type="success" :value="isLogin">로그인이 완료되었습니다.</v-alert>
        <v-card>
          <v-toolbar flat>
            <v-toolbar-title>
              <strong>Login</strong>
            </v-toolbar-title>
          </v-toolbar>
          <div class="px-5 py-3">
            <div class="form-inline form-group">
              <v-icon large>mdi-email</v-icon>
              <v-text-field
                v-model="id"
                label="이메일을 입력하세요"
                class="ml-2"
                @keyup.enter="login({ id, password })"
              ></v-text-field>
            </div>
            <div class="form-inline form-group">
              <v-icon large>mdi-lock</v-icon>
              <v-text-field
                v-model="password"
                type="password"
                label="패스워드를 입력하세요"
                class="ml-2"
                @keyup.enter="login({ id, password })"
              ></v-text-field>
            </div>
            <v-btn
              depressed
              dark
              block
              @click="login({ id, password })"
              color="#7C4DFF"
              class="mb-2"
            >
              <strong>로그인</strong>
            </v-btn>
            <v-btn depressed dark block color="rgba(0,0,0)" class="mb-2" @click="loginWithGithub">
              <v-icon dark size="30" class="mr-2">mdi-github</v-icon>
              <strong>github</strong>
            </v-btn>
            <v-btn
              depressed
              dark
              block
              @click="handleClickSignIn"
              color="rgba(219, 68, 55)"
              class="mb-2"
            >
              <v-icon dark size="30" class="mr-2">mdi-google-plus</v-icon>
              <strong>google</strong>
            </v-btn>
            <v-btn
              depressed
              dark
              block
              @click="signinWithKakao"
              color="rgb(255, 204, 0)"
              class="mb-2"
            >
              <v-icon dark size="30" class="mr-2">mdi-chat</v-icon>
              <strong>kakao</strong>
            </v-btn>
            <v-btn depressed dark block color="rgb(45, 180, 0)" class="mb-2">
              <v-icon size="50">mdi-alpha-n</v-icon>
              <strong>naver</strong>
            </v-btn>
          </div>
        </v-card>
        <div class="pa-2">
          <small>아직 LOCKER의 회원이 아니라면?</small>
          <v-btn depressed router :to="{ name: 'register' }" color="white" class="float-right">
            <strong style="color: #6200EA">회원가입</strong>
          </v-btn>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import axios from "axios";

export default {
  data() {
    return {
      id: null,
      password: null,
      github: "github",
      google: "google",
      isInit: false,
      isSignIn: false
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  methods: {
    //state에 있는 action을 가져다 쓸 수 있게 해줌
    ...mapActions([
      "login",
      "signinWithKakao",
      "handleClickLogin",
      "signinWithSocial"
    ]),

    async handleClickSignIn() {
      try {
        const googleUser = await this.$gAuth.signIn();
        let token = googleUser.getAuthResponse().access_token;
        console.log(
          "google - access_token : ",
          googleUser.getAuthResponse().access_token
        );
        this.isSignIn = this.$gAuth.isAuthorized;
        this.signinWithSocial({ access_token: token, provider: this.google });
      } catch (error) {
        console.error(error);
        alert("구글 로그인 도중 문제가 발생했습니다!", error);
      }
    },

    loginWithGithub() {
      // window.location.href = "/oauth2/authorization/github";
      // test.location.href = "/oauth2/authorization/github";
      window.open("http://i3a606.p.ssafy.io:8000/oauth2/authorization/github");
      window.location.href =
        "http://i3a606.p.ssafy.io:8000/oauth2/authorization/github";
      // window.open("http://localhost:8080/oauth2/authorization/github");
    }
  },
  created() {
    let that = this;
    let checkGauthLoad = setInterval(function() {
      console.log("setInterval!!!");
      that.isInit = that.$gAuth.isInit;
      that.isSignIn = that.$gAuth.isAuthorized;
      if (that.isInit) clearInterval(checkGauthLoad);
    }, 1000);

    // var naverLogin = new naver.LoginWithNaverId({
    //     clientId: "{YOUR_CLIENT_ID}",
    //     callbackUrl: "{YOUR_REDIRECT_URL}",
    //     isPopup: true, /* 팝업을 통한 연동처리 여부 */
    //     loginButton: {color: "green", type: 3, height: 60} /* 로그인 버튼의 타입을 지정 */
    // });

    /* 설정정보를 초기화하고 연동을 준비 */
    // naverLogin.init();
  }
};
</script>