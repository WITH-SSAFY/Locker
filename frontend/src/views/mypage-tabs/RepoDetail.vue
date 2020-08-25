<template>

  <div>
    <!-- 커밋 타임라인, 포스트, 언어 비율 -->
    <!-- TODO: 포스팅을 따로 보여줄지에 대한 여부 논의 -->

    <header>
      <!-- 1. 헤더 : 레포 이름, 디스크립션, 태그, 유저 이름, 리드미, 언어비율 정보 보여줌 -->
      <!-- TODO: GET /api/v1/github, 내 깃헙 레포 조회-->

      <p class="bold text-white" style="font-size: 2rem; position: fixed;">{{ curRepo.repoName }}</p>

      
      <!-- 언어 비율-->
      <div style="display:flex;flex-direction:column">
        <div style="flex:1 400px;display:flex;">
          <div style="flex:0 1 50%;margin:0 auto;padding:0 32px;min-width:320px;max-width:576px;display:flex;flex-direction:column;justify-content:space-around">
            <div>

              <div class="progress-project">
                <div
                  v-for="post in repoPost"
                  :key="post.pid"
                  class="dates"
                >
                  <div class="start label">
                    {{ post.created }}
                  </div>

                  <div class="finish label">
                    {{ post.updated }}
                  </div>

                </div>

                <!-- 언어 사용 비율 시각화 -->
                
                <div class="bar">

                  <div
                    v-for="(ratio, lang, idx) in langRatio"
                    :key="idx"
                    class='phase'
                    :class="'phase-' + (idx+1)"
                    :data-phase="lang"
                    :style="{ width: ratio + '%' }"
                    title="More phase info">
                  </div>
                  
                  <div class="current-state label" :class="'phase-' + (idx+1)" :style="{ width : ratio*(idx+1) + '%'} ">{{ lang }}</div>

                  <!-- <div :style="{ width : ratio*(idx+1) + '%'} ">{{ ratio }}</div> -->
                </div>

              </div>
            </div>
          </div>
        </div> 
      </div>

    </header>
  

    <!-- 레포에 걸린 포스트 정보 -->
    <!-- <div style="background-color: white; margin: 10px;" v-for="(post, index) in repoPost" :key="post.pid">
      <p>{{ index }}. {{ post.created }} / {{ post.title }} / {{ post.nickname }}</p>
    </div> -->


    <!-- 2. 타임라인 영역 : 시간 순 -->
    <div class="row">
      <div class="col-md-6">
        <section id="cd-timeline" class="cd-container">

          <!-- 커밋 정보 -->
          <!-- <div style="background-color: white; margin: 10px;" v-for="(commit, index) in commitList" :key="commit.message">
            <p>{{ index }}. {{ commit.date }} / {{ commit.message }} / {{ commit.url }}</p>
          </div> -->

          <!-- 타임라인 블럭 -->
          <!-- TODO: 레포 아이디를 기준으로 created 또는 updated 순으로 커밋 내역과 포스트 정보를 주는 API 필요 -->
          <!-- <div class="cd-timeline-block">
            
            <div class="cd-timeline-img cd-picture">
              <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-picture.svg" alt="Picture">
            </div>
      
            <div class="cd-timeline-content">
              <h2>커밋 메세지 어쩌구 저쩌구 </h2>
              <p>넣을게 있다면 더 넣고오오 뭘 넣으면 좋을까??</p>
              <a href="#0" class="cd-read-more">Read more 여기에 해당 커밋 코드 링크걸자</a>
              <span class="cd-date">Jan 14 날짜를 깃헙보니까 updated 5 days ago 이런식으로 직관적으로했는데 뭐가 좋을까?</span>
            </div>
          </div> -->
          <!-- 타임라인 블럭 끝 -->

          <!-- 하나의 타임라인 -->
          <div class="cd-timeline-block" v-for="ele in timeline" :key="ele">
            <!-- commit인 경우 -->
            <div v-if="ele.url.substring(0,19) === 'https://github.com/'">
              <div class="cd-timeline-img cd-picture">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-picture.svg" alt="Picture">
              </div> <!-- cd-timeline-img -->
              
              <!-- {{timeline[0].url.substring(0,19)}} -->
              <!-- {{timeline[0]}} -->
        
              <div class="cd-timeline-content">
                <h2>{{ele.message}}</h2>
                <p>{{ele.date}}</p>
                <!-- <span class="cd-date">{{ele.date}}</span> -->
                <a @click="openCommit(ele.url)" class="cd-read-more">Read more - commit 코드 확인하기</a>
              <!-- <span class="cd-date">Jan 14 날짜를 깃헙보니까 updated 5 days ago 이런식으로 직관적으로했는데 뭐가 좋을까?</span> -->
              </div> <!-- cd-timeline-content -->
            </div>

            <!-- 포스트인 경우 -->
            <div v-else>
              <div class="cd-timeline-img cd-movie">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/148866/cd-icon-movie.svg" alt="Movie">
              </div> <!-- cd-timeline-img -->
        
              <div class="cd-timeline-content">
                <h2>{{ele.message}}</h2>
                <p>{{ele.date}}</p> 
                <img :src="ele.thumbnail" style="height:200px;">
                <a :href="ele.url" class="cd-read-more">Read more</a>
                <!-- <span class="cd-date">{{ele.date}}</span> -->
              </div> <!-- cd-timeline-content -->
            </div>
          
          </div> <!-- cd-timeline-block -->
      
        </section>
      </div>




      <!-- 3. 포스트 내역 : 시간 역순 -->
      <!-- TODO: GET /api/v1/post/all/list/repo, 레포에 대한 포스트 내역 조회 -->
      <div class="col-md-6">
        <div>
          <v-row class="justify-content-center">
            <v-col
              cols="12"
              md="11"
            >

              <!-- post list -->
              <div class="blog-card" v-for="post in repoPost" :key="post.pid">
                <div class="meta">

                  <!-- hover -->
                  <div @mouseover="getTags(post.pid)" class="photo">
                    <img :src="post.thumbnail" align="center" />
                  </div>
                  <ul class="details">
                    <li class="author medium">인덱스 : {{ index }}</li>
                    <li class="author medium">작성자 : {{ post.nickname }}</li>
                    <li class="date medium">작성일자 : {{ post.created }}</li>
                    <li class="tags">
        
                      <p class="medium">관련 태그</p>
                      <v-chip
                        v-for="tag in tags"
                        :key="tag.tagid"
                        class="mr-1"
                        label
                        color="#eceffc"
                      >
                        <span class="medium">{{ tag }}</span>
                      </v-chip>
                    </li>
                  </ul>

                </div>

                <!-- 텍스트 섹션 -->
                <div @click="viewPost(post.pid)" class="description" style="cursor: pointer;">
                  <p class="medium mb-2" style="font-size: 1.5rem;">{{ post.title }}</p>
                  <div class="under-line"></div>
                  <p class="regular" style="font-size: 0.9rem;">{{ post.description }}</p>
                </div>

              </div>

            </v-col>
          </v-row>
          
        </div>
      </div>


    </div>
  </div>


