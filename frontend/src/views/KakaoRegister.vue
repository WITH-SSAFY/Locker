<template>
  <v-container fill-height style="max-width:450px;">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-card>
          <v-toolbar flat>
            <v-toolbar-title><strong>Signup</strong></v-toolbar-title>
          </v-toolbar>

          <div class="px-5 py-3">
            <v-form
              ref="form"
              lazy-validation
            >
              <!-- v-model="valid" -->
              <div class="form-inline form-group">
                <v-icon large>mdi-account-outline</v-icon>
                <v-text-field
                  v-model="usrName"
                  :counter="30"
                  :rules="usrNameRules"
                  label="이름"
                  class="ml-2"
                  required
                ></v-text-field>
              </div>

              <v-btn
                  depressed
                  block
                  id="request_btn"
                  @click="signupWithKakao({ kToken, usrName })"
                  color="#EDE7F6"
                  class="mb-2"
              >
                <strong>회원가입</strong>
              </v-btn>

              <!-- <v-btn @click="test">test!!</v-btn> -->
            </v-form>     
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import {mapActions} from "vuex"

export default {
  data: () => ({
    usrName : '',
    usrNameRules : [
      v => !!v || '이름을 입력해주세요',
      v => (v &&v.length>=2&& v.length <= 30) || '이름은 최소 2자 최대 30자 입니다',
    ],
  }),
  props: {
    kToken: {
      type: String,
      default: "none"
    }
  },
  // props: {
  //   access_token: ''
  // },
  methods: {
    ...mapActions(["signupWithKakao"]),
    test(){
      alert("넘어온 값 : "+this.access_token);
    }
  },
}
</script>