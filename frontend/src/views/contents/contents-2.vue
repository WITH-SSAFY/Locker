<template>
  <div class="contents-2">
    <v-container fill-height style="position: relative; padding: 0;">
      <v-flex class="text-section">
        <v-flex class="logo">
          <!-- <v-icon size="200" color="#EDE7F6" style="opacity: 0.1;">mdi-format-quote-open</v-icon> -->
          <p class="bolder" style="color: #7C4DFF; font-size: 4rem;">LOCKER<span style="color: #fff; font-size: 3rem;"> 는</span></p>
          <div style="margin-bottom: 7rem;"></div>
          <p class="bold" style="font-size: 2.5rem;"><span style="background-color: #7C4DFF; color: #EDE7F6;">깃헙 레포지토리</span> 별로</p>
          <p class="bold" style="font-size: 2.5rem;">포스트를 관리합니다</p>
        </v-flex>
      </v-flex>

      <div style="position: absolute; z-index: 10; width: 60vw; left: 40%; top: 32vh;">
          <!-- <v-icon size="40" color="#EDE7F6" class="mb-2">mdi-lock-open-variant</v-icon> -->
          <p class="bold" style="font-size: 1.7rem; color: #fff; background-color: rgba(0,0,0,0.6); padding: 1rem;"><span style="color: #7C4DFF;">코드와 포스트를 병행</span>하여 보여줄 수 있습니다</p>    
          <p class="bold" style="font-size: 1.7rem; color: #fff; background-color: rgba(0,0,0,0.6); padding: 1rem;"><span style="color: #7C4DFF;">언어 비율과 스탯</span>은 블로거의 신뢰도를 확인하는 지표가 됩니다</p>
          <p class="bold" style="font-size: 1.7rem; color: #fff; background-color: rgba(0,0,0,0.6); padding: 1rem;"><span style="color: #7C4DFF;">커밋 타임라인과 글을 함께</span> 확인할 수 있습니다</p>
          <v-btn router :to="{ name: 'register' }" large color="#1a1a1a" style="margin-top: 2.3rem; text-decoration: none;">
            <span class="bold" style="color: #7C4DFF; font-size: 1.5rem;">Getting Started</span>
          </v-btn>
      </div>

      <div class="lockers-back">
        <div class="diagonals">
          <div class="diagonal1"></div>
          <div class="diagonal2"></div>
        </div>

        <div class="lockers">
          <v-row>
            <v-col sm="4">
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
              offset-sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
          </v-row>

          <v-row>
            <v-col
              sm="4"
              offset-sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
                color="#727272"
              >
              </v-card>
            </v-col>
          </v-row>

          <v-row>
            <v-col sm="4">
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
                color="#727272"
              >
              </v-card>
            </v-col>
          </v-row>

        </div>
      </div>

    </v-container>
  </div>
</template>

<script>
export default {
  name: 'contents2',
  components: {

  },
  props: {

  },
}
</script>

<style>

</style>