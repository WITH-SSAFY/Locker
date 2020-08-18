<template>
  <div style="background-color: #eceffc;">

    <div style="margin-left: 3.5rem; margin-top: 3.5rem;">
      <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>유저들이 좋아하는 포스트</p>
      <div class="under-line"></div>
    </div>

    <!-- TODO: 무한스크롤 적용하기 -->
    <div class="w-75" style="margin-left: 3.5rem;">
      <v-row>
        <v-col
          v-for="post in postList"
          :key="post.pid"
          cols="12"
          lg="4"
          md="6"
        >   
          <v-card>
            <!-- TODO: 포스트 썸네일 가져오기 -->
            <v-img
              src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg"
              height="194"
            ></v-img>

            <v-list-item>
              <!-- TODO: 글 작성자의 프로필 사진으로 수정 필요 -->
              <v-flex style="position: absolute; top: -23px;">
                <v-icon
                  v-if="userInfo.picture==null"
                  style="border: solid 2px #fff"
                >
                  mdi-account-circle-outline
                </v-icon>
                <v-avatar v-else style="border: solid 2px #fff">
                  <img :src="userInfo.picture" />
                </v-avatar>
              </v-flex>

              <v-list-item-content class="pa-5">
                <v-list-item-title class="bold" style="font-size: 1.5rem; margin-left: 2.3rem;">{{ post.title }}</v-list-item-title>
                <v-list-item-subtitle class="regular" style="font-size: 0.9rem; margin-left: 2.3rem;">{{ post.nickname }}</v-list-item-subtitle>                
              </v-list-item-content>
            </v-list-item>

            <v-card-text>
              <!-- TODO: 컨텐츠 내용 글자 수 제한해서 보여주기 -->
              {{ post.description }}
            </v-card-text>

            <v-card-actions>
              <!-- TODO: 좋아요 수 가져오기 -->
              <v-icon small color="#7C4DFF" class="ml-auto">mdi-heart</v-icon>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>


  </div>
</template>

<script>
import { mapState } from "vuex"

export default {
  name: 'mainPostList2',
  data: function () {
    return {

    }
  },
  computed: {
    ...mapState(["postList", "userInfo"])
  },
  created () {
    this.$store.dispatch('getPostList')
    this.postList
    this.userInfo
    // console.log(this.postList)
  },

}
</script>

<style>
  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7C4DFF;
  }
</style>