<template>
  <v-container>
    <v-row alignment="center" justify="center">
      <v-col cols="12" sm="8">
        <v-text-field label="검색어를 입력하세요" v-model="q" @keyup.enter="search"></v-text-field>

        <div class="blog-card" v-for="post in searchList" :key="post.pid">
          <div class="meta">
            <div @mouseover="getTags(post.pid)" class="photo">
              <img :src="post.thumbnail" align="center" />
            </div>
            <ul class="details">
              <li class="author">{{post.nickname}}</li>
              <li class="date">{{post.created}}</li>
              <li class="tags">
                <ul>
                  <li v-for="tag in tags" :key="tag.tagid">
                    <a href="#">{{tag}}</a>
                  </li>
                  <!-- <li>
                    <a href="#">Learn</a>
                  </li>
                  <li>
                    <a href="#">Code</a>
                  </li>
                  <li>
                    <a href="#">HTML</a>
                  </li>
                  <li>
                    <a href="#">CSS</a>
                  </li>-->
                </ul>
              </li>
            </ul>
          </div>
          <div class="description">
            <h1>{{post.title}}</h1>
            <p>{{post.description}}</p>
            <p class="read-more">
              <a @click="readPost(post.pid)">Read More</a>
            </p>
          </div>
        </div>

        <!-- <v-card
          class="mx-auto"
          outlined
          v-for="post in searchList"
          :key="post.pid"
          @click="readPost(post.pid)"
          max-height="250px"
        >
          <v-card-text>
            <p class="postNickName">{{ post.nickname }}</p>
            <p class="display-1 text--primary">
              {{ post.title }}
            </p>
            <div class="postContent text--primary">
              {{ post.content }}
            </div>
            <br />
            <p class="postUpdated">{{ post.updated }}</p>
          </v-card-text>
        </v-card>-->
        <div
          v-infinite-scroll="loadMore"
          infinite-scroll-disabled="busy"
          infinite-scroll-distance="10"
        ></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "../lib/axios-common.js"; //axios : 서버와 통신하기 위함
import infiniteScroll from "vue-infinite-scroll";

export default {
  created() {
    //this.tags;
  },
  data() {
    return {
      q: "", //검색어
      searchList: [],
      page: 1,
      tags: []
    };
  },
  directives: { infiniteScroll },
  methods: {
    search() {
      this.page = 1;

      if (this.q != "") {
        axios
          .get("/v1/post/search", {
            params: {
              q: this.q,
              page: 1
            }
          })
          .then(response => {
            this.searchList = response.data;
            this.page += 1;
          })
          .catch(exp => alert("검색 실패" + exp));
      }
    },
    readPost(pid) {
      this.$store.dispatch("showMyDetail", pid);
    },
    loadMore() {
      this.busy = true;

      if (this.q != "") {
        axios
          .get("/v1/post/search", {
            params: {
              q: this.q,
              page: this.page
            }
          })
          .then(response => {
            setTimeout(() => {
              if (response.data.length > 0) {
                this.searchList = this.searchList.concat(response.data);
                this.page += 1;
              }
            }, 700);
          })
          .catch(exp => alert("로딩 실패" + exp));
        this.busy = false;
      }
    },
    async getTags(pid) {
      let response = await axios.get("/v1/tag/all/" + pid);
      this.tags = response.data;

      // axios.get("/v1/tag/all/" + pid).then(response => {
      //   this.tags = response.data;
      // });
    }
  }
};
</script>

<style scoped>
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
  color: #5ad67d;
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
  content: "";
  position: absolute;
  height: 5px;
  background: #5ad67d;
  width: 35px;
  top: -0.75rem;
  border-radius: 3px;
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
