<template>
  <v-container fill-height style="max-width:450px;">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-alert type="error" :value="isLoginError">
          아이디와 비밀번호를 확인해주세요.
        </v-alert>
        <v-alert type="success" :value="isLogin">
          로그인이 완료되었습니다. 
        </v-alert>
        <v-card>
          <v-toolbar flat>
            <v-toolbar-title><strong>Login</strong></v-toolbar-title>
          </v-toolbar>
          <div class="px-5 py-3">
            <div class="mb-7 ml-3" style="margin-right: 11rem">
              <v-row>
                <v-col cols="3">
                  <v-row>
                    <v-btn
                      elevation="0"
                      dark
                      small
                      class="mb-2 mx-2 px-0"
                      color="rgba(219, 68, 55)"
                      height="3rem"
                      @click="handleClickSignIn"
                      :disabled="!isInit"
                    >
                      <v-icon dark size="30">mdi-google-plus</v-icon>
                    </v-btn>
                  </v-row>
                  <v-row >
                    <v-btn
                      elevation="0"
                      dark
                      small
                      color="rgb(255, 204, 0)"
                      class="mb-2 mx-2 px-0"
                      @click="signinWithKakao"
                      height="3rem"
                    >
                      <v-icon dark size="30">mdi-chat</v-icon>
                    </v-btn>
                  </v-row>
                </v-col>
                <v-col cols="3">
                  <v-row>
                  <v-btn
                    elevation="0"
                    dark
                    small
                    color="rgba(66, 103, 178)"
                    class="mb-2 ml-2 px-0"
                    height="3rem"
                  >
                    <v-icon size="35">
                    facebook</v-icon>
                  </v-btn>
                </v-row>
                <v-row>
                  <v-btn
                    elevation="0"
                    dark
                    small
                    color="rgb(45, 180, 0)"
                    class="mb-2 ml-2 px-0"
                    height="3rem"
                  >
                    <v-icon
                      size="50"
                    >
                    mdi-alpha-n</v-icon>
                  </v-btn>
                </v-row>
                </v-col>
                <v-col cols="6" style="padding-left: 5px;">
                  <v-btn
                    elevation="0"
                    class="mb-2"
                    dark
                    small
                    color="black"
                    @click="signinWithSocial({provider: github})"
                    height="6.5rem"
                    width="6.5rem"
                  >
                    <v-icon dark size="90">mdi-github</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </div>


            <div class="form-inline form-group">
              <v-icon large>mdi-email</v-icon>
              <v-text-field
                v-model="id"
                label="이메일을 입력하세요"
                class="ml-2"
              ></v-text-field>
            </div>
            <div class="form-inline form-group">
              <v-icon large>mdi-lock</v-icon>
              <v-text-field
                v-model="password"
                type="password"
                label="패스워드를 입력하세요"
                class="ml-2"
              ></v-text-field>
            </div>
            <v-btn
                depressed
                dark
                block
                @click="login({id, password})"
                color="#7C4DFF"
                class="mb-2"
            >
              <strong>로그인</strong>
            </v-btn>             
          </div>
        </v-card>
        <div class="pa-2">
          <small>아직 LOCKER의 회원이 아니라면?</small>
          <v-btn
              depressed
              router :to="{name: 'register'}"
              color="white"
              class="float-right"
          >
            <strong style="color: #6200EA">회원가입</strong>
          </v-btn>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions} from "vuex"

export default {
  data(){
    return{
      id: null,
      password: null,
      github: "github",
      google: "google",
      isInit: false,
      isSignIn: false
    }
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  methods: {
    //state에 있는 action을 가져다 쓸 수 있게 해줌
    ...mapActions(["login", "signinWithKakao", "handleClickLogin", "signinWithSocial"]),

    async handleClickSignIn() {
      try {
        const googleUser = await this.$gAuth.signIn();
        let token = googleUser.getAuthResponse().access_token;
        console.log("google - access_token : ", googleUser.getAuthResponse().access_token )
        this.isSignIn = this.$gAuth.isAuthorized;
        this.signinWithSocial({access_token: token, provider: this.google})
        
      } catch (error) {
        console.error(error);
        alert("구글 로그인 도중 문제가 발생했습니다!", error)
      }
    },
  }, 
  created() {
    let that = this;
    let checkGauthLoad = setInterval(function() {
      console.log("setInterval!!!")
      that.isInit = that.$gAuth.isInit;
      that.isSignIn = that.$gAuth.isAuthorized;
      if (that.isInit) clearInterval(checkGauthLoad);
    }, 1000);
  }
}
</script>