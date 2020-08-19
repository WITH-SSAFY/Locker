<template>
  <v-container>
    <v-row alignment="center" justify="center">
      <v-col cols="12" sm="6">
        <div id="thumbnail">
          <h5>썸네일 미리보기</h5>
          <div id="post-preview">
            <v-icon
              v-if="thumbnail == null"
              id="no-image"
              size="200"
              style="z-index: 1; margin-top: 50px"
            >image</v-icon>
            <!-- <v-avatar v-else size="200" style="z-index: 1;"> -->
            <!-- <div style="border-radius: 100%; width: 8rem;" id="picture"> -->
            <v-img
              v-else
              style="z-index: 1; width"
              id="image"
              :aspect-ratio="16/9"
              max-height="300px"
              :src="thumbnail"
            />
            <!-- </div> -->
            <!-- </v-avatar> -->
          </div>
          <div id="buttons1">
            <v-btn id="upload" color="#EDE7F6" depressed @click="uploadThumnail">썸네일 업로드</v-btn>
            <v-btn style="margin: 0 5px;" id="delete" depressed @click="removeThumbnail">썸네일 삭제</v-btn>
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
        <div id="repository" style="margin-top: 40px">
          <h5>Repository 선택</h5>
          <v-select
            :items="myRepoList"
            item-text="repoName"
            item-value="id"
            label="Solo field"
            solo
            @change="selectMyRepo"
          ></v-select>
        </div>
        <div id="description">
          <h5>Title: {{ myPost.title }}</h5>

          <v-textarea
            label="description"
            auto-grow
            outlined
            row-height="20"
            counter="150"
            v-model="description"
          ></v-textarea>
        </div>
        <div id="buttons2">
          <v-btn style="color: white;" color="#7C4DFF" @click="postContent">출간하기</v-btn>
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
    this.myPost;
    this.myTags;
    this.description;
    this.thumbnail = this.$store.state.myPost.thumbnail;
    this.description = this.$store.state.myPost.description;
    this.getMyRepoList();
  },
  data() {
    return {
      thumbnail: null,
      description: "",
      pid: 0,
      tagid: 0,
      file: null, //프로필 이미지 파일
      albumBucketName: "locker-beaver-image", //s3세팅
      bucketRegion: "ap-northeast-2", //s3세팅
      IdentityPoolId: "ap-northeast-2:87ba0e75-43e1-4245-96d4-9027f0c262b8", //s3세팅
      myRepoList: [{ id: 0, repoName: "선택안함" }], //선택안함 창 출력
      repoId: -1
    };
  },
  computed: {
    myPost() {
      return this.$store.state.myPost;
    },
    myTags() {
      return this.$store.state.myTags;
    }
  },
  methods: {
    async postContent() {
      console.log(this.myPost.content);
      if (this.repoId == -1) {
        //레포 선택 아예 x
        alert("Repository를 선택해주세요");
      } else {
        if (this.repoId == 0) {
          //레포 '선택 안함' 으로 선택
          this.repoId = null;
        }

        if (this.$store.state.isNewPost) {
          //글 새로 작성시
          let response = await axios.post("/v1/post", {
            title: this.myPost.title,
            email: this.myPost.email,
            content: this.myPost.content,
            nickname: this.myPost.nickname,
            description: this.description,
            thumbnail: this.thumbnail,
            usr_id: this.$store.state.userInfo.id,
            repo_id: this.repoId,
            usr_picture: this.$store.state.userInfo.picture
          });
          this.pid = response.data;
          await this.checkDupTag(); //태그 중복 확인

          this.$store.dispatch("getMyPostList", this.$store.state.userInfo.id);
          this.$router.push("/mypage");
        } else {
          //글 수정시
          await axios.put("/v1/post", {
            pid: this.myPost.pid,
            title: this.myPost.title,
            content: this.myPost.content,
            description: this.description,
            thumbnail: this.thumbnail,
            repo_id: this.repoId,
            usr_picture: this.$store.state.userInfo.picture
          });
          this.pid = this.myPost.pid;
          await this.deletePostAllTag(); //포스트가 가자고 있는 모든 태그 삭제
          await this.checkDupTag(); //나머지 로직은 글 생성 과 같음

          this.$store.dispatch("getMyPostList");
          this.$router.push("/mypage");
        }
      }
    },
    async checkDupTag() {
      //서버로 부터 이미 등록된 태그인지 검사(태그 중복 검사)
      for (let i = 0; i < this.myTags.length; i++) {
        this.tagname = this.myTags[i];
        let response = await axios.get("v1/tag/" + this.tagname); //서버로 부터 응답이 올때까지 대기
        let data = response.data;
        if (data == 0) {
          //중복이 없다
          await this.registNewTag(); //응답이 올 때 까지 기다림
        } else {
          this.tagid = data;
        }
        await this.connectTag(); //응답이 올때 까지 기다림
      }
    },

    async registNewTag() {
      // //새로운 태그 등록
      let response = await axios.post("/v1/tag", { tagname: this.tagname });

      this.tagid = response.data;
    },
    async connectTag() {
      //pid와 tagid 연결
      await axios.post("v1/tag/connect", {
        pid: this.pid,
        tagid: this.tagid
      });
    },
    async deletePostAllTag() {
      //포스트에 등록된 모든 태그 삭제
      await axios.delete("v1/tag?pid=" + this.myPost.pid); //서버로 부터 응답이 올때까지 대기
    },

    uploadThumnail() {
      this.$refs.uploader.click(); //handleChange() 실행
    },
    handleChange(event) {
      console.log("handleChange");
      this.file = event.target.files[0];
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
        console.log("new ImagePath: ", imagePath);
        this.removeThumbnail(this.thumbnail);
        this.uploadThumbnail(photoKey);
        setTimeout(() => {
          this.thumbnail = imagePath;
          console.log("new Thumbnail: ", this.thumbnail);
        }, 2500);
      }
    },
    uploadThumbnail(photoKey) {
      console.log("uploadThumbnail");

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
            throw err;
          }
          data;
        }
      );
    },
    removeThumbnail() {
      console.log("removeThumbnail");
      //프로필 이미지 삭제
      //const imageFileName = this.myPost.thumbnail.split(".com/")[1]; //이미지파일명
      //console.log("imageFileName: ", imageFileName);
      //const imagePath = s3Path + imageFileName; //이미지 경로
      if (this.thumbnail != null) {
        const photoKey = this.thumbnail.split(".com/")[1]; //s3 key
        console.log("delete Thumbnail1: ", this.thumbnail);
        //s3에서 이미지 삭제
        this.deleteThumbnail(photoKey);
      }
    },
    deleteThumbnail(photoKey) {
      console.log("deleteThumbnail");
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
      this.thumbnail = null;
    },
    getMyRepoList() {
      //내 레포 리스트를 받아옴
      axios
        .get("/v1/github?pk=" + 15) //일단 준호형 껄로 테스트(this.$store.state.userInfo.id)
        .then(response => {
          console.log("list: ", response.data.list);
          this.myRepoList = this.myRepoList.concat(response.data.list);
        });
    },
    selectMyRepo(id) {
      //선택한 레포의 id를 받아옴
      console.log("repoid: ", id);
      this.repoId = id;
    }
  }
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

#buttons1 {
  margin-top: 15px;
  text-align: center;
}
#buttons2 {
  margin-top: 15px;
  text-align: right;
}
</style>
