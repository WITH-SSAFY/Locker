<template>
  <div>
    <v-container fill-height style="max-width:450px;">
      <v-layout align-center row wrap>
        <v-flex xs12>
          <v-flex style="margin: 2rem;">
            <h1 class="mb-5" style="font-size: 3.3rem;"><strong>LOCKER</strong></h1>
            <!-- <h1 class="ml-2 mb-5" style="posigion: absolute; font-size: 4rem;"><strong>LOCKER</strong></h1> -->
            <div class="main-under-line"></div>
          </v-flex>
          <v-card flat>
            <div
              class="p-5"
              style="font-size: 1.2rem;"
            >
              <p>Welcome !</p>
              <p>인증이 완료되었습니다.</p>
              <p>LOCKER 와 함께 하게 되어 반갑습니다.</p>
              <v-btn
                depressed
                dark
                @click="goLogin"
                color="#333"
                class="p-0 mt-5"
              >
                <strong class="px-2">로그인 화면으로 바로가기</strong>
              </v-btn> 
            </div>
          </v-card>

          <!-- 장식 박스 -->
          <div class="mx-5 p-4">
            <v-row class="no-gutters">
              <v-col sm="6">
                <v-card
                  style="height: 7rem;"
                  tile
                  color="#7C4DFF"
                >
                </v-card>
              </v-col>
              <v-col
                sm="6"
                offset-sm="6"
              >
                <v-card
                  style="height: 7rem;"
                  tile
                  color="#7C4DFF"
                >
                </v-card>
              </v-col>
            </v-row>

          </div>

        </v-flex>
      </v-layout>
    </v-container>

  </div>
</template>

<script>
import '../assets/css/main_page.css'

export default {
  methods : {
    goLogin () {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.header {
  color: #EDE7F6;
  height: 3.5rem;
}

</style>