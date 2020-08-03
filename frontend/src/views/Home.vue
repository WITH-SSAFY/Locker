<template>
  <v-main class="full-screen">
    <div class="swiper-container">
      <div class="swiper-wrapper">
        <div class="swiper-slide">
          <mainPostList/>
        </div>
      </div>
      <div class="swiper-button-prev"></div>
      <div class="swiper-button-next"></div>
    </div>

    <ul class="nav-buttons">
      <li><button value="1" class="active fp-btn" data-page="0"></button></li>
      <li><button value="2" class="fp-btn" data-page="1"></button></li>
      <li><button value="3" class="fp-btn" data-page="2"></button></li>
      <li><button value="4" class="fp-btn" data-page="3"></button></li>
    </ul>

    <contents1/>
    <contents2/>
    <contents3/>
  </v-main>
</template>
 
<script>
  import '../assets/css/main_page.css'
  import '../assets/js/swiper.js'

  import contents1 from './contents/contents-1.vue'
  import contents2 from './contents/contents-2.vue'
  import contents3 from './contents/contents-3.vue'
  import mainPostList from './contents/mainPostList.vue'

  export default {
    name:"Home",
    components:{
      contents1,
      contents2,
      contents3,
      mainPostList
    },
    data() {
      return {
        isNotMain: false,
      }
    },
    created() {
    },
    methods: {
    }
  }
</script>
<style scoped>
.swiper-container {
  width: 100%;
  min-height: 100vh;
}

.swiper-button-prev {
  color: #EDE7F6;
}

.swiper-button-next {
  color: #EDE7F6;
}
</style>