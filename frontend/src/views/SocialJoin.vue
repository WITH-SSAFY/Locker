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
                <!-- <v-icon large>mdi-account-outline</v-icon>
                <v-text-field
                  v-model="usrName"
                  :counter="30"
                  :rules="usrNameRules"
                  label="이름"
                  class="ml-2"
                  required
                ></v-text-field> -->
                <!-- {{ access_token }} -->
                Locker의 서비스를 이용하기 위해서는 회원가입이 필요합니다!<br>
                가입하시겠어요??
              </div>

              <v-btn
                  depressed
                  block
                  id="request_btn"
                  @click="signupWithKakao({ access_token })"
                  color="#EDE7F6"
                  class="mb-2"
              >
                <strong>회원가입</strong>
              </v-btn>
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
  props: {
    // kToken: {
    //   type: String,
    //   default: "none"
    // }
    access_token: {
      type: String,
      default: "none"
    }
  },
  methods: {
    ...mapActions(["signupWithKakao"]),
    test(){
      alert("넘어온 값 : "+this.access_token);
    }
  },
}
</script>