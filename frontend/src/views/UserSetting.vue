<template>
  <div>
    <v-container class="mt-md-6">
      <v-row>
        <v-col cols="12" md="9" class="p-0">
          <div class="row align-items-center ml-16 mr-5 mb-5">
            <div class="col-md-3 d-none d-sm-block align-self-start">
              <div class="d-flex justify-content-center">
                <v-icon
                  v-if="userInfo.picture==null"
                  size="180"
                  style="z-index: 1;"
                >mdi-account-circle-outline</v-icon>
                <v-avatar v-else size="180" style="z-index: 1;">
                  <img :src="userInfo.picture" />
                </v-avatar>
              </div>
              <div>
                <v-btn
                  color="#7C4DFF"
                  elevation="0"
                  dark
                  block
                  class="mt-2"
                  :loading="isSelecting"
                  @click="onButtonClick"
                >
                  <h6>이미지 바꾸기</h6>
                </v-btn>
                <input
                  ref="uploader"
                  class="d-none"
                  type="file"
                  id="picture"
                  @change="handleChange"
                  accept="image/*"
                />
                <v-btn block color elevation="0" class="mt-1" @click="deleteProfile">이미지 삭제</v-btn>
              </div>
            </div>

            <!-- 닉네임, 한줄 소개 수정 섹션 -->
            <div class="col-md-8 d-flex-wrap align-self-start ml-8">
              <div class="d-flex align-items-center justify-content-between p-0">
                <p
                  v-if="curName"
                  class="lead d-inline my-3"
                  style="font-size: 3rem; font-weight: bold;"
                >{{ userInfo.nickname }}</p>
                <v-text-field
                  v-if="updateName"
                  v-model="userInfo.nickname"
                  outlined
                  required
                  height="6rem"
                  style="font-size: 2.2rem; font-weight: bold;"
                ></v-text-field>
              </div>
              <p v-if="curIntro" class="mt-6 mb-10">{{ userInfo.introduction }}</p>
              <v-text-field v-if="updateIntro" v-model="userInfo.introduction" outlined required></v-text-field>
              <div class="update_link">
                <a v-if="showUpdateBtn" style="text-decoration: none;" @click="showForm">수정</a>
                <v-btn
                  elevation="0"
                  color="#7C4DFF"
                  dark
                  style="float: right;"
                  v-if="showSaveBtn"
                  @click="closeForm"
                >확인</v-btn>
              </div>
            </div>
          </div>

          <!-- 회원 정보 섹션 -->
          <div class="ml-16 mr-3 mt-16">
            <v-row class="mx-1">
              <v-col cols="12" md="4" style="text-align: right;">
                <div style="font-size: 1.5rem; font-weight:bold;">내 라커 이름</div>
              </v-col>
              <v-col md="8" class="mt-1">
                <div style="font-size: 1.3">locker</div>
              </v-col>
            </v-row>
          </div>
          <hr />
          <div class="ml-16 mr-3 mt-5">
            <v-row class="mx-1">
              <v-col cols="12" md="4" style="text-align: right;">
                <div style="font-size: 1.5rem; font-weight:bold;">소셜 정보</div>
              </v-col>
              <v-col md="8">
                <div class="d-inline">
                  <a>
                    <v-icon size="35" style="color: black; padding-right: 1rem;">mdi-github</v-icon>
                  </a>
                  <a>
                    <v-icon size="35" style="color: black;">mdi-google-plus</v-icon>
                  </a>
                </div>
              </v-col>
            </v-row>
          </div>
          <hr />
          <div class="ml-16 mr-3 mt-5">
            <v-row class="mx-1">
              <v-col cols="12" md="4" style="text-align: right;">
                <div style="font-size: 1.5rem; font-weight:bold;">이메일 주소</div>
              </v-col>
              <v-col md="8" class="mt-1">
                <div style="font-size: 1.3">{{userInfo.email}}</div>
              </v-col>
            </v-row>
          </div>
          <hr />
          <div class="ml-16 mr-3 mt-5">
            <v-row class="mx-1">
              <v-col cols="12" md="4" style="text-align: right;">
                <div style="font-size: 1.5rem; font-weight:bold;">비밀번호 변경</div>
              </v-col>
              <v-col md="8">
                <v-btn
                  v-if="userInfo.provider === null"
                  elevation="0"
                  color="#2E7D32"
                  dark
                  router
                  :to="{ name: 'changePassword' }"
                >비밀번호 변경</v-btn>
                <div v-else style="margin-top: 5px;">소셜 로그인 회원은 비밀번호를 변경할 수 없습니다!</div>
              </v-col>
            </v-row>
          </div>
          <hr />
          <div class="ml-16 mr-3 mt-5">
            <v-row class="mx-1">
              <v-col cols="12" md="4" style="text-align: right;">
                <div style="font-size: 1.5rem; font-weight:bold;">회원 탈퇴</div>
              </v-col>
              <v-col md="8">
                <v-btn
                  elevation="0"
                  color="#C62828"
                  dark
                  class="mb-3"
                  @click="deleteUserInfo({id: userInfo.id})"
                >회원 탈퇴</v-btn>
                <br />탈퇴 시 작성하신 포스트 및 댓글이 모두 삭제되며
                <br />복구되지 않습니다.
              </v-col>
            </v-row>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import { mapState, mapActions } from "vuex";
import AWS from "aws-sdk";
import axios from "../lib/axios-common.js";
const s3Dir = "profiles/";
const s3Path =
  "https://locker-beaver-image.s3.ap-northeast-2.amazonaws.com/" + s3Dir; //s3 프로필 이미지 폴더 경로
