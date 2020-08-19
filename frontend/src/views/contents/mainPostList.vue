<template>
  <div style="margin-top: 5.5rem;">
    <div class="row">
      <div class="col-md-4">
        <div style="margin-left: 4.5rem;">
          <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>의 공지사항</p>
          <div class="under-line"></div>
        </div>
        <section id="container">
          <ul id="box" class="medium" style="font-size: 1.1rem;">
            <li><router-link to="/notice1">[공지사항] - Locker blog 서비스 시작</router-link></li>
            <li><router-link to="/notice2">[공지사항] - Locker blog 서비스 시작</router-link></li>
            <li><router-link to="/notice3">[공지사항] - Locker blog 서비스 시작</router-link></li>
            
          </ul>
          <router-view></router-view>
        </section>
        
      </div>
      <div class="col-md-8">
        <div style="margin-left: 4.5rem;">
          <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>가 선정한 이 달의 레포지토리</p>
          <div class="under-line"></div>
        </div>

        <div class="cont s--inactive">
          <div class="cont__inner">

            <!-- el start -->
            <!-- likes, name, repoId, repoName, usrId -->
            <div
            
            >
              <div
                class="el"
                v-for="repo in repoList"
                :key="repo.repoId"  

              >
                <div class="el__overflow">
                  <div class="el__inner">
                    <div class="el__bg"></div>
                    <div class="el__preview-cont">
                      
                      <p class="el__heading bolder" style="font-size: 2.7rem; word-wrap: break-word;">{{ repo.repoName }}</p>
                      <p class="regular text-white" style="font-size: 1.5rem; bottom: 50%;">{{ repo.name }}</p>
                      <v-icon color="#424242" class="ml-auto">mdi-heart</v-icon>
                      <span class="text-white ml-1 medium" style="font-size: 0.9rem; color: #424242;">{{ repo.likes }}</span>

                    </div>
                    <!-- 포스트 내부 -->
                    <div class="el__content">
                      <div class="el__text">Whatever</div>
                      <div class="el__close-btn"></div>
                    </div>

                  </div>
                </div>
                <div class="el__index">
                  <div class="el__index-back">1</div>
                  <div class="el__index-front">
                    <div class="el__index-overlay" data-index="1">1</div>
                  </div>
                </div>
              </div>
            </div>
            <!-- el end -->
    
          </div>
        </div>

      </div>
    </div>
  </div>
  

</template>


<script>
import $ from 'jquery'
import '../../assets/css/main_post.scss'
import axios from "../../lib/axios-common.js"
// import notice1 from '../notice/notice-1.vue'
// import notice2 from '../notice/notice-1.vue'
// import notice3 from '../notice/notice-1.vue'

export default {
  name: 'mainPostList',
  components: {

  },
  computed: {

  },
  data: function () {
    return {
      repoList: [],
    }
  },
  methods: {
    hotRepo () {
      axios
        .get("/v1/post/hot")
        .then(response => {
          this.repoList = response.data.list
          console.log(this.repoList)
        })
        .catch(exp => alert("핫 레포 가져오기 실패" + exp))
    }
  },
  created() {
    this.hotRepo();
  },
  mounted() {
    var $cont = document.querySelector('.cont');
    var $elsArr = [].slice.call(document.querySelectorAll('.el'));
    var $closeBtnsArr = [].slice.call(document.querySelectorAll('.el__close-btn'));

    setTimeout(function() {
      $cont.classList.remove('s--inactive');
    }, 200);

    $elsArr.forEach(function($el) {
      $el.addEventListener('click', function() {
        // console.log($el, i)
        if (this.classList.contains('s--active')) return;
        $cont.classList.add('s--el-active');
        this.classList.add('s--active');
      });
    });

    $closeBtnsArr.forEach(function($btn) {
      $btn.addEventListener('click', function(e) {
        e.stopPropagation();
        $cont.classList.remove('s--el-active');
        document.querySelector('.el.s--active').classList.remove('s--active');
      });
    });

    var $box = $('ul#box li:first-child');
    (function toggleBox() {
      $box.slideToggle();
      setTimeout(function(){
        toggleBox();
      },2250);
    })();


  }, 

}
</script>


<style scoped>
  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7C4DFF;
  }

  section#container {
    overflow: hidden;
    /* border: solid 3px #eceffc; */
    width: 500px;
    margin-left: 4rem;
    padding: 10px 20px;
    height: 150px;

  }

  ul#box {
    margin: 0;
    padding: 0; 
  }

  ul#box li {
    text-decoration: none;
    list-style: none;
    height: 45px;
    margin: 15px 0;
    border-bottom: solid 1.3px #7C4DFF;
    margin-left: 10px;
    margin-right: 10px;
    padding-bottom: 10px;

  }

  ul#box li a {
    color:brown;
  }

  ul#box li a:hover {
    color:crimson;
  }

</style>