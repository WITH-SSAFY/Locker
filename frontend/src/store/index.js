import Vue from "vue";
import Vuex from "vuex";
import router from "../router/index.js";

Vue.use(Vuex);

// import axios2 from "axios"
import axios from "../lib/axios-common.js";

export default new Vuex.Store({
  state: {
    userInfo: null,
    isLogin: false,
    // isLogin: true,
    isLoginError: false,
    myPostList: null, //내가 쓴 포스트 목록
    myDetailTitle: "", //상세보기 제목
    myDetail: "", //상세보기 내용
    postList: [],
    commentList: [],
    nickname: "", //글쓴이
    pid: null, //글 번호
    created: null, //작성일자
    parentid: null, //댓글 부모번호
    depth: null, //댓글 깊이
    rid: null, //댓글 번호
  },
  //state 값 변화
  mutations: {
    // 로그인이 성공했을 때
    loginSuccess(state, payload) {
      console.log("login success!");
      state.isLogin = true;
      state.isLoginError = false;
      state.userInfo = payload;
      router.push({ name: "home" });
    },
    // 로그인이 실패했을 때
    loginError(state) {
      console.log("login error!");
      state.isLoginError = true;
      state.isLogin = false;
    },
    logout(state) {
      state.isLogin = false;
      state.isLoginError = false;
      state.userInfo = null;
    },
    getMyPostList(state, payload) {
      state.myPostList = payload.myPostList;
      //console.log(payload.myPostList);
      //console.log("state : ",state.myPostList);
      //state.myPostList = JSON.parse(localStorage.getItem('myPostList'));
      //console.log("myPostList: ",state.myPostList);
      //router.push({name: "mypage"});
    },
    getPostList(state, payload) {
      state.postList = payload.postList;
    },
    getCommentList(state, payload) {
      state.commentList = payload.commentList;
    },
    showMyDetail(state, payload) {
      state.myDetail = payload.myDetail.content;
      state.myDetailTitle = payload.myDetail.title;
      state.nickname = payload.myDetail.nickname;
      state.pid = payload.myDetail.pid;
      state.created = payload.myDetail.created;
      router.push({ name: "readPost" });
    },
    goEditDetail(state, payload) {
      state.myDetail = payload.myDetail.content;
      state.myDetailTitle = payload.myDetail.title;
      state.nickname = payload.myDetail.nickname;
      state.pid = payload.myDetail.pid;
      router.push({ name: "editPost" });
    },
    goreply(state, payload) {
      state.parentid = payload.rid;
      state.depth = payload.depth;
    },
  },
  //비즈니스 로직
  actions: {
    // 로그인 시도
    login({ dispatch }, loginObj) {
      // 로그인 > 유효한 멤버인지 확인하고 토큰 반환
      axios
        .post(
          "/v1/signin?email=" + loginObj.id + "&password=" + loginObj.password
        ) //파라메터(body)
        .then((res) => {
          // 성공 시 token을 받아옴 (실제로는 user_id 값을 받아옴 / 토큰에 user_id를 암호화해서)
          // 이 토큰을 헤더에 포함시켜서 유저 정보을 요청
          //config에서 헤더 값을 설정해 줄 수 있고, 헤더 내에 토큰 값을 넣어주려고 하기 때문에 config 쓰는것.
          let token = res.data.data;
          localStorage.setItem("access_token", token); //key, value
          dispatch("getMemberInfo");
        })
        .catch((err) => {
          console.log(err);
          alert("이메일과 비밀번호를 확인하세요");
        });
    },

    logout({ commit }) {
      var result = confirm("로그아웃 하시겠어요?");
      if (result) {
        commit("logout");
        console.log("로그아웃 합니다!");
        localStorage.removeItem("access_token");
        localStorage.removeItem("github_token");
        router.push({ name: "home" });
      }
    },

    signinWithKakao({ dispatch }) {
      console.log("signinWithKakao");
      window.Kakao.Auth.login({
        success: function(authObj) {
          axios
            .post("/v1/signin/kakao?accessToken=" + authObj.access_token)
            .then((response) => {
              console.log(response.data);
              let token = response.data.data;
              localStorage.setItem("access_token", token); //key, value
              dispatch("getMemberInfo");
            })
            .catch((err) => {
              if (err.response) {
                console.log("err.response.data", err.response.data);
              } else if (err.request) {
                console.log("error!!!", err.request);
              } else {
                console.log("Error", err.message);
              }
              console.log("err.config", err.config);
            });
        },
        fail: function(err) {
          alert("fail", JSON.stringify(err));
          console.log("err : ", err);
        },
      });
    },
    signinWithSocial({ dispatch }, authObj) {
      console.log("signinWithSocial!!!");
      dispatch;
      console.log("authObj 값 확인 : ", authObj);
      //access_token 가지고 서버에 요청하기
      // -> 성공하면, getMemberInfo를 dispatch하기
      // -> 없는 사용자의 경우 -> 서버에서 아예 signup 해줌
      axios
        .post(
          "/v1/signin/" +
            authObj.provider +
            "?accessToken=" +
            authObj.access_token
        )
        .then((response) => {
          console.log(response.data);
          let token = response.data.data;
          localStorage.setItem("access_token", token); //key, value
          let temp = localStorage.getItem("temp");
          if (temp !== null) {
            localStorage.removeItem("temp");
          }
          dispatch("getMemberInfo");
        })
        .catch((err) => {
          if (err.response) {
            console.log("err.response.data", err.response.data);
          } else if (err.request) {
            console.log("error!!!", err.request);
          } else {
            console.log("Error", err.message);
          }
          console.log("err.config", err.config);
        });
    },

    signupWithSocial({ commit }, authObj) {
      console.log("signinWithSocial - " + authObj.provider);
      axios
        .post(
          "/v1/signup/" +
            authObj.provider +
            "?accessToken=" +
            authObj.access_token
        )
        .then((response) => {
          console.log("response.data", response.data);
          commit;
          router.push({ name: "home" });
        })
        .catch((err) => {
          if (err.response) {
            console.log("err.response.data", err.response.data);
          } else if (err.request) {
            console.log("err.request", err.request);
          } else {
            console.log("err.message", err.message);
          }
          console.log("err.config", err.config);
        });
    },

    getMemberInfo({ commit }) {
      //로컬 스토리지에 저장되어있는 토큰을 불러온다.
      let token = localStorage.getItem("access_token");
      if (token !== null) {
        //반환된 토큰을 가지고 유저정보를 반환
        //새로고침을 하면 state 날라감 -> 토큰만 가지고 멤버정보 요청 가능 : localStorage에 토큰 저장
        axios //config : 보안과 관련된 헤더나 옵션 등을 설정해줄 수 있는 파일
          .get("/v1/user?lang=ko&token=" + token)
          .then((response) => {
            let res = response.data.data;
            let pic = res.picture;
            let prov = res.provider;
            let userInfo = {
              id: res.id,
              email: res.email,
              name: res.name,
              nickname: res.nickname,
              picture: pic,
              provider: prov,
              introduction: res.introduction,
            };
            if (pic === "null") {
              userInfo.picture = null;
            }
            if (prov === "null"){
              userInfo.provider = null;
            }
            console.log("가지고 온 유저 정보 : ", res);
            commit("loginSuccess", userInfo);
            if (pic == "null") {
              userInfo.picture = null;
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },

    updateUserInfo({ commit }, userInfo) {
      console.log("udateUserInfo!!");
      commit;

      axios
        .put(
          "/v1/user/info?id=" +
            userInfo.id +
            "&nickname=" +
            userInfo.nickname +
            "&introduction=" +
            userInfo.introduction
        )
        .then((response) => {
          console.log("response", response);
        })
        .catch((exp) => {
          console.log("exp", exp);
        });
    },
    
    deleteUserInfo({commit}, userInfo){
      console.log("delete - userInfo : ", userInfo)
      
      var result = confirm("정말 탈퇴하실 건가요?");
      if (result) { //예를 선택하면
        let token = localStorage.getItem("access_token");
        if(token !== null){ //token이 있을 때, axios.delete 실행
          let config = {
            "Accept": "*/*",
            "X-AUTH-TOKEN": token
          }
          axios
            .delete("/v1/user/"+userInfo.id, config)
            .then((res) => {
              console.log("delete - res: ", res.data)
              //삭제 성공하면 값, localStorage 초기화 
              commit("logout");
              localStorage.removeItem("access_token");
              localStorage.removeItem("github_token");
              router.push({ name: "home" });
            })
            .catch((err) => {
              console.log("delete - err: ", err.data)
              window.location.reload()
            })

        } else {
          alert("인증되지 않은 사용자입니다!")
          router.push({ name: "home"})
        }
      } 
    },

    findPassword({commit}, email){
      console.log("비번찾기 -> email: ", email)

      axios
        .put("/v1/user/find/password?email="+email)
        .then((res) =>{
          console.log("find pw - res", res.data)
          alert("입력하신 이메일의 메일함을 확인해주세요!")
          router.push({ name: "home" })
        })
        .catch((err) => {
          console.log("find pw - err.response.data: ", err.response.data);
          if(err.response){
            if(err.response.data.code === -1000){
              alert("존재하지 않는 회원입니다! 이메일을 확인해주세요!")
              window.location.reload();
            } else{
              alert("오류 발생 : "+err.response.data.msg)
              window.location.reload();
            }
          } else {
            alert("오류 발생 : ", err);
          }
        })
      commit
    },

    getMyPostList({ commit }, email) {
      // 내가 쓴 포스트 리스트 받아옴
      axios
        .get("/v1/post/all/" + email)
        .then((response) => {
          commit("getMyPostList", { myPostList: response.data });
        })
        .catch((exp) => alert("내 글 리스트 불러오기 실패 " + exp));
    },
    getPostList({ commit }, page) {
      axios
        .get("/v1/post/all/page/" + page)
        .then((response) => {
          // console.log('들어옴 ', response.data)
          commit("getPostList", { postList: response.data });
        })
        .catch((exp) => alert("전체 글 리스트 불러오기 실패" + exp));
    },
    getCommentList({ commit }, pid) {
      // 하나의 포스트에 대한 모든 댓글 받아옴
      axios
        .get("/v1/comment/" + pid)
        .then((response) => {
          console.log(response.data);
          commit("getCommentList", { commentList: response.data });
        })
        .catch((exp) => alert("전체 댓글 가져오기 실패" + exp));
    },
    showMyDetail({ commit }, pid) {
      //내 글 상세보기
      axios
        .get("/v1/post/" + pid)
        .then((response) => {
          console.dir(response.data);
          commit("showMyDetail", { myDetail: response.data });
        })
        .catch((exp) => alert("내 글 상세보기 실패 " + exp));
    },
    goEditDetail({ commit }, pid) {
      axios
        .get("/v1/post/" + pid)
        .then((response) => {
          commit("goEditDetail", { myDetail: response.data });
        })
        .catch((exp) => alert("내 에디터로 이동 실패 " + exp));
      commit;
    },
    deleteDetail({ commit }, pid) {
      axios
        .delete("/v1/post/" + pid)
        .then((response) => {
          console.log(response);
          this.dispatch("getMyPostList");
          router.push({ name: "mypage" });
        })
        .catch((exp) => alert("내 글 삭제 실패 " + exp));
      commit;
    },
  },
});
