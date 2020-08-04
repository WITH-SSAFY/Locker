import axios2 from "axios"
import axios from "../../lib/axios-common.js"
import router from "../../router/index.js"

const userLoginStore = {
  state: {
    userInfo: null,
    isLogin: false,
    // isLogin: true,
    isLoginError: false,
  },
  mutations: {
    // 로그인이 성공했을 때
    loginSuccess(state, payload){
      console.log("login success!")
      state.isLogin = true;
      state.isLoginError = false;
      state.userInfo = payload;
      router.push({ name: "home"})
    },
  // 로그인이 실패했을 때
    loginError(state){
      console.log("login error!")
      state.isLoginError = true;
      state.isLogin = false;
    },
    logout(state){
      state.isLogin = false;
      state.isLoginError = false;
      state.userInfo = null;
    },
  },
  actions: {
    // 로그인 시도
    login({dispatch}, loginObj){
      // 로그인 > 유효한 멤버인지 확인하고 토큰 반환
      axios
        .post('/v1/signin?id='+loginObj.id+"&password="+loginObj.password) //파라메터(body)
        .then( res => {
          // 성공 시 token을 받아옴 (실제로는 user_id 값을 받아옴 / 토큰에 user_id를 암호화해서)
          // 이 토큰을 헤더에 포함시켜서 유저 정보을 요청
          
          //config에서 헤더 값을 설정해 줄 수 있고, 헤더 내에 토큰 값을 넣어주려고 하기 때문에 config 쓰는것.
          let token = res.data.data
          //토큰을 로컬스토리지에 저장
          localStorage.setItem("access_token", token) //key, value
          dispatch('getMemberInfo')
        })
        .catch( err => {
            console.log(err)
            // if(loginObj.id !== '' && loginObj.password !== ''){
              alert('이메일과 비밀번호를 확인하세요')
            // }
        });
    },
    logout({commit}){
      var result = confirm("로그아웃 하시겠어요?")
      if(result){
        commit('logout')
        console.log("로그아웃 합니다!")
        localStorage.removeItem("access_token")
        router.push({ name: "home"})
      } else {
        // commit('login')
      }
    },

    signinWithKakao({dispatch}) {
      console.log("signinWithKakao")
      window.Kakao.Auth.login({
        success: function(authObj){
          axios
          .post("/v1/signin/kakao?accessToken="+authObj.access_token)
          .then(response =>{
            console.log(response.data);
            let token = response.data.data
            localStorage.setItem("access_token", token) //key, value
            dispatch('getMemberInfo')
          })
          .catch(err => {
            if(err.response){
              console.log("err.response.data", err.response.data);
              if(err.response.data.code === -1000){
                console.log("token 값 확인 : ", authObj.access_token);
                dispatch('signupWithKakao', authObj)
              } 
              // console.log("status", err.reaponse.status);
              // console.log("headers", err.reaponse.headers);
            } else if(err.request){
              console.log("error!!!",err.request);
            } else{
              console.log('Error', err.message);
            }
            console.log("err.config",err.config);
          })
        },
        fail: function(err){
          alert("fail",JSON.stringify(err))
        }
      })
    }, 
    signupWithKakao({commit}, authObj) {
      console.log("signupWithKakao")
      commit
      console.log(authObj)
      axios
        .post("/v1/signup/kakao?accessToken="+authObj.access_token)
        .then(response => {
          console.log("response.data", response.data);
          commit
          alert("회원가입에 성공했습니다.!")
          router.push({name: "home"})
        })
        .catch(err => {
          if(err.response){
            console.log("err.response.data", err.response.data);
            
          } else if(err.request){
            console.log("err.request",err.request);
          } else{
            console.log('err.message', err.message);
          }
          console.log('err.config', err.config);
        })
    },
    signinWithSocial({dispatch}, authObj){
      console.log("signinWithSocial - "+authObj.provider)
      dispatch
      let token = null
      
      axios2
      .get("http://localhost:8090/oauth2/authorization/"+authObj.provider)
        .then(response => {
          token = response.data.access_token
          console.log(response.data);
          axios
            .post("/v1/signin/"+authObj.provider+"?accessToken="+token)
            .then(response =>{
              console.log(response.data)
              localStorage.setItem("access_token", token) //key, value
              dispatch('getMemberInfo')
            })
            .catch(err => {
              if(err.response){
                console.log("err.response.data", err.response.data);
                if(err.response.data.code === -1000){
                  console.log("token 값 확인 : ", token);
                  dispatch('signupWith', ({ access_token: token, provider: authObj.provider }))
                } 
                // console.log("status", err.reaponse.status);
                // console.log("headers", err.reaponse.headers);
              } else if(err.request){
                console.log("error!!!",err.request);
              } else{
                console.log('Error', err.message);
              }
              console.log("err.config",err.config);
            })
        })
        .catch(err => {
          if(err.response){
            console.log("err.response.data", err.response.data);
            if(err.response.data.code === -1000){
              // console.log("token 값 확인 : ", .access_token);
              // router.push({ name: "socialJoin", params: { access_token: authObj.access_token }})
              dispatch()
            } 
            // console.log("status", err.reaponse.status);
            // console.log("headers", err.reaponse.headers);
          } else if(err.request){
            console.log("error!!!",err.request);
          } else{
            console.log('Error', err.message);
          }
          console.log("err.config",err.config);
        })
    },

    signupWithSocial({commit}, authObj){
      console.log("signinWithSocial - "+authObj.provider)
      commit
      axios
        .post("/v1/signup/"+authObj.provider+"?accessToken="+authObj.access_token)
        .then(response => {
          console.log("response.data", response.data);
          commit
          alert("회원가입에 성공했습니다.!")
          router.push({name: "home"})
        })
        .catch(err => {
          if(err.response){
            console.log("err.response.data", err.response.data);
            
          } else if(err.request){
            console.log("err.request",err.request);
          } else{
            console.log('err.message', err.message);
          }
          console.log('err.config', err.config);
        })
    },

    getMemberInfo({commit}) {
      //로컬 스토리지에 저장되어있는 토큰을 불러온다.
      let token = localStorage.getItem("access_token");
      if(token !== null){
        let config = {  
          headers: {
            "Accept": "*/*",
            "X-AUTH-TOKEN": token
          }
        }
        //반환된 토큰을 가지고 유저정보를 반환
        //새로고침을 하면 state 날라감 -> 토큰만 가지고 멤버정보 요청 가능 : localStorage에 토큰 저장
        axios //config : 보안과 관련된 헤더나 옵션 등을 설정해줄 수 있는 파일
          .get("/v1/user?lang=ko", config)
          .then(response => {
              let userInfo = {
                id: response.data.data.id,
                email: response.data.data.email,
                name: response.data.data.name,
                nickname: response.data.data.nickname,
                picture: response.data.data.picture
              }
              commit('loginSuccess', userInfo)
          })
          .catch(error => {
            console.log(error)
            alert('이메일과 비밀번호를 확인하세요')
          })
      }
    },
  }
}

export default userLoginStore