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
    showRepo: [true, false, false, false],

    //내 레포 정보
    myLockerRepos: [],
    myGitRepos: [],
    //팀 레포 정보
    teamLockerRepos: [],
    teamGitRepos: [],
    //레포 관련 정보
    commitList: [],
    langRatio: [],
    repoPost: [], //해당 레포에 걸려있는 post 목록

    myPostList: null, //내가 쓴 포스트 목록
    myDetailTitle: "", //상세보기 제목
    myDetail: "", //상세보기 내용
    postList: [],
    commentList: [],
    nickname: "", //글쓴이
    pid: null, //글 번호
    usr_id: null, //post에 저장된 userid
    created: null, //작성일자
    parentid: null, //댓글 부모번호
    depth: null, //댓글 깊이
    rid: null, //댓글 번호
    email: null, //이메일
    thumbnail: null,
    description: null,
    repo_id: null,
    isNewPost: true,
    tagname: "", //태그 클릭시 검색용

    myPost: {}, //작성한 포스트
    myTags: [], //글 작성시 등록한 태그
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
      state.myLockerRepos = [];
      state.teamLockerRepos = [];
      state.arrGitRepo = [];
      state.myRepoInfo = [];
      state.teamRepoInfo = [];
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
      state.usr_id = payload.myDetail.usr_id;
      state.created = payload.myDetail.created;
      state.email = payload.myDetail.email;
      state.thumbnail = payload.myDetail.thumbnail;
      state.description = payload.myDetail.description;
      //console.log("userId : ", state.usr_id);
      router.push({ name: "readPost", params: { pid: state.pid } });
    },
    goEditDetail(state, payload) {
      state.myDetail = payload.myDetail.content;
      state.myDetailTitle = payload.myDetail.title;
      state.nickname = payload.myDetail.nickname;
      state.pid = payload.myDetail.pid;
      state.repo_id = payload.myDetail.repo_id;
      router.push({ name: "editPost" });
    },
    goreply(state, payload) {
      state.parentid = payload.rid;
      state.depth = payload.depth;
    },
    getMyPost(state, payload) {
      state.myPost = payload.myPost;
      router.push({ name: "afterPost" }); //글 작성 후 화면으로 이동
    },
    getRepos(state, payload) {
      console.log("mutations - gitRepos", payload.repos);
      console.log("mutations uid 확인:", payload.uid);
      console.log("mutations - lockerRepoList 확인 : ", payload.lockerRepoList);

      // 팀 레포 리스트, 내 레포 리스트 구별하기
      var myGitCnt = 0;
      var teamGitCnt = 0;

      for (var j = 0; j < payload.repos.length; j++) {
        //내 repo 정보들 저장하기: myLockerRepos, myGitRepos
        var repoList = payload.repos[j];
        var imgSrc = "https://github-readme-stats.vercel.app/api/pin/?username="+repoList.name + "&repo="+repoList.repoName;
        if (payload.repos[j].name === payload.uid) {
          state.myGitRepos[myGitCnt] = { id: repoList.id, name: repoList.name, repoName: repoList.repoName, repoUrl: repoList.repoUrl, src: imgSrc};
          myGitCnt++;
        } else {
          state.teamGitRepos[teamGitCnt] = { id: repoList.id , name: repoList.name, repoName: repoList.repoName, repoUrl: repoList.repoUrl, src: imgSrc};
          teamGitCnt++;
        }
      }
      console.log("mutations - myGitRepos", state.myGitRepos);
      console.log("mutations - teamGitRepos", state.teamGitRepos);

      var myLockerCnt = 0;
      var teamLockerCnt = 0;
      for (var k = 0; k < payload.lockerRepoList.length; k++) {
        repoList = payload.lockerRepoList[k];
        // console.log("repoList", repoList)
        imgSrc = "https://github-readme-stats.vercel.app/api/pin/?username="+repoList.name + "&repo="+repoList.repoName;
        if (payload.lockerRepoList[k].name === payload.uid) {
          state.myLockerRepos[myLockerCnt] = { id: repoList.id, name: repoList.name, repoName: repoList.repoName, repoUrl: repoList.repoUrl, src: imgSrc};
          myLockerCnt++;
        } else {
          state.teamLockerRepos[teamLockerCnt] = { id: repoList.id, name: repoList.name, repoName: repoList.repoName, repoUrl: repoList.repoUrl, src: imgSrc};
          teamLockerCnt++;
        }
      }
      console.log("mutations - myLockerRepos", state.myLockerRepos);
      console.log("mutations - teamLockerRepos", state.teamLockerRepos);

      var temp = [];
      var cnt = 0;

      for (var m = 0; m < myGitCnt; m++) {
        var flag = false;
        for (var n = 0; n < myLockerCnt; n++) {
          if (
            state.myGitRepos[m].name === state.myLockerRepos[n].name &&
            state.myGitRepos[m].repoName === state.myLockerRepos[n].repoName
          ) {
            flag = true;
            break;
          }
        }
        if (!flag) {
          temp[cnt] = state.myGitRepos[m];
          cnt++;
        }
      }
      state.myGitRepos = temp;

      temp = [];
      cnt = 0; 
      for (m = 0; m < teamGitCnt; m++) {
        flag = false;
        for (n = 0; n < teamLockerCnt; n++) {
          if (
            state.teamGitRepos[m].name === state.teamLockerRepos[n].name &&
            state.teamGitRepos[m].repoName === state.teamLockerRepos[n].repoName
          ) {
            flag = true;
            break;
          }
        }
        if (!flag) {
          temp[cnt] = state.teamGitRepos[m];
          cnt++;
        }
      }
      state.teamGitRepos = temp;
    },
    getLockerRepos(state, payload) {
      var myLockerCnt = 0;
      var teamLockerCnt = 0;
      for (var j = 0; j < payload.repos.length; j++) {
        var repoList = payload.repos[j];
        var imgSrc =
          "https://github-readme-stats.vercel.app/api/pin/?username=" +
          payload.repos[j].name +
          "&repo=" +
          payload.repos[j].repoName;
        if (payload.repos[j].name === payload.uid) {
          state.myLockerRepos[myLockerCnt] = {
            id: null,
            name: repoList.name,
            repoName: repoList.repoName,
            repoUrl: repoList.repoUrl,
            src: imgSrc,
          };
          myLockerCnt++;
        } else {
          state.teamLockerRepos[teamLockerCnt] = {
            id: null,
            name: repoList.name,
            repoName: repoList.repoName,
            repoUrl: repoList.repoUrl,
            src: imgSrc,
          };
          teamLockerCnt++;
        }
      }
      console.log("mutations - myLockerRepos", state.myLockerRepos);
      console.log("mutations - teamLockerRepos", state.teamLockerRepos);
    },
    getRepoDetail(state, payload) {
      console.log("mutations - commitList", payload.commitList);
      console.log("mutations - langList", payload.langList);
      state.commitList = payload.commitList;
      state.langRatio = payload.langList;
      router.push({ name: "repoDetail" });
    },
    getRepoPost(state, payload){
      console.log("mutations: getRepoPost", payload.repoPost);
      state.repoPost = payload.repoPost;
    }
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
              uid: null,
            };

            if (pic === "null") {
              userInfo.picture = null;
            }
            if (prov === "null") {
              userInfo.provider = null;
            } else {
              if (prov === "github") {
                userInfo.uid = res.uid;
              }
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

    deleteUserInfo({ commit }, userInfo) {
      console.log("delete - userInfo : ", userInfo);

      var result = confirm("정말 탈퇴하실 건가요?");
      if (result) {
        //예를 선택하면
        let token = localStorage.getItem("access_token");
        if (token !== null) {
          //token이 있을 때, axios.delete 실행
          let config = {
            Accept: "*/*",
            "X-AUTH-TOKEN": token,
          };
          axios
            .delete("/v1/user/" + userInfo.id, config)
            .then((res) => {
              console.log("delete - res: ", res.data);
              //삭제 성공하면 값, localStorage 초기화
              commit("logout");
              localStorage.removeItem("access_token");
              localStorage.removeItem("github_token");
              router.push({ name: "home" });
            })
            .catch((err) => {
              console.log("delete - err: ", err.data);
              window.location.reload();
            });
        } else {
          alert("인증되지 않은 사용자입니다!");
          router.push({ name: "home" });
        }
      }
    },

    findPassword({ commit }, email) {
      commit;
      console.log("비번찾기 -> email: ", email);

      axios
        .put("/v1/user/find/password?email=" + email)
        .then((res) => {
          console.log("find pw - res", res.data);
          alert("입력하신 이메일의 메일함을 확인해주세요!");
          router.push({ name: "home" });
        })
        .catch((err) => {
          console.log("find pw - err.response.data: ", err.response.data);
          if (err.response) {
            if (err.response.data.code === -1000) {
              alert("존재하지 않는 회원입니다! 이메일을 확인해주세요!");
              window.location.reload();
            } else {
              alert("오류 발생 : " + err.response.data.msg);
              window.location.reload();
            }
          } else {
            alert("오류 발생 : ", err);
          }
        });
    },

    changePassword({ commit }, userInfo) {
      commit;
      console.log("change PW - userInfo : ", userInfo);
      const token = localStorage.getItem("access_token");
      axios
        .put(
          "/v1/user/password?token=" +
            token +
            "&password=" +
            userInfo.oldPw +
            "&newPassword=" +
            userInfo.password
        )
        .then((res) => {
          console.log("change PW - res", res.data);
          alert("비밀번호 변경을 성공하였습니다!");
          router.push({ name: "userSetting" });
        })
        .catch((err) => {
          console.log("change pw - err", err.response);
          if (err.response) {
            if (err.response.data.code === -1000) {
              alert("현재 비밀번호를 확인해주세요!");
            }
          }
        });
    },

    getRepos({ commit }, userInfo) {
      console.log("getRepos - userInfo 값 확인", userInfo);
      axios
        .get(
          "/v1/github/repos?token=" +
            userInfo.token +
            "&accessToken=" +
            userInfo.accessToken
        )
        .then((res) => {
          console.log("res.data", res.data);
          var repoList = res.data.list;
          // commit("getRepos", { repos: repoList, uid: userInfo.uid});

          axios
            .get("/v1/github?pk=" + userInfo.id)
            .then((res) => {
              var lockerRepoList = res.data.list;
              console.log("getRepos - res", res.data.list);
              console.log(
                "lockerRepoList를 확인해보아요~~~!!!!",
                lockerRepoList
              );
              commit("getRepos", {
                repos: repoList,
                uid: userInfo.uid,
                lockerRepoList,
              });
            })
            .catch((err) => {
              console.log("getRepos - err", err.data);
            });
        })
        .catch((err) => {
          console.log("err", err);
        });
    },
    getLockerRepos({ commit }, userInfo) {
      axios
        .get("/v1/github?pk=" + userInfo.id)
        .then((res) => {
          console.log("res", res);
          commit("getLockerRepos", { id: userInfo.id, repos: res.data.list });
        })
        .catch((err) => {
          console.log("err", err);
        });
    },
    getRepoDetail({commit, dispatch}, repoInfo){
      console.log("getRepoDetail - repoInfo:", repoInfo)
      
      var commitList = [];
      var langList = null;
      //커밋 정보 얻어오기
      axios
        .get(
          "/v1/github/commits?name=" +
            repoInfo.name +
            "&repoName=" +
            repoInfo.repoName
        )
        .then((res) => {
          commitList = res.data.list;
          console.log("commitList - res", res.data.list);
          axios
            .get(
              "/v1/github/lang?name=" +
                repoInfo.name +
                "&repo=" +
                repoInfo.repoName
            )
            .then((res) => {
              langList = res.data.data;
              console.log("langList - res", res.data.data);
              dispatch('getRepoPost', repoInfo.id)
              commit('getRepoDetail', { commitList, langList });
            })
            .catch((err) => {
              console.log("langList - err", err.reponse);
              if(err.response.data.success == false){
                dispatch('getRepoPost', repoInfo.id)
                commit('getRepoDetail', { commitList, langList });
              }
            });
        })
        .catch((err) => {
          console.log("commitList - err", err.response);
          if (err.response) {
            if (err.response.data.success == false) {
              axios
                .get(
                  "/v1/github/lang?name=" +
                    repoInfo.name +
                    "&repo=" +
                    repoInfo.repoName
                )
                .then((res) => {
                  langList = res.data.data;
                  console.log("langList - res", res.data.data);
                  dispatch('getRepoPost', repoInfo.id)
                  commit('getRepoDetail', { commitList, langList });
                })
                .catch((err) => {
                  console.log("langList - err", err.response);
                  if(err.response.data.success == false){
                    dispatch('getRepoPost', repoInfo.id)
                    commit('getRepoDetail', { commitList, langList });
                  }
                });
            }
          }
        });
    },

    getRepoPost({commit}, repoInfo){
      console.log("getRepoPost - repoInfo", repoInfo);
      axios
        .get("/v1/post/all/list/repo?repo_id="+repoInfo)
        .then((res)=>{
          commit('getRepoPost', { repoPost: res.data})
        })
        .catch((err)=>{
          console.lot("getRepoPost - err", err.response);
        })
    },

    getMyPostList({ commit }, usr_id) {
      // 내가 쓴 포스트 리스트 받아옴
      axios
        .get("/v1/post/all/" + usr_id)
        .then((response) => {
          commit("getMyPostList", { myPostList: response.data });
        })
        .catch((exp) => alert("내 글 리스트 불러오기 실패 " + exp));
    },
    getPostList({ commit }) {
      axios
        .get("/v1/post/all/list")
        .then((response) => {
          commit("getPostList", { postList: response.data.list });
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
    },
    deleteDetail({ commit }, pid) {
      axios
        .delete("/v1/post/" + pid)
        .then((response) => {
          console.log(response);
          this.dispatch("getMyPostList", this.state.userInfo.id);
          router.push({ name: "mypage" });
        })
        .catch((exp) => alert("내 글 삭제 실패 " + exp));
      commit;
    },
    getMyPost({ commit }, pid) {
      //글 작성후, 해당 pid를 가진 포스트의 정보 받아옴
      axios
        .get("/v1/post/" + pid)
        .then((response) => {
          commit("getMyPost", { myPost: response.data });
        })
        .catch((exp) => alert("포스트 데이터 받기 실패 " + exp));
    },
  },
});
