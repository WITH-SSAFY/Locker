<template>
  <div id="container">

    <div class="py-5">
      <p class="bold" style="font-size: 1.3rem;"><span class="bolder">LOCKER </span>에 글 작성</p>
      <div class="under-line"></div>
    </div>

    <div id="buttons">
      <v-btn depressed id="temp_save" class="save_button" color="#eceffc"><span class="bold">임시저장</span></v-btn>
      <v-btn depressed id="save" class="save_button" color="#7C4DFF" @click="postContent"><span class="bold">작성완료</span></v-btn>
    </div>

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
        class="mx-1"
        label
        color="#eceffc"
        @click="deleteTag(index)"
      >
        {{ tag }}
        <v-icon small right color="#929292">mdi-close</v-icon>
      </v-chip>
    </div>

    <editor
      ref="toastuiEditor"
      id="editor"
      :options="editorOptions"
      height="90%"
      previewStyle="vertical"
      @change="onEditorChange"
      class="mt-5"
    />

  </div>
</template>
<script>
// const defaultOptions = {
//   minHeight: '200px',
//   language: 'en-US',
//   useCommandShortcut: true,
//   useDefaultHTMLSanitizer: true,
//   usageStatistics: true,
//   hideModeSwitch: false,
//   toolbarItems: [
//     'heading',
//     'bold',
//     'italic',
//     'strike',
//     'divider',
//     'hr',
//     'quote',
//     'divider',
//     'ul',
//     'ol',
//     'task',
//     'indent',
//     'outdent',
//     'divider',
//     'table',
//     'image',
//     'link',
//     'divider',
//     'code',
//     'codeblock'
//   ]
//   };
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
//import 'highlight.js/styles/github.css';

import { Editor } from "@toast-ui/vue-editor";
import axios from "../../lib/axios-common.js"; //axios : 서버와 통신하기 위함

export default {
  components: {
    editor: Editor
  },
  data() {
    return {
      title: "",
      tag: "",
      tags: [], //전체 태그
      content: "",
      email: "",
      editorOptions: {
        hideModeSwitch: true, //모드 설정(markdown, wysiwyg) 안보이게함
        placeholder: "내용을 입력해주세요"
      },
      pid: 0
    };
  },
  methods: {
    onEditorChange() {
      //에디터의 content가 바뀌었을 때
      //자동 저장 구현(나중에)
    },
    postContent() {
      this.content = this.$refs.toastuiEditor.invoke("getMarkdown");
      axios
        .get("/v1/post/nextpid")
        .then(response => {
          this.pid = response.data;
          this.$store.state.myPost = {
            pid: this.pid,
            title: this.title,
            email: this.$store.state.userInfo.email,
            content: this.content,
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
  }
};
</script>

<style scoped>
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
.mx-1 {
  margin-top: 3px;
}

#tag {
  font-size: 14px;
}

#editor {
  margin-bottom: 20px;
}

#buttons {
  /* text-align: right; */
  /* margin-right: 5px; */
  display: inline-block;
  position: absolute;
  top: 24rem;
  right: 4rem;
  
}
.save_button {
  margin-left: 7px;
  margin-bottom: 20px;
  width: 80pgit chx;
  color: rgba(255, 255, 255, 0.904);
}

.v-text-field {
  color: #7C4DFF !important;
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

  /*editor 가운데 줄 안보이게 */
  .tui-editor .te-preview-style-vertical .te-md-splitter {
    display: none;
  }
  /* 오른쪽 결과 창 안보이게 함 */
  .te-preview {
    display: none;
  }
  /* 왼쪽 에디터 부분 크기 조정*/
  .tui-editor .te-preview-style-vertical .te-editor {
    width: 100%;
  }
}
</style>
