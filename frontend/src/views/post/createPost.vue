<template>
  <div id="container">
    <div class="row">
      <div class="col-md-6">
        <div id="title_container">
          <v-icon small>mdi-chevron-right</v-icon>
          <p class="light d-inline" style="font-size: 1.3rem;">제목</p>
          <v-text-field autofocus solo id="title" v-model="title" placeholder="제목을 입력해주세요"></v-text-field>
        </div>

        <div id="tag_container">
          <v-icon small>mdi-chevron-right</v-icon>
          <p class="light d-inline" style="font-size: 1.3rem;">태그</p>
          <v-icon small class="d-inline px-2" color="success">mdi-check-circle</v-icon>
          <v-text-field
            solo
            id="tag"
            v-model="tag"
            placeholder="태그를 입력해주세요"
            @keyup.enter="createTag"
          ></v-text-field>
        </div>

        <div id="tag_list">
          <v-chip
            v-for="(tag, index) in tags"
            :key="index"
            class="mx-1 mb-2"
            label
            color="#eceffc"
            @click="deleteTag(index)"
          >
            {{ tag }}
            <v-icon small right color="#929292">mdi-close</v-icon>
          </v-chip>
        </div>
      </div>
      <div class="col-md-6">
        <div style="margin-left: 1.5rem;">
          <p class="bold" style="font-size: 1.3rem;">
            <span class="bolder">LOCKER</span>에 글 작성
          </p>
          <div class="under-line"></div>
          <div style="margin-top: 6.5rem;">
            <v-btn depressed id="temp_save" class="save_button" color="#eceffc">
              <span class="medium">임시저장</span>
            </v-btn>
            <v-btn
              depressed
              id="save"
              class="save_button ml-2"
              color="#7C4DFF"
              @click="postContent"
            >
              <span class="medium">작성완료</span>
            </v-btn>
          </div>
        </div>
      </div>
    </div>

    <v-icon small>mdi-chevron-right</v-icon>
    <p class="light d-inline" style="font-size: 1.3rem;">내용</p>
    <!-- 새로운 마크다운 적용 -->
    <div>
      <textarea v-model="post"></textarea>
      <aside id="markdown" contenteditable style="display: none;">{{ post }}</aside>
      <section id="output-html" class="markdown-body" style="display: none;"></section>
      <section id="page" class="markdown-body width: 75%;"></section>
    </div>
  </div>
</template>
<script>
import axios from "../../lib/axios-common.js";
import "@/assets/text_editor/github-md.css";

