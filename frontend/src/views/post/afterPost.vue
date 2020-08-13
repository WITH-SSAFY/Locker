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
          <v-btn>출간하기</v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  created() {
    this.myPost = this.$store.state.myPost;
  },
  data() {
    return {
      myPost: {},
      description: "",
    };
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
