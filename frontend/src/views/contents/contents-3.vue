<template>
  <div class="contents-3">
    <v-container fill-height>
      <div class="diagonals">
        <div class="diagonal1"></div>
        <div class="diagonal2"></div>
      </div>

      <v-layout>
        <v-flex style="width: 30vw;">

        </v-flex>

        <v-flex style="margin-top: 7rem; width: 60vw;">
          <p class="bolder" style="font-size: 3rem;">개발자를 가장 잘 아는 개발자</p>
          <p class="bolder" style="font-size: 3rem; margin-bottom: 5rem;">이들이 만든 블로그,  <span style="color: #7C4DFF; font-size: 3.5rem;">LOCKER</span></p>
          <v-btn large color="#1a1a1a" style="padding: 2rem; margin-bottom: 2rem;"><span class="bold" style="color: #fff; font-size: 1.5rem;">Getting Started</span></v-btn>
          <div class="wrapper">
              <div class="typing-demo bolder" style="color: #7C4DFF; font-size: 3.5rem;">이제, 당신의 LOCKER를 여세요</div>
          </div>

        </v-flex>
      </v-layout>

    </v-container>
  </div>
</template>

<script>
export default {
  name: 'contents3',
  components: {

  },
  props: {

  },
}
</script>

<style>
.wrapper {
  height: 8vh;
  display: flex;
}

.typing-demo {
  width: 22ch;
  animation: typing 5s steps(22), blink .5s step-end infinite alternate;
  white-space: nowrap;
  overflow: hidden;
  border-right: 3px solid;
}

@keyframes typing {
  from {
    width: 0
  }
}
    
@keyframes blink {
  50% {
    border-color: transparent
  }
}

</style>