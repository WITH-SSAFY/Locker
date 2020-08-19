<template>
<v-container>
  <div>
    <h1>repository-detail 화면 입니다...!!</h1>
    <div>
      <h2>커밋정보들</h2>
      <div style="background-color: gray; border-radius: 10px;">
        <div style="height: 10px;"></div>
        <div style="background-color: white; margin: 10px;" v-for="(commit, index) in commitList" :key="commit.message">
          <p>{{index}}. {{commit.date}}/{{commit.message}}/{{commit.url}}</p>
        </div>
        <div style="height: 10px;"></div>
      </div>
    </div>
    <div>
      <h2>언어사용비유우울</h2>
      <div>{{langRatio}}</div>
    </div>
  </div>
  <div>
    <h2>레포에 걸려있는 포스트 출력</h2>
    <div style="background-color: skyblue; border-radius: 10px;">
      <div style="height: 10px;"></div>
      <div style="background-color: white; margin: 10px;" v-for="(post, index) in repoPost" :key="post.pid">
        <p>{{index}}. {{post.created}}/{{post.title}}/{{post.nickname}}</p>
      </div>
      <div style="height: 10px;"></div>
    </div>
  </div>
</v-container>
</template>
<script>
import { mapState } from "vuex";

export default { 
  computed: {
    ...mapState(["commitList", "langRatio", "repoPost"])
  },  
}
</script>