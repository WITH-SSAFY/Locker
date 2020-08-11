<template>
  <v-container fill-height style="max-width:450px;">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-card>
          <v-toolbar flat>
            <v-toolbar-title>
              <strong>Signup</strong>
            </v-toolbar-title>
          </v-toolbar>

          <div class="px-5 py-3">
            <v-form ref="form" v-model="valid" lazy-validation>
              <div class="form-inline form-group">
                <v-icon large>mdi-email</v-icon>
                <v-text-field
                  v-model="email"
                  :rules="emailRules"
                  label="이메일"
                  :disabled="useThisEmail"
                  class="ml-2"
                ></v-text-field>&nbsp;&nbsp;&nbsp;&nbsp;
                <v-btn
                  v-show="!useThisEmail"
                  depressed
                  id="email_btn"
                  @click="checkEmail"
                  color="white"
                >중복확인</v-btn>
              </div>

              <div class="form-inline form-group">
                <v-icon large>mdi-email</v-icon>
                <v-text-field
                  v-model="inputAuth"
                  label="인증 번호 입력"
                  :disabled="!getAuthFlag||sendAuthFlag"
                  class="ml-2"
                ></v-text-field>
                <v-btn
                  v-show="!getAuthFlag"
                  depressed
                  id="getAuth_btn"
                  @click="getAuth"
                  color="white"
                >번호받기</v-btn>
                <v-btn
                  v-show="getAuthFlag&&!sendAuthFlag"
                  depressed
                  id="sendAuth_btn"
                  @click="sendAuth"
                  color="white"
                >인증</v-btn>
              </div>

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

              <div class="form-inline form-group">
                <v-icon large>mdi-account-outline</v-icon>
                <v-text-field
                  v-model="nickName"
                  :counter="30"
                  :rules="nickNameRules"
                  label="닉네임"
                  class="ml-2"
                  required
                ></v-text-field>
              </div>

              <div class="form-inline form-group">
                <v-icon large>mdi-lock</v-icon>
                <v-text-field
                  v-model="password"
                  :counter="50"
                  :rules="passwordRules"
                  label="비밀번호"
                  type="password"
                  class="ml-2"
                  required
                ></v-text-field>
              </div>

              <div class="form-inline form-group">
                <v-icon large>mdi-lock</v-icon>
                <v-text-field
                  v-model="passwordConfirm"
                  :counter="50"
                  :rules="passwordConfirmRules"
                  label="비밀번호 확인"
                  type="password"
                  class="ml-2"
                  required
                ></v-text-field>
              </div>

              <v-btn
                depressed
                block
                id="request_btn"
                @click="requestJoin"
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
import axios from "../lib/axios-common.js"; //axios : 서버와 통신하기 위함

export default {
  data: () => ({
    valid: true,

    email: "",
    emailRules: [
      v => !!v || "이메일을 입력해주세요",
      v =>
        /^[A-Za-z0-9_.-]+@[A-Za-z0-9-]+\.[A-Za-z0-9]+/.test(v) ||
        "이메일 형식에 맞게 입력해주세요"
    ],
    usrName: "",
    usrNameRules: [
      v => !!v || "이름을 입력해주세요",
      v =>
        (v && v.length >= 2 && v.length <= 30) ||
        "이름은 최소 2자 최대 30자 입니다"
    ],
    nickName: "",
    nickNameRules: [
      v => !!v || "닉네임을 입력해주세요",
      v =>
        (v && v.length >= 1 && v.length <= 30) ||
        "닉네임은 최소 1자 최대 30자 입니다"
    ],

    password: "",
    passwordRules: [
      v => !!v || "비밀번호를 입력해주세요",
      v =>
        (v && v.length >= 4 && v.length <= 50) ||
        "비밀번호는 최소 4자 최대 50자 입니다"
    ],
    passwordConfirm: "",

    isEmailOk: false, //중복 확인된 이메일 인가?

    useThisEmail: false, //해당 이메일을 사용할 것인가?
    inputAuth: "", //사용자가 입력한 인증번호
    auth: "", //서버로 부터 받아온 인증번호
    getAuthFlag: false, //인증 번호를 받았는가?
    sendAuthFlag: false //인증 보내서 인증을 완료 하였는가?
  }),

  computed: {
    passwordConfirmRules() {
      if (this.passwordConfirm != this.passwordConfirm) {
        return "비밀번호가 일치하지 않습니다";
      }

      return [
        () =>
          this.password === this.passwordConfirm ||
          "비밀번호가 일치하지 않습니다",
        v => !!v || "비밀번호 확인을 입력해주세요",
        v =>
          (v && v.length >= 4 && v.length <= 50) ||
          "비밀번호 확인은 최소 4자 최대 50자 입니다"
      ];
    }
  },

  methods: {
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    getAuth() {
      if (this.useThisEmail) {
        axios
          .get("/v1/verify?email=" + this.email)
          .then(response => {
            this.auth = response.data.data;
            this.getAuthFlag = true;
            alert(
              this.email +
                "로 인증 번호를 발송 하였습니다. 확인 후 인증 번호를 입력해주세요"
            );
          })
          .catch(exp => alert("인증 번호 받기 실패" + exp));
      } else {
        alert("먼저 이메일 중복 확인을 해주세요");
      }
    },
    sendAuth() {
      if (this.useThisEmail && this.getAuthFlag) {
        if (this.auth == this.inputAuth) {
          alert("인증이 완료 되었습니다.");
          this.sendAuthFlag = true;
        } else {
          alert("인증 번호가 틀립니다.");
        }
      } else {
        alert("유효하지 않은 접근입니다.");
      }
    },

    checkEmail() {
      //이메일 중복확인
      if (
        this.email != "" &&
        /^[A-Za-z0-9_.-]+@[A-Za-z0-9-]+\.[A-Za-z0-9]+/.test(this.email)
      ) {
        //console.log("email : "+this.email);
        axios
          .post("/v1/verify/duplicate?email=" + this.email)
          .then(response => {
            let data = response.data;
            console.dir(data);
            if (data.success) {
              //중복된 닉네임이 없다
              this.useThisEmail = confirm("해당 이메일을 사용하시겠습니까?");
            }
          })
          .catch(exp => {
            alert("중복된 이메일 입니다.");
            exp;
          });
      }
    },
    requestJoin() {
      //회원 가입 요청
      // isPasswordValid: false,//비밀번호와 확인이 같은지 검사
      if (this.$refs.form.validate()) {
        if (!this.useThisEmail) {
          alert("이메일 중복 확인 해주세요");
        } else if (!this.sendAuthFlag) {
          alert("인증 번호를 입력 해주세요");
        } else {
          axios
            .post(
              "/v1/signup?email=" +
                this.email +
                "&password=" +
                this.password +
                "&name=" +
                this.usrName +
                "&nickname=" +
                this.nickName
            )
            .then(response => {
              let data = response.data;
              if (data != null) {
                alert("가입완료!!!");
                this.$router.push("/login");
              }
            })
            .catch(exp => alert("회원 가입 처리에 실패했습니다" + exp));
        }
      }
    }
  }
};
</script>

<style>
</style>