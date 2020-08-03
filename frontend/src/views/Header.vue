<template>
  <nav class="nav_back">
    <div class="nav_deco"></div>
    <v-container>
      <div>
        <router-link :to="{ name: 'home' }">
          <v-icon
            large
            color="black"
            class="nav_icon"
          >
            mdi-archive-outline
          </v-icon>
          <div class="nav_title" v-if="isNotMain">
            LOCKER
          </div>
        </router-link>

        <div class="button_group">
          <v-btn
            color="#7C4DFF"
            text
            icon
            router :to="{ name: 'search'}"
            class="mx-1"
          >
            <v-icon large>search</v-icon>
          </v-btn>

          <v-btn
            color="#7C4DFF"
            text
            icon
            v-if="isLogin"
            class="mx-1"
            router :to="{ name: 'createPost'}"
          >
            <v-icon
              large
            >create</v-icon>
          </v-btn>

          <v-btn
            color="#7C4DFF"
            text
            icon
            v-if="isLogin"
            class="mx-1"
          >
            <v-badge
              color="#D4E157"
              overlap
              :value="test"
              dot
            >
              <v-icon large>notifications</v-icon>
            </v-badge>
          </v-btn>

          <v-menu
            offset-y
            v-if="isLogin"
            absolute  
          >
          <template v-slot:activator="{ on, attrs }">
            <div
              v-bind="attrs"
              v-on="on"
              class="menu ml-2"
            >
              <v-icon
                large
                color="rgba( 117, 117, 117, 1)"
              >
              mdi-account-circle</v-icon>
            </div>
          </template>

          <v-list>
            <v-list-item  @click="goMypage">
              <v-list-item-title>mypage</v-list-item-title>
            </v-list-item>
            <v-list-item
              @click="$store.dispatch('logout')"
              >
              <v-list-item-title>logout</v-list-item-title>
            </v-list-item>
          </v-list>
          </v-menu>
        
          <v-btn
            v-else
            icon
            router :to="{name: 'login'}"
          >
            Login
          </v-btn>
        </div>
      </div>
    </v-container>
  </nav>
</template>

<script>
import { mapState } from "vuex";
import('../assets/css/header-style.css')

  export default {
    name: 'Header',
    computed: {
      ...mapState(["isLogin"]),
      ...mapState(["userInfo"])
    },
    props: {
      source: String,
      isNotMain: Boolean,
    },
    // props: ["source", "isNotMain"],
    data: () => ({
      drawer: null,
      test: 10,
      showSearch: false
    }),
    methods:{
      goMypage(){
        //this.$store.dispatch('getMyPostList');
        this.$router.push({name: "mypage"});
      }
    }

  }
</script>