</template>
<script>
import { mapState, mapActions } from "vuex";
import $ from 'jquery'
import axios from "../../lib/axios-common.js"
import("../../assets/css/linguist.css");

export default { 
  data() {
    return {
      tags: [],
      timelineInfo: [],
    }
  },
  methods: {
    ...mapActions(["getRepoDetail"]),
    async getTags(pid) {
      let response = await axios.get("/v1/tag/all/" + pid);
      this.tags = response.data;
    },
     viewPost(pid) {
      //포스트 상세보기로 이동
      this.$store.dispatch("showMyDetail", pid);
    },
    openCommit(url){
      window.open(url);
    },   

  },
  computed: {
    ...mapState(["timeline", "langRatio", "repoPost", "curRepo"])
  },
  created () {
    console.log("timeline: ", this.timeline);
    this.timelineInfo = this.timeline
    console.log("langRatio: ", this.langRatio);
    console.log("repoPost: ", this.repoPost);
    console.log("curRepo: ", this.curRepo);
    this.getRepoDetail(this.curRepo);
    // console.log("timeline[0] : ", this.timeline[0]);
  },
  mounted () {
    
    $(document).ready(function($){
      var $timeline_block = $('.cd-timeline-block');

      //hide timeline blocks which are outside the viewport
      $timeline_block.each(function(){
        if($(this).offset().top > $(window).scrollTop()+$(window).height()*0.75) {
          $(this).find('.cd-timeline-img, .cd-timeline-content').addClass('is-hidden');
        }
      });

      //on scolling, show/animate timeline blocks when enter the viewport
      $(window).on('scroll', function(){
        $timeline_block.each(function(){
          if( $(this).offset().top <= $(window).scrollTop()+$(window).height()*0.75 && $(this).find('.cd-timeline-img').hasClass('is-hidden') ) {
            $(this).find('.cd-timeline-img, .cd-timeline-content').removeClass('is-hidden').addClass('bounce-in');
          }
        });
      });
    });
  },
}
</script>

