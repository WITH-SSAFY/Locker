<template>
  <div class="contents-1">
    <v-container fill-height style="padding: 0;">
      <v-layout style="position: relative; top: 20vh;">
        <div>
          <v-flex class="logo">
            <v-icon size="50" color="black" class="mb-5">mdi-archive-outline</v-icon>
            <p class="bold" style="font-size: 3rem;">LOCKER</p>
            <div class="under-line mb-5"></div>
          </v-flex>
          <v-btn router :to="{ name: 'register' }" large color="#7C4DFF" class="my-3" style="text-decoration: none;">
            <span class="bold" style="color: #fff; font-size: 1.5rem;">Getting Started</span>
          </v-btn>
          <v-flex style="margin-top: 2.2rem;">
            <p class="medium" style="font-size: 1.3rem;">이미 LOCKER 계정이 있으신가요?</p>
            <button router :to="{ name: 'login' }"><p class="bold" style="color: #7C4DFF; font-size: 1.3rem; text-decoration: none;">로그인하기</p></button>
          </v-flex>
        </div>

        <div class="mx-auto ml-5">
          <div class="box-top"></div>
          <div class="box-under"></div>
          <div class="box-center"></div>
        </div>
        <div style="position: relative; top: 11.5vh; z-index: 1; text-align: right;">
          <p class="bolder" style="font-size: 3.5rem;">깃헙과 가장 긴밀하게 연결되는</p>
          <p class="bolder" style="font-size: 3.5rem;">새로운 솔루션, <span style="color: #7C4DFF">LOCKER</span></p>
        </div>

      </v-layout>
    </v-container>

  </div>
</template>

<script>
export default {
  name: 'contents1',
  components: {

  },
  props: {

  },
}
</script>

<style>

</style>