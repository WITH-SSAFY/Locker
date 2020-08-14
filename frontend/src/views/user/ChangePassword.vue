<template>
  <v-container fill-height style="max-width:450px;">
        <v-layout align-center row wrap>
            <v-flex xs12>
                <v-card>
                    <v-toolbar flat color="white">
                        <v-toolbar-title>비밀번호 찾기</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-3">
                      <v-text-field
                        autofocus
                        v-model="oldPw"
                        type="password"
                        label="현재 비밀번호를 입력하세요"
                      ></v-text-field>
                      <v-text-field
                        v-model="password"
                        type="password"
                        label="새로운 비밀번호를 입력하세요"
                      ></v-text-field>
                      <div v-if="password!==checkPw" style="color: red; font-size: 10px; text-align: right;">
                        비밀번호가 다릅니다. 
                      </div>
                      <v-text-field
                        v-model="checkPw"
                        type="password"
                        label="새로운 비밀번호 확인"
                      ></v-text-field>
                      
                      <v-btn 
                        v-if="password === checkPw"
                        depress
                        large
                        block
                        color="#7C4DFF"
                        dark
                        @click="changePassword({oldPw, password})"
                      >
                        비밀번호 변경
                      </v-btn>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data: () => ({
    oldPw: "",
    password: "",
    checkPw: "",
    isValid: false,
  }),
  methods: {
    ...mapActions(["changePassword"]),
  }
}
</script>