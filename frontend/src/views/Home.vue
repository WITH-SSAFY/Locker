<template>
  <div>
    
    <div v-if="isLogin">
      <div><mainPostList/></div>
      <div><mainPostList2/></div>
    </div>

    <div v-if="!isLogin">
      <v-main class="full-screen">
        <full-page :options="options" id="fullpage">
          <div class="section"><contents1/></div>
          <div class="section"><contents2/></div>
          <div class="section"><contents3/></div>
        </full-page>
      </v-main>
    </div>
    
  </div>
</template>
 
<script>
  import '../assets/css/main_page.css'
  import { mapState } from "vuex"

  import contents1 from './contents/contents-1.vue'
  import contents2 from './contents/contents-2.vue'
  import contents3 from './contents/contents-3.vue'
  import mainPostList from './contents/mainPostList.vue'
  import mainPostList2 from './contents/mainPostList-2.vue'

  export default {
    name:"Home",
    components:{
      contents1,
      contents2,
      contents3,
      mainPostList,
      mainPostList2,
    },
    data() {
      return {
        isNotMain: false,
        options: {
          licenseKey: 'YOUR_KEY_HERE',
          afterLoad: this.afterLoad,
          scrollBar: false,
          navigation: true,
          navigationPosition: 'right',
          menu: '#menu',
        }
      }
    },
    created() {
      this.isLogin;
    },
    computed: {
      ...mapState(["isLogin"]),
    },
    methods: {
    }
  }
</script>
<style scoped>
.section {
  margin: 0;
  padding: 0;
}
</style>