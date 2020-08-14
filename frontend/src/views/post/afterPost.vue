<template>
  <v-container>
    <v-row alignment="center" justify="center">
      <v-col cols="12" sm="6">
        <div id="thumbnail">
          <h4>포스트 미리보기</h4>
          <div id="post-preview">
            <v-icon
              v-if="myPost.thumbnail == null"
              id="no-image"
              size="200"
              style="z-index: 1; margin-top: 50px"
              >image</v-icon
            >
            <img
              v-else
              id="image"
              :src="myPost.thumbnail"
              style="z-index: 1;"
            />
          </div>
          <div id="buttons">
            <v-btn id="upload" color="#EDE7F6" depressed @click="uploadThumnail"
              >이미지 업로드</v-btn
            >
            <v-btn id="delete" depressed @click="removeThumbnail"
              >이미지 삭제</v-btn
            >
          </div>

          <input
            ref="uploader"
            class="d-none"
            type="file"
            id="picture"
            @change="handleChange"
            accept="image/*"
          />
        </div>
        <div id="description">
          <h5>{{ myPost.title }}</h5>

          <v-textarea
            label="description"
            auto-grow
            outlined
            row-height="20"
            counter="150"
            v-model="description"
          ></v-textarea>
        </div>
        <div id="buttons">
          <v-btn color="#7C4DFF" @click="postContent">출간하기</v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AWS from "aws-sdk";
import axios from "../../lib/axios-common.js";
const s3Dir = "thumbnails/";
const s3Path =
  "https://locker-beaver-image.s3.ap-northeast-2.amazonaws.com/" + s3Dir; //s3 프로필 이미지 폴더 경로
export default {
  created() {
    this.myPost = this.$store.state.myPost;
    this.myTags = this.$store.state.myTags;
  },
  data() {
    return {
      myPost: {},
      myTags: [],
      description: "",
      tagname: "",
      pid: 0,
      tagid: 0,
      file: null, //프로필 이미지 파일
      albumBucketName: "locker-beaver-image", //s3세팅
      bucketRegion: "ap-northeast-2", //s3세팅
      IdentityPoolId: "ap-northeast-2:59ec49b1-e859-4beb-a30e-b8a11a7341b8", //s3세팅
    };
  },
  methods: {
    postContent() {
      axios
        .post("/v1/post", {
          title: this.myPost.title,
          email: this.myPost.email,
          content: this.myPost.content,
          nickname: this.myPost.nickname,
          description: this.description,
          thumbnail: this.myPost.thumbnail,
        })
        .then((response) => {
          this.pid = response.data;
          this.checkDupTag(); //태그 중복 확인

          //this.$store.dispatch("getMyPost", this.pid); //state에포스트 정보 업데이트
          this.$store.dispatch("getMyPostList");
          this.$router.push("/mypage");
        })
        .catch((exp) => alert("글 작성에 실패했습니다" + exp));
    },
    async checkDupTag() {
      //서버로 부터 이미 등록된 태그인지 검사(태그 중복 검사)
      for (let i = 0; i < this.myTags.length; i++) {
        this.tagname = this.myTags[i];
        let response = await axios.get("v1/tag/" + this.tagname); //서버로 부터 응답이 올때까지 대기
        let data = response.data;
        if (data == 0) {
          //중복이 없다
          this.registNewTag();
        } else {
          this.tagid = data;
          this.connectTag();
        }
      }
    },

    registNewTag() {
      //새로운 태그 등록
      console.log("registTag: " + this.tagname);
      axios
        .post("/v1/tag", { tagname: this.tagname })
        .then((response) => {
          this.tagid = response.data;
          console.log("new tagid: " + this.tagid);
          this.connectTag(); //이 포스트에 해당 태그가 있다는 것을 알려줌(pid와 tagid연결)
        })
        .catch((exp) => alert(this.tagname + "태그 등록 실패" + exp));
    },
    connectTag() {
      //pid와 tagid 연결
      axios
        .post("v1/tag/connect", { pid: this.pid, tagid: this.tagid })
        .catch((exp) => alert("태그 연결 실패" + exp));
    },
    uploadThumnail() {
      // this.isSelecting = true;
      // window.addEventListener(
      //   "focus",
      //   () => {
      //     this.isSelecting = false;
      //   },
      //   { once: true }
      // );

      this.$refs.uploader.click(); //handleChange() 실행
    },
    handleChange(event) {
      this.file = event.target.files[0];
      console.log("file", this.file);
      if (!this.file.type.match(/^image\/(png|jpeg)$/)) {
        //이미지 아닌 경우
        alert("이미지 파일을 선택해주세요");
      } else if (this.file) {
        const imageExtension = this.file.name.split(".")[1]; //확장자
        const imageFileName =
          "thumbnail_" + this.myPost.pid + "." + imageExtension; //이미지파일명
        const imagePath = s3Path + imageFileName; //이미지 경로
        const photoKey = s3Dir + imageFileName; //s3 업로드용 key
        //s3에 이미지 업로드
        this.uploadThumbnail(photoKey);
        this.myPost.thumbnail = imagePath;
      }
    },
    uploadThumbnail(photoKey) {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        }),
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: {
          Bucket: this.albumBucketName,
        },
      });

      //s3에 실제로 이미지 업로드
      s3.upload(
        //실제 업로드
        {
          Key: photoKey,
          Body: this.file,
          ACL: "public-read",
        },
        (err, data) => {
          if (err) {
            console.log(err);
            return alert(
              "There was an error uploading your photo: ",
              err.message
            );
          }
          console.log(data);
        }
      );
    },
    removeThumbnail() {
      //프로필 이미지 삭제
      const imageFileName = this.myPost.thumbnail; //이미지파일명
      //const imagePath = s3Path + imageFileName; //이미지 경로
      const photoKey = s3Dir + imageFileName; //s3 key

      //s3에서 이미지 삭제
      this.deleteThumbnail(photoKey);
      this.myPost.thumbnail = null;
    },
    deleteThumbnail(photoKey) {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        }),
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: {
          Bucket: this.albumBucketName,
        },
      });

      //s3에서 이미지 삭제
      s3.deleteObject(
        {
          Key: photoKey,
        },
        (err, data) => {
          if (err) {
            throw err;
          }
          console.log(data);
        }
      );
    },
  },
};
</script>

<style scoped>
#thumbnail {
  text-align: center;
}

#post-preview {
  height: 300px;
  margin: 0 auto;
  background-color: silver;
}
#image {
  height: 300px;
}

#description {
  margin-top: 50px;
}
#buttons {
  margin-top: 15px;
  text-align: center;
}
</style>
