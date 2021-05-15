import Vue from 'vue'
import App from './App'
import VueRouter from "vue-router";
import router from "./router"
//导入elementUI
import ElementUI from "element-ui"
//导入element css
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
// import VueAxios from 'vue-axios'

// Vue.use(axios)
Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.prototype.serverHost=  "localhost:8081/"
// Vue.prototype.serverHost=  "1.117.62.189/"
// Vue.prototype.serverHost=  "192.168.137.1:8081/"
Vue.prototype.serverBaseURL="http://"+Vue.prototype.serverHost
// 设置请求的根路径
axios.defaults.baseURL = Vue.prototype.serverBaseURL
axios.defaults.withCredentials=true
axios.defaults.withCredentials = true;
axios.defaults.withCredentials = true;
axios.defaults.withCredentials = true;
axios.interceptors.request.use(config => {
  console.log(config);
  // 请求头挂载信息
  config.headers.Authorization = window.sessionStorage.getItem("flag");
  // 在最后必须return config
  return config;
})
new Vue({
  el: '#app',
  router,
  components: { App },
  render: h => h(App),
  template: '<App/>'
})