<style scoped>
html * {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

*, *:after, *:before {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

body {
  font-size: 100%;
  font-family: "Droid Serif", serif;
  color: #7f8c97;
  background-color: #e9f0f5;
}

a {
  color: #acb7c0;
  text-decoration: none;
  font-family: "Open Sans", sans-serif;
}

img {
  max-width: 100%;
}

h1, h2 {
  font-family: "Open Sans", sans-serif;
  font-weight: bold;
}

/* -------------------------------- 

Modules - reusable parts of our design

-------------------------------- */
.cd-container {
  /* this class is used to give a max-width to the element it is applied to, and center it horizontally when it reaches that max-width */
  width: 100%;
  max-width: 1170px;
  /* margin: 0 auto; */
}
.cd-container::after {
  /* clearfix */
  content: '';
  display: table;
  clear: both;
}

/* -------------------------------- 

Main components 

-------------------------------- */
header {
  height: 300px;
  line-height: 300px;
  z-index: -1;
  text-align: center;
  background: #242b33;
}
header h1 {
  color: #ffffff;
  font-size: 18px;
  font-size: 1.125rem;
}
@media only screen and (min-width: 1170px) {
  header {
    height: 300px;
    line-height: 300px;
  }
  header h1 {
    font-size: 24px;
    font-size: 1.5rem;
  }
}

#cd-timeline {
  position: relative;
  padding: 2em 0;
  margin-top: 2em;
  margin-bottom: 2em;
}
#cd-timeline::before {
  /* this is the vertical line */
  content: '';
  position: absolute;
  top: 0;
  left: 18px;
  height: 100%;
  width: 4px;
  background: #d7e4ed;
}
@media only screen and (min-width: 1170px) {
  #cd-timeline {
    margin-top: 3em;
    margin-bottom: 3em;
  }
  #cd-timeline::before {
    left: 50%;
    margin-left: -2px;
  }
}

.cd-timeline-block {
  position: relative;
  margin: 2em 0;
}
.cd-timeline-block::after {
  clear: both;
  content: "";
  display: table;
}
.cd-timeline-block:first-child {
  margin-top: 0;
}
.cd-timeline-block:last-child {
  margin-bottom: 0;
}
@media only screen and (min-width: 1170px) {
  .cd-timeline-block {
    margin: 4em 0;
  }
  .cd-timeline-block:first-child {
    margin-top: 0;
  }
  .cd-timeline-block:last-child {
    margin-bottom: 0;
  }
}

.cd-timeline-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  box-shadow: 0 0 0 4px #ffffff, inset 0 2px 0 rgba(0, 0, 0, 0.08), 0 3px 0 4px rgba(0, 0, 0, 0.05);
}
.cd-timeline-img img {
  display: block;
  width: 24px;
  height: 24px;
  position: relative;
  left: 50%;
  top: 50%;
  margin-left: -12px;
  margin-top: -12px;
}
.cd-timeline-img.cd-picture {
  background: #75ce66;
}
.cd-timeline-img.cd-movie {
  background: #c03b44;
}
.cd-timeline-img.cd-location {
  background: #f0ca45;
}
@media only screen and (min-width: 1170px) {
  .cd-timeline-img {
    width: 60px;
    height: 60px;
    left: 50%;
    margin-left: -30px;
    /* Force Hardware Acceleration in WebKit */
    -webkit-transform: translateZ(0);
    -webkit-backface-visibility: hidden;
  }
  .cssanimations .cd-timeline-img.is-hidden {
    visibility: hidden;
  }
  .cssanimations .cd-timeline-img.bounce-in {
    visibility: visible;
    -webkit-animation: cd-bounce-1 0.6s;
    -moz-animation: cd-bounce-1 0.6s;
    animation: cd-bounce-1 0.6s;
  }
}

