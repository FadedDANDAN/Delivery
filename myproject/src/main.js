// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import routeConfig from './router'
import axios from 'axios' ;
import store from './store.js'
axios.defaults.withCredentials=true;
Vue.prototype.$axios = axios;
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//引入elementui
Vue.use(ElementUI);
//加载路由中间件
Vue.use(VueRouter)

//定义路由
const router = new VueRouter({
  mode: 'history',
  routes: routeConfig
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

