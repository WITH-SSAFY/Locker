<template>
  <v-container>
    <v-row alignment="center" justify="center">
      <v-col cols="12" sm="6">
        <div id="thumbnail">
          <h4>포스트 미리보기</h4>
          <div id="post-preview">
            <div id="no-picture" v-if="myPost.picture == null">
              <v-icon id="no-image" size="120" style="z-index: 1;"
                >image</v-icon
              >
              <br />
              <v-btn depressed>이미지 업로드</v-btn>
            </div>
            <div v-else></div>
          </div>
        </div>
        <div id="description">
          <h5>{{ myPost.title }}</h5>

          <v-textarea
            label="description"
            auto-grow
            outlined
            row-height="20"
            v-model="description"
          ></v-textarea>
        </div>
        <div id="buttons">
          <v-btn @click="postContent">출간하기</v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "../../lib/axios-common.js";

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
  },
};
</script>

<style scoped>
#thumbnail {
  text-align: center;
}

#post-preview {
  padding-top: 15px;
  width: 300px;
  height: 200px;
  background-color: grey;
  margin: 0 auto;
}
#description {
  margin-top: 50px;
}
</style>