@-webkit-keyframes cd-bounce-1 {
  0% {
    opacity: 0;
    -webkit-transform: scale(0.5);
  }
  60% {
    opacity: 1;
    -webkit-transform: scale(1.2);
  }
  100% {
    -webkit-transform: scale(1);
  }
}
@-moz-keyframes cd-bounce-1 {
  0% {
    opacity: 0;
    -moz-transform: scale(0.5);
  }
  60% {
    opacity: 1;
    -moz-transform: scale(1.2);
  }
  100% {
    -moz-transform: scale(1);
  }
}
@keyframes cd-bounce-1 {
  0% {
    opacity: 0;
    -webkit-transform: scale(0.5);
    -moz-transform: scale(0.5);
    -ms-transform: scale(0.5);
    -o-transform: scale(0.5);
    transform: scale(0.5);
  }
  60% {
    opacity: 1;
    -webkit-transform: scale(1.2);
    -moz-transform: scale(1.2);
    -ms-transform: scale(1.2);
    -o-transform: scale(1.2);
    transform: scale(1.2);
  }
  100% {
    -webkit-transform: scale(1);
    -moz-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1);
  }
}
.cd-timeline-content {
  position: relative;
  margin-left: 60px;
  background: #ffffff;
  border-radius: 0.25em;
  padding: 1em;
  box-shadow: 0 3px 0 #d7e4ed;
}
.cd-timeline-content::after {
  clear: both;
  content: "";
  display: table;
}
.cd-timeline-content h2 {
  color: #303e49;
}
.cd-timeline-content p, .cd-timeline-content .cd-read-more, .cd-timeline-content .cd-date {
  font-size: 13px;
  font-size: 0.8125rem;
}
.cd-timeline-content .cd-read-more, .cd-timeline-content .cd-date {
  display: inline-block;
}
.cd-timeline-content p {
  margin: 1em 0;
  line-height: 1.6;
}
.cd-timeline-content .cd-read-more {
  float: right;
  padding: .8em 1em;
  background: #acb7c0;
  color: #ffffff;
  border-radius: 0.25em;
}
.no-touch .cd-timeline-content .cd-read-more:hover {
  background-color: #bac4cb;
}
.cd-timeline-content .cd-date {
  float: left;
  padding: .8em 0;
  opacity: .7;
}
.cd-timeline-content::before {
  content: '';
  position: absolute;
  top: 16px;
  right: 100%;
  height: 0;
  width: 0;
  border: 7px solid transparent;
  border-right: 7px solid #ffffff;
}
@media only screen and (min-width: 768px) {
  .cd-timeline-content h2 {
    font-size: 20px;
    font-size: 1.25rem;
  }
  .cd-timeline-content p {
    font-size: 16px;
    font-size: 1rem;
  }
  .cd-timeline-content .cd-read-more, .cd-timeline-content .cd-date {
    font-size: 14px;
    font-size: 0.875rem;
  }
}
@media only screen and (min-width: 1170px) {
  .cd-timeline-content {
    margin-left: 0;
    padding: 1.6em;
    width: 45%;
  }
  .cd-timeline-content::before {
    top: 24px;
    left: 100%;
    border-color: transparent;
    border-left-color: #ffffff;
  }
  .cd-timeline-content .cd-read-more {
    float: left;
  }
  .cd-timeline-content .cd-date {
    position: absolute;
    width: 100%;
    left: 122%;
    top: 6px;
    font-size: 16px;
    font-size: 1rem;
  }
  .cd-timeline-block:nth-child(even) .cd-timeline-content {
    float: right;
  }
  .cd-timeline-block:nth-child(even) .cd-timeline-content::before {
    top: 24px;
    left: auto;
    right: 100%;
    border-color: transparent;
    border-right-color: #ffffff;
  }
  .cd-timeline-block:nth-child(even) .cd-timeline-content .cd-read-more {
    float: right;
  }
  .cd-timeline-block:nth-child(even) .cd-timeline-content .cd-date {
    left: auto;
    right: 122%;
    text-align: right;
  }
  .cssanimations .cd-timeline-content.is-hidden {
    visibility: hidden;
  }
  .cssanimations .cd-timeline-content.bounce-in {
    visibility: visible;
    -webkit-animation: cd-bounce-2 0.6s;
    -moz-animation: cd-bounce-2 0.6s;
    animation: cd-bounce-2 0.6s;
  }
}

