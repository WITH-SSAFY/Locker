<template>
    <div>
      <v-container class="mt-md-6">
        <v-row>
          <v-col
            cols="12"
            md="10"
            class="p-0"
          >
            <div class="row align-items-center ml-16 mr-5 mb-5">
              <div class="col-md-3 d-none d-sm-block align-self-start">
                <div class="d-flex justify-content-center" style="background-color: #f2f3f4;">
                  <v-icon v-if="userInfo.picture==null"
                    size="180" style="z-index: 1;">mdi-account-circle-outline</v-icon>
                  <v-avatar v-else  size="180" style="z-index: 1;">
                    <img :src="userInfo.picture">
                  </v-avatar>
                </div>
                <div>
                  <v-btn
                    color="#7C4DFF"
                    elevation="0"
                    dark
                    block
                    class="mt-2"
                  >
                    <h6>이미지 바꾸기</h6>
                  </v-btn>
                  <v-btn
                    block
                    color=""
                    elevation="0"
                    class="mt-1"
                  >
                    이미지 삭제 
                  </v-btn>
                </div>
              </div>

              <!-- 텍스트 섹션 -->
              <div class="col-md-8 d-flex-wrap align-self-start ml-8">
                <div class="d-flex align-items-center justify-content-between p-0">
                  <p v-if="curName" class="lead d-inline my-3" style="font-size: 3rem; font-weight: bold;">{{ userInfo.nickname }}</p>
                  <v-text-field
                    v-if="updateName"
                    v-model="userInfo.nickname"
                    outlined
                    required
                    height="5.3rem"
                    style="font-size: 2.5rem; font-weight: bold;"
                  ></v-text-field>
                </div>
                <p v-if="curIntro" class="mt-6 mb-10">{{ userInfo.introduction }}</p>
                <v-text-field
                  v-if="updateIntro"
                  v-model="userInfo.introduction"
                  outlined
                  required
                ></v-text-field>
                <div class="update_link">
                  <a
                    v-if="showUpdateBtn"
                    style="text-decoration: none;"
                    @click="showForm"
                    >
                    수정</a>
                  <v-btn
                    elevation="0"
                    color="#7C4DFF"
                    dark
                    style="float: right;"
                    v-if="showSaveBtn"
                    @click="closeForm"
                    >
                    확인</v-btn>
                </div>
                <div  class="mt-2">
                  <v-icon size="60" style="color: #EDE7F6;">mdi-arm-flex</v-icon>
                  <p class="d-inline"><small>1F_병아리</small></p>
                </div>
              </div>
            </div>
            <div class="ml-16 mr-3 mt-16">
              <v-row class="mx-1">
                <v-col cols="12" md="3">
                  <div style="font-size: 1.5rem; font-weight:bold;">내 라커 이름</div>
                </v-col>
                <v-col md="9" class="mt-2">
                  <div style="font-size: 1.3">locker_test</div>
                </v-col>
              </v-row>
            </div>
            <hr>
            <div class="ml-16 mr-3 mt-5">
              <v-row class="mx-1">
                <v-col cols="12" md="3">
                  <div style="font-size: 1.5rem; font-weight:bold;">소셜 정보</div>
                </v-col>
                <v-col md="9" class="mt-2">
                  <div class="d-inline">
                    <a><v-icon size="35" style="color: black; padding-right: 1rem;">mdi-github</v-icon></a>
                    <a><v-icon size="35" style="color: black;">mdi-google-plus</v-icon></a>
                  </div>
                </v-col>
              </v-row>
            </div>
            <hr>
            <div class="ml-16 mr-3 mt-5">
              <v-row class="mx-1">
                <v-col cols="12" md="3">
                  <div style="font-size: 1.5rem; font-weight:bold;">이메일 주소</div>
                </v-col>
                <v-col md="9" class="mt-2">
                  <div style="font-size: 1.3">{{userInfo.email}}</div>
                </v-col>
              </v-row>
            </div>
            <hr>
            <div class="ml-16 mr-3 mt-5">
              <v-row class="mx-1">
                <v-col cols="12" md="3">
                  <div style="font-size: 1.5rem; font-weight:bold;">회원 탈퇴</div>
                </v-col>
                <v-col md="9">
                  <v-btn
                    elevation="0"
                    color="#C62828"
                    dark
                  >
                  회원 탈퇴</v-btn>
                </v-col>
              </v-row>
              <v-row class="ml-4 mb-16">
                <p>탈퇴 시 작성하신 포스트 및 댓글이 모두 삭제되며 복구되지 않습니다.</p>
              </v-row>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </div>
</template>
<script>
import { mapState, mapActions } from 'vuex';

export default {
   data: ()=>{
     return{
      curName: true,
      curIntro: true,
      updateName: false,
      updateIntro: false,
      showUpdateBtn: true,
      showSaveBtn: false
     }
   },
   computed:{
     ...mapState(["userInfo"])
   },
   methods: {
     ...mapActions(["updateUserInfo"]),

     showForm(){
       this.curName = false;
       this.curIntro = false;
       this.updateName = true;
       this.updateIntro = true;
       this.showUpdateBtn = false;
       this.showSaveBtn = true;
     }, 
     closeForm(){
       console.log("확인 누르면 userInfo", this.userInfo)
       this.updateUserInfo(this.userInfo)
       this.curName = true;
       this.curIntro = true;
       this.updateName = false;
       this.updateIntro = false;
       this.showUpdateBtn = true;
       this.showSaveBtn = false;
     },
     
   }, 
   created() {
     console.log("가지고 온 유저 정보 : ", this.userInfo)
   }
}
</script>