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
                block
                @click="login({id, password})"
                color="#EDE7F6"
                class="mb-2"
            >
              <strong>로그인</strong>
            </v-btn>
            <v-btn
                depressed
                block
                color="dark"
                class="mb-2"
            >
              <v-icon dark>mdi-github</v-icon>
              <span class="ml-2">Github</span>
            </v-btn>
            <v-btn
                depressed
                block
                color="dark"
                class="mb-2"
            >
              <v-icon dark>mdi-google-plus</v-icon>
              <span class="ml-1">Google</span>
            </v-btn>
            <v-btn
                depressed
                block
                color="rgba(255, 204, 0, 0.5)"
                @click="socialLogin"
            >
              <v-icon dark>mdi-chat</v-icon>
              <span class="ml-1">KakaoTalk</span>
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
      password: null
    }
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  methods: {
    //state에 있는 action을 가져다 쓸 수 있게 해줌
    ...mapActions(["login","socialLogin"]),
  }
}
</script>