@media only screen and (min-width: 1170px) {
  /* inverse bounce effect on even content blocks */
  .cssanimations .cd-timeline-block:nth-child(even) .cd-timeline-content.bounce-in {
    -webkit-animation: cd-bounce-2-inverse 0.6s;
    -moz-animation: cd-bounce-2-inverse 0.6s;
    animation: cd-bounce-2-inverse 0.6s;
  }
}
@-webkit-keyframes cd-bounce-2 {
  0% {
    opacity: 0;
    -webkit-transform: translateX(-100px);
  }
  60% {
    opacity: 1;
    -webkit-transform: translateX(20px);
  }
  100% {
    -webkit-transform: translateX(0);
  }
}
@-moz-keyframes cd-bounce-2 {
  0% {
    opacity: 0;
    -moz-transform: translateX(-100px);
  }
  60% {
    opacity: 1;
    -moz-transform: translateX(20px);
  }
  100% {
    -moz-transform: translateX(0);
  }
}
@keyframes cd-bounce-2 {
  0% {
    opacity: 0;
    -webkit-transform: translateX(-100px);
    -moz-transform: translateX(-100px);
    -ms-transform: translateX(-100px);
    -o-transform: translateX(-100px);
    transform: translateX(-100px);
  }
  60% {
    opacity: 1;
    -webkit-transform: translateX(20px);
    -moz-transform: translateX(20px);
    -ms-transform: translateX(20px);
    -o-transform: translateX(20px);
    transform: translateX(20px);
  }
  100% {
    -webkit-transform: translateX(0);
    -moz-transform: translateX(0);
    -ms-transform: translateX(0);
    -o-transform: translateX(0);
    transform: translateX(0);
  }
}
@-webkit-keyframes cd-bounce-2-inverse {
  0% {
    opacity: 0;
    -webkit-transform: translateX(100px);
  }
  60% {
    opacity: 1;
    -webkit-transform: translateX(-20px);
  }
  100% {
    -webkit-transform: translateX(0);
  }
}
@-moz-keyframes cd-bounce-2-inverse {
  0% {
    opacity: 0;
    -moz-transform: translateX(100px);
  }
  60% {
    opacity: 1;
    -moz-transform: translateX(-20px);
  }
  100% {
    -moz-transform: translateX(0);
  }
}
@keyframes cd-bounce-2-inverse {
  0% {
    opacity: 0;
    -webkit-transform: translateX(100px);
    -moz-transform: translateX(100px);
    -ms-transform: translateX(100px);
    -o-transform: translateX(100px);
    transform: translateX(100px);
  }
  60% {
    opacity: 1;
    -webkit-transform: translateX(-20px);
    -moz-transform: translateX(-20px);
    -ms-transform: translateX(-20px);
    -o-transform: translateX(-20px);
    transform: translateX(-20px);
  }
  100% {
    -webkit-transform: translateX(0);
    -moz-transform: translateX(0);
    -ms-transform: translateX(0);
    -o-transform: translateX(0);
    transform: translateX(0);
  }
}

