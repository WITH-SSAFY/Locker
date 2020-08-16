<template>
  <v-content style="background-color: #EDE7F6;">
    <v-container fill-height>
      <v-layout align-center row wrap>
        <v-card style="min-width: 50vw; padding: 3rem;">
          <p class="bolder my-5" style="font-size: 1.5rem;">로그인</p>
          <div class="under-line"></div>

          <div class="row">
            <div class="col-md-6">
              <p class="bold py-3"><span style="color: #7C4DFF;">LOCKER </span>계정으로 로그인하기</p>
              <div class="form-inline form-group">
                <v-icon>mdi-email</v-icon>
                <v-text-field
                  v-model="id"
                  label="이메일을 입력하세요"
                  class="ml-2"
                  @keyup.enter="login({ id, password })"
                >
                </v-text-field>
              </div>
              <div class="form-inline form-group">
                <v-icon>mdi-lock</v-icon>
                <v-text-field
                  v-model="password"
                  type="password"
                  label="패스워드를 입력하세요"
                  class="ml-2"
                  @keyup.enter="login({ id, password })"
                >
                </v-text-field>
              </div>

              <v-btn
                depressed
                block
                @click="login({ id, password })"
                style="margin-bottom: 2.5rem;"
              >
                <span class="bolder" style="font-size: 1rem;">로그인</span>
              </v-btn>
            </div>
              
            <div class="col-md-6">
              <p class="bold py-3"><span style="color: #7C4DFF;">소셜로그인</span>으로 간편하게 시작하기</p>
              <div>
                <!-- 깃헙 로그인 -->
                <button
                  @click="loginWithGithub"
                  style="color: #fff; background-color: rgba(66, 66, 66); width: 15rem;"
                  class="btn m-1 d-flex justify-content-between"
                >
                  <v-icon dark size="25" class="mr-2">mdi-github</v-icon>
                  <strong>Continue with github</strong>
                </button>
                <p class="regular mb-5" style="font-size: 0.8rem;">LOCKER를 가장 효과적으로 이용하려면 !</p>

                <!-- 카카오 로그인 -->
                <button
                  @click="signinWithKakao"
                  style="color: #fff; background-color: rgb(255, 204, 0); width: 15rem;"
                  class="btn m-1 d-flex justify-content-between"
                >
                  <v-icon dark size="25" class="mr-2">mdi-chat</v-icon>
                  <strong>Continue with kakao</strong>
                </button>

                <!-- 구글 로그인 -->
                <button
                  @click="handleClickSignIn"
                  style="color: #fff; background-color: rgba(219, 68, 55); width: 15rem;"
                  class="btn m-1 d-flex justify-content-between"
                >
                  <v-icon dark size="25" class="mr-2">mdi-google-plus</v-icon>
                  <strong>Continue with google</strong>
                </button>

                <!-- 네이버 로그인 -->
                <button
                  style="color: #fff; background-color: rgb(45, 180, 0); width: 15rem;"
                  class="btn m-1 d-flex justify-content-between"
                >
                  <v-icon size="25">mdi-alpha-n</v-icon>
                  <strong>Continue with naver</strong>
                </button>
              </div>
            </div>
          </div>

        </v-card>
        
      </v-layout>

      <div style="margin-top: 7rem;">
        <p class="bold" style="color: #424242">아직 LOCKER의 회원이 아니라면?</p>
        <v-btn router :to="{ name: 'register' }" color="#424242" class="mb-5">
          <v-icon size="25" class="mr-2">mdi-arrow-right-thick</v-icon>
          <strong style="color: #fff;">회원가입 하기</strong>
        </v-btn>

        <p class="bold mt-5" style="color: #424242">비밀번호를 잊어버렸다면?</p>
        <v-btn router :to="{ name: 'findPassword' }" color="#424242">
          <v-icon size="25" class="mr-2">mdi-arrow-right-thick</v-icon>
          <strong style="color: #fff;">비밀번호 찾기</strong>
        </v-btn>
      </div>
    </v-container>
  </v-content>
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
      // isSignIn: false
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
      window.location.href =
        "http://i3a606.p.ssafy.io:8000/oauth2/authorization/github";
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
<style scoped>
.under-line {
  height: 0.3rem;
  width: 3.5rem;
  margin-bottom: 3rem;
  background-color: #7C4DFF;
}

.body {
  background-color: #EDE7F6;
}
</style>