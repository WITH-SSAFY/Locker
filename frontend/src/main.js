import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import BootstrapVue from 'bootstrap-vue';
import VueFullPage from 'vue-fullpage.js';
import GAuth from 'vue-google-oauth2';

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
// import 'swiper/swiper-bundle.css';

const gquthOption={
  // clientId: '858388202728-f26u33io27sn79ic2ac789sc03ht9vuf.apps.googleusercontent.com',
  clientId: '768834812579-007e5802er7gj3c93p8qa9568h8bj3na.apps.googleusercontent.com',
  scope: 'profile email', 
  prompt: 'select_account',
  fetch_basic_profile: false
}

Vue.use(GAuth, gquthOption);
Vue.use(VueFullPage);
Vue.use(BootstrapVue);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  beforeCreate(){
    this.$store.dispatch('getMemberInfo')
  },
  vuetify,
  render: h => h(App)
}).$mount("#app");

// new Swiper('.swiper-container');