export default {
  data: () => {
    return {
      curName: true,
      curIntro: true,
      updateName: false,
      updateIntro: false,
      showUpdateBtn: true,
      showSaveBtn: false,
      isSelecting: false,
      file: null, //프로필 이미지 파일
      albumBucketName: "locker-beaver-image", //s3세팅
      bucketRegion: "ap-northeast-2", //s3세팅
      IdentityPoolId: "ap-northeast-2:87ba0e75-43e1-4245-96d4-9027f0c262b8"
    };
  },
  computed: {
    ...mapState(["userInfo"])
  },
  methods: {
    ...mapActions(["updateUserInfo", "deleteUserInfo"]),

    showForm() {
      this.curName = false;
      this.curIntro = false;
      this.updateName = true;
      this.updateIntro = true;
      this.showUpdateBtn = false;
      this.showSaveBtn = true;
    },
    closeForm() {
      this.updateUserInfo(this.userInfo);
      this.curName = true;
      this.curIntro = true;
      this.updateName = false;
      this.updateIntro = false;
      this.showUpdateBtn = true;
      this.showSaveBtn = false;
    },
    onButtonClick() {
      this.isSelecting = true;
      window.addEventListener(
        "focus",
        () => {
          this.isSelecting = false;
        },
        { once: true }
      );

      this.$refs.uploader.click();
    },

    handleChange(event) {
      this.file = event.target.files[0];
      if (!this.file || !this.file.type.match(/^image\/(png|jpeg)$/)) {
        //이미지 아닌 경우
        alert("이미지 파일을 선택해주세요");
      } else {
        const imageExtension = this.file.name.split(".")[1]; //확장자
        const imageFileName =
          "profile_" + this.userInfo.id + "." + imageExtension; //이미지파일명
        const imagePath = s3Path + imageFileName; //이미지 경로
        const photoKey = s3Dir + imageFileName; //s3 업로드용 key
        //이미지 삭제
        this.deleteProfile();
        //s3에 이미지 업로드
        this.uploadImage(photoKey);
        //DB에 이미지 경로 등록(수정)
        this.registerImagePath(imagePath);
        //post의 usr_picture에 경로 수정
        this.updatePostPicturePath(imagePath);

        // let reader = new FileReader();
        // console.log("this.userInfo", this.userInfo)

        // reader.onload = function(){
        //   document.getElementById("picture").src = reader.result;
        //   this.preview = reader.result
        // this.$state.userInfo.picture = reader.result;
        // console.log("base64??", reader.result)

        // picture db 수정 필요 ?? -> long long 이런거로?
        // axios
        //   .put("/v1/user/image?id="+"/picture="+reader.result, config)
        //   .then(response => {
        //     console.log("response", response)
        //   })
        //   .catch(exp =>{
        //     console.log("exp", exp)
        //   })
        // }
        // if(file){
        //   reader.readAsDataURL(file);
        // }
      }
    },
    uploadImage(photoKey) {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: {
          Bucket: this.albumBucketName
        }
      });

      //s3에 실제로 이미지 업로드
      s3.upload(
        //실제 업로드
        {
          Key: photoKey,
          Body: this.file,
          ACL: "public-read"
        },
        (err, data) => {
          if (err) {
            err;
            return alert(
              "There was an error uploading your photo: ",
              err.message
            );
          }
          data;
        }
      );
    },
    registerImagePath(imagePath) {
      //DB에 이미지 경로 등록(수정)
      //TODO
      const token = localStorage.getItem("access_token");

      axios
        .put("/v1/user/img/update?token=" + token + "&picture=" + imagePath)
        .then(() => {
          setTimeout(() => {
            this.userInfo.picture = imagePath;
          }, 1500);
        });
    },
    updatePostPicturePath(imagePath) {
      //Post의 usr_picture 경로 수정
      axios.put("/v1/post/picture", {
        usr_id: this.userInfo.id,
        usr_picture: imagePath
      });
    },

    deleteProfile() {
      //프로필 이미지 삭제(경로를 userInfo를 이용해서 받아야함)
      if (this.userInfo.picture != null) {
        const photoKey = this.userInfo.picture.split(".com/")[1]; // profiles/profile_userInfo.id.확장자
        //s3에서 이미지 삭제
        this.deleteImage(photoKey);

        //DB에서 이미지 삭제
        this.deleteImagePath();

        //post에서 usr_picture null로 변경
        this.deletePostPicturePath();
      }
    },
    deleteImage(photoKey) {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01"
      });

      //s3에서 이미지 삭제
      s3.deleteObject(
        {
          Bucket: this.albumBucketName,
          Key: photoKey
        },
        (err, data) => {
          if (err) {
            throw err;
          }
          data;
        }
      );
    },
    deleteImagePath() {
      //DB에서 이미지 삭제
      const token = localStorage.getItem("access_token");

      axios.delete("/v1/user/img/delete?token=" + token).then(() => {
        this.userInfo.picture = null;
      });

      // axios
      //   .delete("/v1/user/img/delete?token=" + token)
      //   .then(() => {
      //     this.userInfo.picture = null; //state내 userInfo 이미지 경로 변경s
      //   })
      //   .catch(exp => alert("이미지 경로 삭제 오류" + exp));
    },
    deletePostPicturePath() {
      //post에서 usr_picture null로 변경
      axios.put("/v1/post/picture", {
        usr_id: this.userInfo.id
      });
    }
  },
  created() {
    console.log("가지고 온 유저 정보 : ", this.userInfo);
  }
};
</script>