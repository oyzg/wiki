<template>
  <a-layout-header class="header">
    <div class="logo">mywiki</div>
    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook" :style="user.id ? {} : {display: 'none'}">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category" :style="user.id ? {} : {display: 'none'}">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user" :style="user.id ? {} : {display: 'none'}">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
      <a-popconfirm
          cancel-text="否 "
          ok-text="是"
          title="确认退出登录吗？"
          @confirm="logout"
      >
        <a class="login-menu" v-show="user.id">
          <span>退出登录</span>
        </a>
      </a-popconfirm>
      <a class="login-menu" v-show="user.id">
        <span>您好:{{user.name}}</span>
      </a>
      <a class="login-menu" @click="showLoginModal" v-show="!user.id">
        <span>登录</span>
      </a>


    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ spran: 6 }" :wrapper-col="{ span: 18 }">

        <a-form-item label="用户名" >
          <a-input v-model:value="loginUser.loginName"/>
        </a-form-item>
        <a-form-item label="密码" >
          <a-input v-model:value="loginUser.password" type="password"/>
        </a-form-item>
      </a-form>
    </a-modal>

  </a-layout-header>
</template>

<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
  name: 'the-header',
  setup() {
    // -------- 登录 ---------
    const loginUser = ref({
      loginName: 'test',
      password: 'test123'
    });

    //登录后
    const user = computed(() => store.state.user);

    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    }

    const login = () => {
      console.log("开始登录！")
      loginModalLoading.value = true;
      loginUser.value.password = hexMd5(loginUser.value.password + KEY);
      axios.post('/user/login',loginUser.value).then((response) => {
        loginModalVisible.value = false;
        const data = response.data;
        if (data.success) {
          loginModalLoading.value = false;
          message.success("登录成功！");

          store.commit("setUser",data.content);
        } else {
          message.error(data.message);
          loginModalLoading.value = false;
        }
      });
    };

    const logout = () => {
      console.log("退出登录！")
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("退出登录成功！");
          store.commit("setUser", {});
        } else {
          message.error(data.message);
          loginModalLoading.value = false;
        }
      });
    };

    return {
      loginModalVisible,
      loginModalLoading,
      login,
      showLoginModal,
      loginUser,
      user,
      logout
    }

  }
});
</script>

<style>
  .logo {
    width: 70px;
    height: 31px;
    /*background: rgba(255, 255, 255, 0.2);*/
    /*margin:  0 20px 0 0;*/
    float: left;
    color: white;
    font-size: 18px;
    text-align: center;
  }
  .login-menu {
    float: right;
    color: white;
    padding-right: 20px;
  }
</style>
