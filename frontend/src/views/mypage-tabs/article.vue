<template>
  <div class="container p-5">

    <div style="margin-left: 3.5rem; padding-top: 5rem;">
      <v-icon small color="white">mdi-chevron-right</v-icon>
      <p class="medium d-inline text-white" style="font-size: 1.5rem;">포스트</p>
      <p class="light text-white ml-4" style="font-size: 1.1rem;">내가 작성한 포스트를 모아서 볼 수 있어요</p>
    </div>

    <v-row class="justify-content-center">
      <v-col
        cols="12"
        md="11"
      >

        <!-- post list -->
        <div class="blog-card" v-for="post in myPostList" :key="post.pid">
          <div class="meta">

            <!-- hover -->
            <div @mouseover="getTags(myPost.pid)" class="photo">
              <img :src="post.thumbnail" align="center" />
            </div>
            <ul class="details">
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
          <div @click="showMyDetail(post.pid)" class="description" style="cursor: pointer;">
            <p class="medium mb-2" style="font-size: 1.5rem;">{{ post.title }}</p>
            <div class="under-line"></div>
            <p class="regular" style="font-size: 0.9rem;">{{ post.description }}</p>
          </div>

        </div>

      </v-col>
    </v-row>

  </div>
</template>

<script>
import { mapState } from "vuex"
import axios from "../../lib/axios-common.js"

export default {
  name: 'article',
  components: {

  },
  data() {
    return {
      tags: [],
    }
  },
  computed: {
    ...mapState(["userInfo","myPostList"]),
  },
  methods: {
    showMyDetail (pid) {
      this.$store.dispatch('showMyDetail', pid);
      //this.$router.push({name: "readPost"});
    },
    async getTags(pid) {
      let response = await axios.get("/v1/tag/all/" + pid);
      this.tags = response.data;
    }
  },
  props: {

  },
}
</script>

<style scoped>

  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7c4dff;
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
  .blog-card p:first-of-type:before {
    /* content: "";
    position: absolute;
    height: 4.5px;
    background: #7C4DFF;
    width: 55px;
    top: -0.75rem; */
    /* border-radius: 3px; */
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