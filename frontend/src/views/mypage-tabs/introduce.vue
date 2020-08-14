<template>
  <div class="d-flex justify-content-center mb-5" style="margin: 5rem 0;">
    <aside id="markdown" contenteditable style="display: none;">{{ intro }}</aside>
    <section id="output-html" class="markdown-body" style="display: none;"></section>
    <div id="page" class="markdown-body width: 75%;"></div>
  </div>
</template>

<script>
import axios from "../../lib/axios-common.js";
// import '@/assets/text_editor/md.css'
import '@/assets/text_editor/github-md.css'

export default {
  name: 'introduce',
  data() {
    return {
      intro: null,
      axiosFlag: false,
    }
  },
  computed: {

  },
  components: {
    // Viewer,
  },
  methods: {
    // access token을 로컬스토리지에서 가져와서 넘겨주기
    getIntro () {
      // let accessToken = localStorage.getItem("github_token");
      // test
      let accessToken = '0f585a129c21cb9c8e71e5e7a36a076eee99498d';
      axios.get("/v1/github/hidden?accessToken=" + accessToken)
        .then((response) => {
          this.intro = response.data.data
          this.axiosFlag = true
        })
        .catch((exp) => alert("소개글 불러오기 실패" + exp))
    },
  },
  created () {
    this.getIntro();

  },
  mounted () {
    // hljs.initHighlightingOnLoad();
    // hljs.highlightBlock(code);
  },

  updated () {
    function parseMd(md){

      //ul
      md = md.replace(/^\s*\n\*/gm, '<ul>\n*');
      md = md.replace(/^(\*.+)\s*\n([^\*])/gm, '$1\n</ul>\n\n$2');
      md = md.replace(/^\*(.+)/gm, '<li>$1</li>');
      
      //ol
      md = md.replace(/^\s*\n\d\./gm, '<ol>\n1.');
      md = md.replace(/^(\d\..+)\s*\n([^\d\.])/gm, '$1\n</ol>\n\n$2');
      md = md.replace(/^\d\.(.+)/gm, '<li>$1</li>');
      
      //blockquote
      md = md.replace(/^\>(.+)/gm, '<blockquote>$1</blockquote>');
      
      //h
      md = md.replace(/[\#]{6}(.+)/g, '<h6>$1</h6>');
      md = md.replace(/[\#]{5}(.+)/g, '<h5>$1</h5>');
      md = md.replace(/[\#]{4}(.+)/g, '<h4>$1</h4>');
      md = md.replace(/[\#]{3}(.+)/g, '<h3>$1</h3>');
      md = md.replace(/[\#]{2}(.+)/g, '<h2>$1</h2>');
      md = md.replace(/[\#]{1}(.+)/g, '<h1>$1</h1>');
      
      //alt h
      md = md.replace(/^(.+)\n\=+/gm, '<h1>$1</h1>');
      md = md.replace(/^(.+)\n\-+/gm, '<h2>$1</h2>');
      
      //images
      md = md.replace(/\!\[([^\]]+)\]\(([^\)]+)\)/g, '<br><br><img src="$2" alt="$1" style="display: table; margin-left: auto; margin-right: auto;"/><br>');
      
      //links
      md = md.replace(/[\[]{1}([^\]]+)[\]]{1}[\(]{1}([^\)\"]+)(\"(.+)\")?[\)]{1}/g, '<a href="$2" title="$4">$1</a>');
      
      //font styles
      md = md.replace(/[\*\_]{2}([^\*\_]+)[\*\_]{2}/g, '<b>$1</b>');
      md = md.replace(/[\*\_]{1}([^\*\_]+)[\*\_]{1}/g, '<i>$1</i>');
      md = md.replace(/[\~]{2}([^\~]+)[\~]{2}/g, '<del>$1</del>');
      
      //pre
      md = md.replace(/^\s\`\`\` (([^\s]+))?\n/gm, '<pre><code class="$2">');

      md = md.replace(/^\`\`\`\s*/gm, '</code></pre>\n\n');
      
      //code
      md = md.replace(/[\`]{1}([^\`]+)[\`]{1}\n/g, '<code>$1</code>');
      
      return md;
      
    }

    var mdEl = document.querySelector('#markdown')
    var outputEl = document.querySelector('#output-html')
    var parse = function(){
      outputEl["innerText"] = parseMd(mdEl.innerText);
      };

    parse();
    document.querySelector('#page').innerHTML = document.querySelector('#output-html').innerText

  },

}

</script>

<style>

</style>