export default {
  components: {},
  data() {
    return {
      title: "",
      tag: "",
      tags: [], //전체 태그
      content: "",
      email: "",
      pid: 0,
      post: null
    };
  },
  methods: {
    onEditorChange() {
      //에디터의 content가 바뀌었을 때
      //자동 저장 구현(나중에)
    },
    postContent() {
      axios
        .get("/v1/post/nextpid")
        .then(response => {
          this.pid = response.data;
          this.$store.state.myPost = {
            pid: this.pid,
            title: this.title,
            email: this.$store.state.userInfo.email,
            content: this.post,
            nickname: this.$store.state.userInfo.nickname
          };

          this.$store.state.myTags = this.tags;
          this.$store.state.isNewPost = true;
          this.$router.push({ name: "afterPost" });
        })
        .catch(exp => alert("pid 가져오기 실패" + exp));
    },
    createTag() {
      //태그 생성
      //중복 확인
      let isDup = false; //중복 되었는가?

      for (let i = 0; i < this.tags.length; i++) {
        if (this.tags[i] == this.tag) {
          isDup = true;
          break;
        }
      }

      if (!isDup && this.tag != "") {
        //중복과 빈 값 인지 체크
        this.tags.push(this.tag);
        this.tag = "";
      }
    },
    deleteTag(index) {
      //태그 삭제
      this.tags.splice(index, 1); //splice(p1,p2,p3) p1: 시작 index, p2: 삭제 or 수정할 요소 개수, p3 바꿀 내용
    }
  },
  updated() {
    function parseMd(md) {
      //ul
      // md = md.replace(/^\s*\n\-\s/gm, '<ul>\n');
      // md = md.replace(/^(\-.+)\s*([^\-])/gm, '$1\n</ul>\n\n$2');
      md = md.replace(/^(\-\s)(.+)/gm, "<li>$2</li>");

      //ol
      md = md.replace(/^\s*\n\d\./gm, "<ol>\n1.");
      md = md.replace(/^(\d\..+)\s*\n([^\d\.])/gm, "$1\n</ol>\n\n$2");
      md = md.replace(/^\d\.(.+)/gm, "<li>$1</li>");

      //blockquote
      md = md.replace(/^\>(.+)/gm, "<blockquote>$1</blockquote>");

      //h
      md = md.replace(/[\#]{6}\s(.+)/g, "<h6>$1</h6>");
      md = md.replace(/[\#]{5}\s(.+)/g, "<h5>$1</h5>");
      md = md.replace(/[\#]{4}\s(.+)/g, "<h4>$1</h4>");
      md = md.replace(/[\#]{3}\s(.+)/g, "<h3>$1</h3>");
      md = md.replace(/[\#]{2}\s(.+)/g, "<h2>$1</h2>");
      md = md.replace(/[\#]{1}\s(.+)/g, "<h1>$1</h1>");

      //images
      md = md.replace(
        /\!\[([^\]]+)\]\(([^\)]+)\)/g,
        '<p><img src="$2" alt="$1" /></p>'
      );

      //links
      md = md.replace(
        /[\[]{1}([^\]]+)[\]]{1}[\(]{1}([^\)\"]+)(\"(.+)\")?[\)]{1}/g,
        '<a href="$2" title="$4">$1</a>'
      );

      //font styles
      md = md.replace(/[\*\_]{2}([^\*\_]+)[\*\_]{2}/g, "<b>$1</b>");
      md = md.replace(/[\*\_]{1}([^\*\_]+)[\*\_]{1}/g, "<i>$1</i>");
      md = md.replace(/[\~]{2}([^\~]+)[\~]{2}/g, "<del>$1</del>");

      //pre
      md = md.replace(/^\s*\`\`\`\s(([^\s]+))/gm, '<pre class="$2">');
      md = md.replace(/^\`\`\`/gm, "</pre>\n\n");

      //code
      md = md.replace(/[\`]{1}([^\`]+)[\`]{1}/g, "<code>$1</code>");

      //hr
      md = md.replace(/\-\-\-+/gm, "<hr>");

      //p
      md = md.replace(/^\s*(\n)?(.+)/gm, function(m) {
        return /\<(\/)?(h\d|ul|ol|li|blockquote|pre|img)/.test(m)
          ? m
          : "<p>" + m + "</p>";
      });

      //strip p from pre
      md = md.replace(/(\<pre.+\>)*\<p\>(.+)\<\/p\>/gm, "$1$2");

      return md;
    }

    var mdEl = document.querySelector("#markdown");
    this.content = mdEl;
    var outputEl = document.querySelector("#output-html");
    var parse = function() {
      outputEl["innerText"] = parseMd(mdEl.innerText);
    };

    parse();
    document.querySelector("#page").innerHTML = document.querySelector(
      "#output-html"
    ).innerText;
  }
};
</script>

<style scoped>
.under-line {
  height: 0.3rem;
  width: 3.5rem;
  margin-bottom: 3rem;
  background-color: #7c4dff;
}

#container {
  margin: 20px auto 0 auto;
  height: 100%;
  width: 100%;
  padding: 0 55px 0 62px;
}

#title_container {
  width: 40%;
}
#title {
  font-size: 27px;
  font-weight: 800;
}
#tag_container {
  width: 40%;
}
#tag_list {
  width: 50%;
}

#tag {
  font-size: 14px;
}

.save_button {
  margin-bottom: 20px;
  width: 80pgit chx;
}

/*md 사이즈 */
@media (max-width: 768px) {
  /* 제목 너비 크기 조정 */
  #title_container {
    width: 100%;
  }
  #tag_container {
    width: 100%;
  }
  #tag_list {
    width: 100%;
  }
}
textarea,
section {
  width: 50%;
  min-height: 60vh;
  padding: 1rem 1.5rem;
}
textarea {
  background: #f1f1f1;
  white-space: pre-wrap;
  top: 0;
  left: 0;
  bottom: 0;
  overflow-y: scroll;
  float: left;
}
textarea:focus {
  outline: none;
}
section {
  height: 100%;
  margin-left: 50%;
  overflow-y: scroll;
  overflow-x: hidden;
}
section h1 {
  font-size: 1.8em;
}
section b {
  font-weight: 700;
}
section pre {
  overflow-y: scroll;
}
</style>
