<template>
  <div id="container">
    <div id="title_container">
      <v-text-field
      id="title"
      v-model="title"
      placeholder="제목을 입력해주세요"
      height="50"
      >
      </v-text-field>
    </div>
    
    <editor
        ref="toastuiEditor"
        id="editor"
        initialValue="내용을 입력해주세요"
        :options="editorOptions"
        height="90%"
        previewStyle="vertical"
        placeholder="내용을 입력해주세요"
        @change="onEditorChange"
    />
    <div id="buttons">
      <v-btn
        id="temp_save"
        class="save_button"
        color="#c7c9c5">
        임시저장
      </v-btn>
      <v-btn
        id="save"
        class="save_button"
        color="#7C4DFF"
        @click="postContent">
        작성완료
      </v-btn>
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
  import 'codemirror/lib/codemirror.css';
  import '@toast-ui/editor/dist/toastui-editor.css';
  //import 'highlight.js/styles/github.css';

  import { Editor } from '@toast-ui/vue-editor';
  import axios from "../../lib/axios-common.js"; //axios : 서버와 통신하기 위함
  
  export default {
    components: {
      editor: Editor,
    },
    data() {
      return {
        title: '',
        content: '',
        email: '',
        editorOptions: {
          hideModeSwitch: true,//모드 설정(markdown, wysiwyg) 안보이게함
        }
      };
    },
    methods: {
      onEditorChange() {//에디터의 content가 바뀌었을 때
        //자동 저장 구현(나중에)
      },
      postContent(){
        this.content = this.$refs.toastuiEditor.invoke('getMarkdown');
        console.log("title : "+this.title);
        console.log("content : "+this.content);
        console.log("nickname : "+this.$store.state.userInfo.nickname);
         axios
          .post("/v1/post",{title : this.title,
                            email : this.$store.state.userInfo.email,
                            content: this.content,
                            nickname: this.$store.state.userInfo.nickname})
           .then(() =>{
             //alert("작성자: "+this.$store.state.userInfo.nickname);
             this.$store.dispatch('getMyPostList');
             this.$router.push('/mypage'); //어디로 이동해야할지 정해야할듯?  
           })
          .catch(exp => alert("글 작성에 실패했습니다"+exp));
      }
  }
}
</script>

<style>
    #container {
       margin: 20px auto 0 auto;
       height: 100%;
       width: 100%;
       padding: 0 55px 0 62px;
    } 

    #title_container{
      width: 50%;
    }
    .v-input {
      font-size: 28px;
      font-weight: 800;
    }

    #editor{
      margin-bottom: 20px;
    }

    #buttons{
      text-align: right;
      margin-right: 5px;
    }
    .save_button {
      margin-left: 7px;
      margin-bottom: 20px;
      width : 80pgit chx;
      color:rgba(255, 255, 255, 0.904);
    }

    /*md 사이즈 */
    @media (max-width: 768px){
      /* 제목 너비 크기 조정 */
      #title_container{
        width: 100%;
      }
      /*editor 가운데 줄 안보이게 */
      .tui-editor .te-preview-style-vertical .te-md-splitter {
        display: none;
      }
      /* 오른쪽 결과 창 안보이게 함 */
      .te-preview{
        display: none;
      }
      /* 왼쪽 에디터 부분 크기 조정*/
      .tui-editor .te-preview-style-vertical .te-editor{
        width: 100%;
      }
    }
</style>
