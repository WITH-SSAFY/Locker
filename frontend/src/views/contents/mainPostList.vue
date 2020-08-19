<template>
  <div style="margin-top: 3rem; background-color: #222;">

    <div class="row">
      <div class="col-md-4">

        <v-flex class="logo" style="margin-left: 7rem;">
          <p class="bolder" style="font-size: 5rem; color: #7C4DFF">LOCKER</p>
        </v-flex>

        <div style="margin-left: 6.5rem; margin-top: 5rem;">
          <v-icon color="#7C4DFF">mdi-chevron-right</v-icon>
          <p class="regular d-inline ml-2" style="font-size: 1.7rem; color: #999">공지사항</p>
        </div>
        <section id="container">
          <ul id="box" class="regular" style="font-size: 1.1rem;">
            <li class="medium"><router-link to="/notice1">[공지사항] - Locker blog 서비스 시작</router-link></li>
            <li class="medium"><router-link to="/notice2">[공지사항] - Ver 1.01 업데이트</router-link></li>
            <li class="medium"><router-link to="/notice3">[공지사항] - Locker 향후 업데이트 방향</router-link></li>
          </ul>
          <router-view></router-view>
        </section>

        <div class="float-right" style="margin-top: 5rem;">
          <v-icon color="#7C4DFF">mdi-chevron-right</v-icon>
          <p class="regular d-inline ml-2" style="font-size: 1.7rem; color: #999">이 달의 레포지토리</p>
        </div>
        
        <div style="margin-left: 6.5rem; margin-top: 12rem;">
          <v-icon color="#7C4DFF">mdi-chevron-down</v-icon>
          <p class="regular d-inline ml-2" style="font-size: 1.7rem; color: #999">유저의 좋아요 컨텐츠</p>
        </div>

      </div>
      <div class="col-md-8 mt-5">

        <div class="cont s--inactive">
          <div class="cont__inner">

            <!-- el start -->
            <!-- likes, name, repoId, repoName, usrId -->
            <div>
              <div
                class="el"
                v-for="(repo, i) in repoList"
                @click="openAni($event, i)"
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
                      <div class="el__text">{{ repo.repoName }}</div>
                      <div class="el__text">{{ repo.name }}</div>
                      <v-icon color="#424242" class="ml-auto">mdi-heart</v-icon>
                      <span class="text-white ml-1 medium" style="font-size: 0.9rem; color: #424242;">{{ repo.likes }}</span>
                      <div class="el__close-btn" @click="closeAni($btn, i)"></div>
                    </div>

                  </div>
                </div>

                <div class="el__index">
                  <div class="el__index-back">{{ i + 1 }}</div>
                  <div class="el__index-front">
                    <div class="el__index-overlay" :data-index="i + 1">{{ i + 1 }}</div>
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
        })
        .catch(exp => alert("핫 레포 가져오기 실패" + exp))
    },

    // mounted에서는 실행하고, methods에서는 정의하고
    openAni: function (ev, i) {
      console.log(ev, i)
      var $cont = document.querySelector('.cont');
      var $elsArr = [].slice.call(document.querySelectorAll('.el'));

      $elsArr.forEach(function($el, i) {
        $el.addEventListener('click', function() {
          console.log('해줘어', $el, i)
          if (this.classList.contains('s--active')) return;
          $cont.classList.add('s--el-active');
          this.classList.add('s--active');
      });
    });
    },

    // mounted에서는 실행하고, methods에서는 정의하고
    closeAni: function (btn, i) {
      console.log(btn, i)
      var $cont = document.querySelector('.cont');
      var $closeBtnsArr = [].slice.call(document.querySelectorAll('.el__close-btn'));

      $closeBtnsArr.forEach(function($btn) {
        $btn.addEventListener('click', function(e) {
          e.stopPropagation();
          $cont.classList.remove('s--el-active');
          document.querySelector('.el.s--active').classList.remove('s--active');
        });
      });

    },
  },

  created() {
    this.hotRepo();
  },

  mounted() {
    var $cont = document.querySelector('.cont');

    setTimeout(function() {
      $cont.classList.remove('s--inactive');
    }, 200);

    this.openAni();
    this.closeAni();

    var $box = $('ul#box li:first-child');
    (function toggleBox() {
      $box.slideToggle();
      setTimeout(function(){
        toggleBox();
      },2250);
    })();
    }
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
    width: 27.5rem;
    margin-top: 0;
    margin-left: 7rem;
    height: 6rem;
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
    text-decoration: none;
    color:brown;
  }

  ul#box li a:hover {
    text-decoration: none;
    color:crimson;
  }

</style>