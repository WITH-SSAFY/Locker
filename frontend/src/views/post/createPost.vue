<template>
  <div id="container">
    <div id="title_container">
      <v-text-field
        id="title"
        v-model="title"
        placeholder="제목을 입력해주세요"
      ></v-text-field>
    </div>

    <div id="tag_list">
      <v-chip
        v-for="(tag, index) in tags"
        :key="index"
        class="mx-1"
        color="#EDE7F6"
        small
        @click="deleteTag(index)"
        >{{ tag }}</v-chip
      >
    </div>

    <div id="tag_container">
      <v-text-field
        id="tag"
        v-model="tag"
        placeholder="태그를 입력해주세요"
        height="16"
        @keyup.enter="createTag"
      ></v-text-field>
    </div>

    <editor
      ref="toastuiEditor"
      id="editor"
      :options="editorOptions"
      height="90%"
      previewStyle="vertical"
      @change="onEditorChange"
    />
    <div id="buttons">
      <v-btn id="temp_save" class="save_button" color="#c7c9c5">임시저장</v-btn>
      <v-btn id="save" class="save_button" color="#7C4DFF" @click="postContent"
        >작성완료</v-btn
      >
    </div>
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
    editor: Editor,
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
        placeholder: "내용을 입력해주세요",
      },
      pid: 0, //새로 등록되는 포스트의 pid
      tagid: 0, //tagid
      tagelem: "",
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
        .post("/v1/post", {
          title: this.title,
          email: this.$store.state.userInfo.email,
          content: this.content,
          nickname: this.$store.state.userInfo.nickname,
        })
        .then((response) => {
          //alert("작성자: "+this.$store.state.userInfo.nickname);
          this.pid = response.data;
          this.checkDupTag(); //태그 중복 확인

          this.$store.dispatch("getMyPost", this.pid); //state에포스트 정보 업데이트
          //this.$store.dispatch("getMyPostList");
          //this.$router.push("/mypage"); //어디로 이동해야할지 정해야할듯?
        })
        .catch((exp) => alert("글 작성에 실패했습니다" + exp));
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
    },
    async checkDupTag() {
      //서버로 부터 이미 등록된 태그인지 검사(태그 중복 검사)
      for (let i = 0; i < this.tags.length; i++) {
        this.tagelem = this.tags[i];
        let response = await axios.get("v1/tag/" + this.tagelem); //서버로 부터 응답이 올때까지 대기
        let data = response.data;
        if (data == 0) {
          //중복이 없다
          this.registNewTag();
        } else {
          this.tagid = data;
          this.connectTag();
        }

        // axios.get("v1/tag/" + this.tagelem).then(response => {
        //   var data = response.data;
        //   console.log("tag: " + this.tagelem);
        //   if (data == 0) {
        //     //중복이 없다
        //     this.registNewTag(); //새로운 태그이므로 등록  해야한다.
        //   } else {
        //     //이미 있는 태그
        //     this.tagid = data;
        //     //이 포스트에 해당 태그가 있다는 것을 알려줌(pid와 tagid연결)
        //     this.connectTag();
        //   }
        // });
      }
    },
    registNewTag() {
      //새로운 태그 등록
      console.log("registTag: " + this.tagelem);
      axios
        .post("/v1/tag", { tagname: this.tagelem })
        .then((response) => {
          this.tagid = response.data;
          console.log("new tagid: " + this.tagid);
          this.connectTag(); //이 포스트에 해당 태그가 있다는 것을 알려줌(pid와 tagid연결)
        })
        .catch((exp) => alert(this.tagelem + "태그 등록 실패" + exp));
    },
    connectTag() {
      //pid와 tagid 연결
      axios
        .post("v1/tag/connect", { pid: this.pid, tagid: this.tagid })
        .catch((exp) => alert("태그 연결 실패" + exp));
    },
  },
};
</script>

<style>
#container {
  margin: 20px auto 0 auto;
  height: 100%;
  width: 100%;
  padding: 0 55px 0 62px;
}

#title_container {
  width: 50%;
}
#title {
  font-size: 27px;
  font-weight: 800;
}
#tag_container {
  width: 50%;
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
  text-align: right;
  margin-right: 5px;
}
.save_button {
  margin-left: 7px;
  margin-bottom: 20px;
  width: 80pgit chx;
  color: rgba(255, 255, 255, 0.904);
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
