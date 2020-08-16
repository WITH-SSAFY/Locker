<template>
  <nav class="nav_back">
    <!-- <div class="nav_deco"></div> -->

    <div class="d-flex justify-content-between" style="padding: 0 3rem;">
      <!-- 로고 -->
      <router-link :to="{ name: 'home' }">
        <v-icon large color="black" class="nav_icon">mdi-archive-outline</v-icon>
        <div class="nav_title" v-if="isNotMain">LOCKER</div>
      </router-link>

      <!-- 버튼 그룹 -->
      <div class="button_group">
        <v-btn color="#7C4DFF" text icon router :to="{ name: 'search'}" class="mx-1">
          <v-icon large>search</v-icon>
        </v-btn>

        <v-btn
          color="#7C4DFF"
          text
          icon
          v-if="isLogin"
          class="mx-2"
          router
          :to="{ name: 'createPost'}"
        >
          <v-icon large>create</v-icon>
        </v-btn>

        <v-menu offset-y v-if="isLogin" absolute>
          <template v-slot:activator="{ on, attrs }">
            <div v-bind="attrs" v-on="on" class="menu ml-2">
              <!-- <v-icon
              large
              color="rgba( 117, 117, 117, 1)"
            >
              mdi-account-circle</v-icon>-->
              <v-icon
                v-if="userInfo.picture==null"
                large
                color="rgba( 117, 117, 117, 1)"
              >mdi-account-circle-outline</v-icon>
              <v-avatar v-else size="35">
                <img :src="userInfo.picture" />
              </v-avatar>
            </div>
          </template>

          <!-- 드롭다운 -->
          <v-list>
            <v-list-item @click="goMypage">
              <v-list-item-title>mypage</v-list-item-title>
            </v-list-item>
            <v-list-item @click="$store.dispatch('logout')">
              <v-list-item-title>logout</v-list-item-title>
            </v-list-item>
            <v-list-item router :to="{name: 'userSetting'}">
              <v-list-item-title>setting</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <!-- 로그인버튼 -->
        <div v-else style="display: inline;">
          <v-btn icon router :to="{name: 'login'}" class="bold mx-3">로그인</v-btn>
          <v-btn icon router :to="{name: 'register'}" class="bold mx-3">회원가입</v-btn>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapState } from "vuex";
import("../assets/css/header-style.css");

export default {
  name: "Header",
  computed: {
    ...mapState(["isLogin", "userInfo"])
  },
  props: {
    source: String,
    isNotMain: Boolean
  },
  // props: ["source", "isNotMain"],
  data: () => ({
    drawer: null,
    test: 10,
    showSearch: false
  }),
  methods: {
    goMypage() {
      //this.$store.dispatch('getMyPostList');
      this.$router.push({ name: "mypage" });
    }
  }
};
</script>