.postContent {
  text-overflow: ellipsis;
}
.mx-auto {
  margin-bottom: 20px;
}
.blog-card {
  display: -webkit-box;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  margin: 1rem auto;
  box-shadow: 0 3px 7px -1px rgba(0, 0, 0, 0.1);
  margin-bottom: 1.6%;
  background: #fff;
  line-height: 1.4;
  font-family: sans-serif;
  border-radius: 5px;
  overflow: hidden;
  z-index: 0;
}
.blog-card a {
  color: inherit;
}
.blog-card a:hover {
  color: #5ad67d;
}
.blog-card:hover .photo {
  -webkit-transform: scale(1.3) rotate(3deg);
  transform: scale(1.3) rotate(3deg);
}
.blog-card .meta {
  position: relative;
  z-index: 0;
  height: 200px;
}
.blog-card .photo {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-size: cover;
  background-position: center;
  -webkit-transition: -webkit-transform 0.2s;
  transition: -webkit-transform 0.2s;
  transition: transform 0.2s;
  transition: transform 0.2s, -webkit-transform 0.2s;
}
.blog-card .details,
.blog-card .details ul {
  margin: auto;
  padding: 0;
  list-style: none;
}
.blog-card .details {
  position: absolute;
  top: 0;
  bottom: 0;
  left: -100%;
  margin: auto;
  -webkit-transition: left 0.2s;
  transition: left 0.2s;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  padding: 10px;
  width: 100%;
  font-size: 0.9rem;
}
.blog-card .details a {
  -webkit-text-decoration: dotted underline;
  text-decoration: dotted underline;
}
.blog-card .details ul li {
  display: inline-block;
}
.blog-card .details .author:before {
  font-family: FontAwesome;
  margin-right: 10px;
  content: "\f007";
}
.blog-card .details .date:before {
  font-family: FontAwesome;
  margin-right: 10px;
  content: "\f133";
}
.blog-card .details .tags ul:before {
  font-family: FontAwesome;
  content: "\f02b";
  margin-right: 10px;
}
.blog-card .details .tags li {
  margin-right: 2px;
}
.blog-card .details .tags li:first-child {
  margin-left: -4px;
}
.blog-card .description {
  padding: 1rem;
  background: #fff;
  position: relative;
  z-index: 1;
}
.blog-card .description h1,
.blog-card .description h2 {
  font-family: Poppins, sans-serif;
}
.blog-card .description h1 {
  line-height: 1;
  margin: 0;
  font-size: 1.7rem;
}
.blog-card .description h2 {
  font-size: 1rem;
  font-weight: 300;
  text-transform: uppercase;
  color: #a2a2a2;
  margin-top: 5px;
}
.blog-card .description .read-more {
  text-align: right;
}
.blog-card .description .read-more a {
  color: #7C4DFF;
  display: inline-block;
  position: relative;
}
.blog-card .description .read-more a:after {
  content: "\f061";
  font-family: FontAwesome;
  margin-left: -10px;
  opacity: 0;
  vertical-align: middle;
  -webkit-transition: margin 0.3s, opacity 0.3s;
  transition: margin 0.3s, opacity 0.3s;
}
.blog-card .description .read-more a:hover:after {
  margin-left: 5px;
  opacity: 1;
}
.blog-card p {
  position: relative;
  margin: 1rem 0 0;
}
.blog-card p:first-of-type {
  margin-top: 1.25rem;
}
.blog-card:hover .details {
  left: 0%;
}
@media (min-width: 640px) {
  .blog-card {
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    flex-direction: row;
    max-width: 700px;
  }
  .blog-card .meta {
    flex-basis: 40%;
    height: auto;
  }
  .blog-card .description {
    flex-basis: 60%;
  }
  .blog-card .description:before {
    -webkit-transform: skewX(-3deg);
    transform: skewX(-3deg);
    content: "";
    background: #fff;
    width: 30px;
    position: absolute;
    left: -10px;
    top: 0;
    bottom: 0;
    z-index: -1;
  }
  .blog-card.alt {
    -webkit-box-orient: horizontal;
    -webkit-box-direction: reverse;
    flex-direction: row-reverse;
  }
  .blog-card.alt .description:before {
    left: inherit;
    right: -10px;
    -webkit-transform: skew(3deg);
    transform: skew(3deg);
  }
  .blog-card.alt .details {
    padding-left: 25px;
  }
}



</style>