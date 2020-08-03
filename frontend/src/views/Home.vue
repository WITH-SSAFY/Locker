<template>
  <v-main class="full-screen">
    <full-page :options="options" id="fullpage">
      <div class="section"><mainPostList/></div>
      <div class="section"><contents1/></div>
      <div class="section"><contents2/></div>
      <div class="section"><contents3/></div>
    </full-page>

    <ul class="nav-buttons">
      <li><button value="1" class="active fp-btn" data-page="0"></button></li>
      <li><button value="2" class="fp-btn" data-page="1"></button></li>
      <li><button value="3" class="fp-btn" data-page="2"></button></li>
      <li><button value="4" class="fp-btn" data-page="3"></button></li>
    </ul>

    <!-- <contents1/>
    <contents2/>
    <contents3/> -->
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
        options: {
          licenseKey: 'YOUR_KEY_HERE',
          afterLoad: this.afterLoad,
          // scrollOverflow: true,
          scrollBar: false,
          // menu: '#menu',
          navigation: true,
          // anchors: ['page1', 'page2', 'page3'],
        }
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