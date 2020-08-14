<template>
  <div class="d-flex justify-content-center mb-5" style="margin: 5rem 0;">

    <a href="#" id="raw-switch" class="raw-switch">Raw: <span></span></a>
    <aside id="markdown" contenteditable>
``` java

int a = 1

```

# 바보
    </aside>
    <section id="output-html" class="markdown-body"></section>
    <!-- <viewer v-if="axiosFlag" :initialValue="intro" height="100%" /> -->
  </div>
</template>

<script>
import axios from "../../lib/axios-common.js";
import '@/assets/text_editor/md.css'
import '@/assets/text_editor/github-md.css'
// import { Viewer } from '@toast-ui/vue-editor';
// import '@toast-ui/editor/dist/toastui-editor-viewer.css';

export default {
  name: 'introcduce',
  data() {
    return {
      intro: '',
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
      // let token = localStorage.getItem("github_token");
      // test
      let accessToken = '055bcf6a87cfd6b760b67a2150cfb5948b5b9385';
      axios.get("/v1/github/hidden?accessToken=" + accessToken)
        .then((response) => {
          this.intro = response.data.data
          this.axiosFlag = true
        })
        .catch((exp) => alert("소개글 불러오기 실패" + exp))
    },
  },

  mounted () {
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

    var rawMode = true;
    var mdEl = document.querySelector('#markdown')
    var outputEl = document.querySelector('#output-html')
    var parse = function(){
      outputEl[rawMode ? "innerText" : "innerHTML"] = parseMd(mdEl.innerText);
      };

    parse();
    mdEl.addEventListener('keyup', parse, false);

    // Raw mode trigger btn
    (function(){

      var trigger = document.getElementById('raw-switch'),
          status = trigger.getElementsByTagName('span')[0],
          updateStatus = function(){
            status.innerText = rawMode ? 'On' : 'Off';
          };

      updateStatus();
      trigger.addEventListener('click', function(e){
        e.preventDefault();
        rawMode = rawMode ? false : true;
        updateStatus();
        parse();
      }, false);
    }());

    // hljs.initHighlightingOnLoad();
    hljs.highlightBlock(code);

  },
}
</script>

<style>

</style>