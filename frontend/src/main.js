import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import BootstrapVue from 'bootstrap-vue';
import VueFullPage from 'vue-fullpage.js';

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import 'swiper/swiper-bundle.css';


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