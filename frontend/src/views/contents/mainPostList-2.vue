<template>
  <div style="background-color: #eceffc; padding: 4.5rem;">
    <!-- <div style="margin-left: 3.5rem;">
      <p class="bold" style="font-size: 1.5rem; padding-top: 4rem;">
        유저들의 like 포스트
      </p>
      <div class="under-line"></div>
    </div> -->

    <!-- TODO: 무한스크롤 적용하기 -->
    <div class="w-75" style="margin-left: 3.5rem;">
      <v-row>
        <v-col v-for="post in postList" :key="post.pid" cols="12" lg="4" md="6">
          <v-card @click="viewPost(post.pid)">
            <!-- 포스트 썸네일 가져오기 -->
            <v-img
              v-if="post.thumbnail != null"
              :src="post.thumbnail"
              height="194"
              style="position: relative;"
            >
              <!-- <div
                class="d-flex justify-content-end"
                style="position: absolute; width: 3rem; height: 3rem; background-color: rgba(0,0,0,0.7); border-radius: 3px;"
              >
                <v-icon color="white">mdi-arrow-right</v-icon>
              </div> -->
            </v-img>

            <v-img
              v-else
              src="https://lh3.googleusercontent.com/EbXw8rOdYxOGdXEFjgNP8lh-YAuUxwhOAe2jhrz3sgqvPeMac6a6tHvT35V6YMbyNvkZL4R_a2hcYBrtfUhLvhf-N2X3OB9cvH4uMw=w1064-v0"
              height="194"
              style="position: relative;"
            >
              <!-- <div
                class="d-flex justify-content-end"
                style="position: absolute; width: 3rem; height: 3rem; background-color: rgba(0,0,0,0.7); border-radius: 3px;"
              >
                <v-icon color="white">mdi-arrow-right</v-icon>
              </div> -->
            </v-img>

            <v-list-item>
              <!-- TODO: 글 작성자의 프로필 사진으로 수정 필요 -->
              <v-flex style="position: absolute; top: -23px;">
                <v-avatar
                  color="white"
                  v-if="post.usr_picture == null"
                  style="border: solid 2px #fff"
                >
                  <v-icon size="60">mdi-account-circle-outline</v-icon>
                </v-avatar>
                <v-avatar v-else style="border: solid 2px #fff">
                  <img :src="post.usr_picture" />
                </v-avatar>
              </v-flex>

              <v-list-item-content class="pa-5">
                <v-list-item-title
                  class="bold"
                  style="font-size: 1.5rem; margin-left: 2.3rem;"
                  >{{ post.title }}</v-list-item-title
                >
                <v-list-item-subtitle
                  class="regular"
                  style="font-size: 0.9rem; margin-left: 2.3rem;"
                  >{{ post.nickname }}</v-list-item-subtitle
                >
              </v-list-item-content>
            </v-list-item>

            <v-card-text
              id="description"
              style="display: -webkit-box;
                    -webkit-box-orient: vertical;
                    -webkit-line-clamp: 3;
                    overflow: hidden;
                    line-height: 1.2em;
                    height: 4.8em;"
            >
              <!-- 컨텐츠 내용 글자 수 제한해서 보여주기 -->
              {{ post.description }}
            </v-card-text>

            <v-card-actions>
              <!--  좋아요 수 가져오기 -->
              <v-icon small color="#7C4DFF" class="ml-auto">mdi-heart</v-icon>
              {{ post.likes }}
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>

    <!-- 네비게이터 박스 -->
    <article id="nav_box">
      <ul class="p-0">
        <p>
          <v-icon color="white">mdi-chevron-up</v-icon>
        </p>

        <router-link to="createPost">
          <li>
            <span class="bold text-white" style="font-size: 1.1rem;"
              >글쓰기</span
            >
          </li>
        </router-link>

        <router-link to="mypage">
          <li>
            <span class="bold text-white" style="font-size: 1.1rem;"
              >마이페이지</span
            >
          </li>
        </router-link>

        <router-link to="memberinfo">
          <li>
            <span class="bold text-white" style="font-size: 1.1rem;"
              >만든이</span
            >
          </li>
        </router-link>

        <li @click="scrollToTop">
          <span class="bold text-white" style="font-size: 1.3rem;">
            <v-icon color="white">mdi-chevron-up-circle-outline</v-icon>
          </span>
        </li>
      </ul>
    </article>
  </div>
</template>

<script>
import { mapState } from "vuex";
import("../../assets/css/nav_box.scss");

export default {
  name: "mainPostList2",
  data: function() {
    return {};
  },
  computed: {
    ...mapState(["postList", "userInfo"]),
  },
  created() {
    this.$store.dispatch("getPostList");
    this.postList;
    this.userInfo;
  },
  methods: {
    scrollToTop: function() {
      scroll(0, 0);
    },
    viewPost(pid) {
      //포스트 상세보기로 이동
      this.$store.state.pid = pid;
      this.$router.push({
        name: "readPost",
        params: { pid: pid },
      });
      //this.$store.dispatch("showMyDetail", pid);
    },
  },
};
</script>

<style>
.under-line {
  height: 0.3rem;
  width: 3.5rem;
  margin-bottom: 3rem;
  background-color: #7c4dff;
}
</style>
