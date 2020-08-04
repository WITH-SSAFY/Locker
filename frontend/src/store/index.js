import Vue from "vue";
import Vuex from "vuex";
import router from "../router/index.js"

Vue.use(Vuex);

import axios from "../lib/axios-common.js"

import userLoginStore from '@/store/modules/userLoginStore.js'

export default new Vuex.Store({
  modules: {
    userLoginStore,
  },
  state: {
    myPostList: null,//내가 쓴 포스트 목록
    myDetailTitle: "",//상세보기 제목
    myDetail: "",//상세보기 내용
    writer: "",//글쓴이
    pid: "",//글번호
  },
  //state 값 변화
  mutations: {
      getMyPostList(state, payload){
        state.myPostList = payload.myPostList;
        //console.log(payload.myPostList);
        //console.log("state : ",state.myPostList);
        //state.myPostList = JSON.parse(localStorage.getItem('myPostList'));
        //console.log("myPostList: ",state.myPostList);
        //router.push({name: "mypage"});
      },
      showMyDetail(state,payload){
        state.myDetail = payload.myDetail.content;
        state.myDetailTitle = payload.myDetail.title;
        state.writer = payload.myDetail.writer;
        state.pid = payload.myDetail.pid;
        console.log("state_mayDetail :"+state.myDetail);
        console.log("payload_mayDetail :"+payload.myDetail);
        router.push({name: "readPost"});
      },
      goEditDetail(state,payload){
        state.myDetail = payload.myDetail.content;
        state.myDetailTitle = payload.myDetail.title;
        state.writer = payload.myDetail.writer;
        state.pid = payload.myDetail.pid;
        router.push({name: "editPost"});
      }
      
  },
  //비즈니스 로직
  actions: {
    getMyPostList({commit}){//내가 쓴 포스트 리스트 받아옴
      console.log("getMyPostList");
      axios
          .get("/v1/post/all") //일단은 전체 리스트받아오는 걸로
          .then(response =>{
            commit("getMyPostList",{myPostList : response.data})
          }).catch(
            exp => alert("내 글 리스트 불러오기 실패 "+exp)
          );
      
      //localStorage로 테스트
      // let myPostList = [{title: "test", content: "#test"},{title: "test2", content: "#test2"}];
      // localStorage.setItem('myPostList',JSON.stringify(myPostList));
      // commit('getMyPostList');
    },
    showMyDetail({commit}, pid){//내 글 상세보기
      console.log("showMyDetail_pid:"+pid);
      
      axios
          .get("/v1/post/" + pid) //일단은 전체 리스트받아오는 걸로
          .then(response =>{
            console.dir(response.data);
            commit("showMyDetail",{myDetail : response.data})
          }).catch(
            exp => alert("내 글 상세보기 실패 "+exp)
          );
    },
    goEditDetail({commit},pid) {
      console.log("editDetail_pid:"+pid);
      axios
      .get("/v1/post/" + pid) //일단은 전체 리스트받아오는 걸로
      .then(response =>{
        commit("goEditDetail",{myDetail : response.data})
      }).catch(
        exp => alert("내 에디터로 이동 실패 "+exp)
      );
      commit
    },
    deleteDetail({commit}, pid) {
      console.log("deleteDetail")
      console.log(pid)
      axios
        .delete("/v1/post/" + pid)
        .then(response => {
          console.log(response)
          this.dispatch("getMyPostList")
          router.push({name: "mypage"})
        })
        .catch(
          exp => alert("내 글 삭제 실패 " + exp)
        );
        commit
    },
  }
});