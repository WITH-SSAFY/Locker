<template>
<v-container class="p-5">

    <div style="margin-left: 3.5rem;">
      <v-icon small color="white">mdi-chevron-right</v-icon>
      <p class="medium d-inline text-white" style="font-size: 1.5rem;">레포지토리</p>
      <p class="light text-white ml-4" style="font-size: 1.1rem;">개인 또는 팀으로 LOCKER와 함께해보세요</p>
    </div>

  <div v-if="userInfo.provider === 'github'" style="margin-left:20px;">

    <!-- 개인 / 팀 블로그 선택 -->
    <v-row>
      <v-col style="display: inline; margin-left: 4rem; margin-top: 2.5rem;">

        <div class="slash"></div>

        <v-btn
          tile
          text
          class="mx-2"
          x-large
          @click="showAction(0)"
          style="font-size: 1.3rem; border-radius: 3px;"
        >
          <mark> 내 블로그로 함께하기 </mark>
        </v-btn>

        <div class="slash"></div>

        <v-btn
          @click="showAction(2)"
          tile
          text
          class="mx-2"
          x-large
          style="font-size: 1.3rem; border-radius: 3px;"
        >
          <mark> 팀 블로그 함께하기 </mark>
        </v-btn>

        <div class="slash"></div>

      </v-col>
    </v-row>

    <!-- 내 블로그 탭 -->
    <v-row v-if="showRepo[0]">
      <v-col cols="8" style="margin-top: 3rem;">
        <v-icon large color="#7C4DFF">mdi-chevron-right</v-icon>
        <p class="thin" style="color: #7C4DFF; font-size: 3rem;">내 블로그</p>
      </v-col>
    </v-row>

    <v-row v-if="showRepo[1]">
      <v-col cols="8" style="margin-top: 3rem;">
        <v-icon large color="#7C4DFF">mdi-chevron-right</v-icon>
        <p class="thin" style="color: #7C4DFF; font-size: 3rem;">내 블로그</p>
      </v-col>
    </v-row>

    <v-btn
      style="position:relative; color: #7C4DFF; padding: 0;"
      text
      v-if="showRepo[0]"
      depressed
      @click="showAction(1)"
    >
      <v-icon>mdi-chevron-right</v-icon>
      <span style="font-size: 1.5rem;">Github에서 가져오기</span>
      <span class="thin ml-5" style="color: #EDE7F6">click</span>
    </v-btn>

    <v-btn
      v-if="showRepo[1]"
      style="position:relative; color: #7C4DFF; padding: 0;"
      text
      depressed
      @click="showAction(0)"
    >
      <v-icon>mdi-chevron-left</v-icon>
      <span style="font-size: 1.5rem;">뒤로</span>
      <span class="thin ml-5" style="color: #EDE7F6">click</span>
    </v-btn>

    <!-- 팀 블로그 탭 -->
    <v-row v-if="showRepo[2]">
      <v-col cols="8" style="margin-top: 3rem;">
        <v-icon large color="#7C4DFF">mdi-chevron-right</v-icon>
        <p class="thin" style="color: #7C4DFF; font-size: 3rem;">팀 블로그</p>
      </v-col>
    </v-row>

    <v-row v-if="showRepo[3]">
      <v-col cols="8" style="margin-top: 3rem;">
        <v-icon large color="#7C4DFF">mdi-chevron-right</v-icon>
        <p class="thin" style="color: #7C4DFF; font-size: 3rem;">팀 블로그</p>
      </v-col>
    </v-row>

    <v-btn
      style="position:relative; color: #7C4DFF; padding: 0;"
      text
      v-if="showRepo[2]"
      depressed
      @click="showAction(3)"
    >
      <v-icon>mdi-chevron-right</v-icon>
      <span style="font-size: 1.5rem;">Github에서 가져오기</span>
      <span class="thin ml-5" style="color: #EDE7F6">click</span>
    </v-btn>

    <v-btn
      v-if="showRepo[3]"
      style="position:relative; color: #7C4DFF; padding: 0;"
      text
      depressed
      @click="showAction(2)"
    >
      <v-icon>mdi-chevron-left</v-icon>
      <span style="font-size: 1.5rem;">뒤로</span>
      <span class="thin ml-5" style="color: #EDE7F6">click</span>
    </v-btn>

    <!-- 깃헙과 연동 되었을 경우 => 레포 선택 -->
    <hr style="margin:5px;" />
    <v-row v-if="showRepo[0]" style="min-height: 70vh;">
      <my-repository></my-repository>
    </v-row>
    <v-row v-if="showRepo[1]" style="min-height: 70vh;">
      <add-my-repo></add-my-repo>
    </v-row>
    <v-row v-if="showRepo[2]" style="min-height: 70vh;">
      <team-repository></team-repository>
    </v-row>
    <v-row v-if="showRepo[3]" style="min-height: 70vh;">
      <add-team-repo></add-team-repo>
    </v-row>
  </div>

  <!-- 깃헙과 연동 안 되었을 경우 -->
  <div v-else style="margin-left:20px; color: white; min-height: 25vh;">

    <div class="text-center mt-5" style="font-size: 1.3rem;">
      <p class="thin" style="padding: 2rem 0;">깃헙과 연동해주세요 !</p>
      <p class="medium">깃헙과 연동하면 당신의 <strong style="color: #7C4DFF">레포지토리</strong>를</p>
      <p class="medium"><strong style="color: #7C4DFF;">LOCKER</strong>에서 관리할 수 있어요</p>
      <v-icon color="#7C4DFF" class="mr-2">mdi-menu-up</v-icon>
      <v-btn
        depressed
        text
        color="#252525"
        router
        :to="{ name: 'register' }"
        style="text-decoration: none; padding: 2rem 0;"
      >
        <v-icon size="23" style="color: black; padding-right: 1.1rem;">mdi-github</v-icon>
        <span class="bolder text-white py-5" style="font-size: 1.1rem;">깃헙 연동 바로가기</span>
      </v-btn>

    </div>



  </div>
</v-container>
</template>

<script>
import "../../assets/css/repository.scss";
import MyRepository from "./manage-repository/MyRepository.vue";
import AddMyRepo from "./manage-repository/AddMyRepo.vue";
import TeamRepository from "./manage-repository/TeamRepository.vue";
import AddTeamRepo from "./manage-repository/AddTeamRepo.vue";
import { mapActions, mapState } from 'vuex';

export default {
  name: 'repository',
  components: {
    MyRepository,
    AddMyRepo,
    TeamRepository,
    AddTeamRepo
  },
  created() {
    this.showRepo;
    this.showAction(0);

    // 토큰 값 전달해서 getRepos 실행(Repository 리스트 받아오기)
    // this.userInfo.uid = 'jane399'
    // this.userInfo.uid = 'junhok82'
    this.userInfo.uid = 'YNNJN'
    this.userInfo.provider = 'github'
    // this.userInfo.provider = 'google'
    // console.log("userInfo.uid: ", this.userInfo.uid)

    // locker에 저장된 repository 조회하기
    // this.userInfo.id = 17
    // this.userInfo.id = 15
    this.userInfo.id = 21
},
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["userInfo", "myGitRepo", "teamGitRepo"]),
  },
  data: () => {
    return {
    };
  },
  methods: {
    ...mapActions(["getRepos"]),
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    }, 
  },
  mounted () {
    (function (document) {
    const markers = [...document.querySelectorAll('mark')];
    
    const observer = new IntersectionObserver(entries => {
      entries.forEach((entry) => {
        if (entry.intersectionRatio > 0) {
          entry.target.style.animationPlayState = 'running';
          observer.unobserve(entry.target);
        }
      });
    }, {
      threshold: 0.8
    });
    
    markers.forEach(mark => {
      observer.observe(mark);
    });
  })(document);
  }
};
</script>

<style scoped>

mark {
  --color1: #eceffc;;
  --color2: #eceffc;;
  --bg-height: 100%;
    
  all: unset;
  background-image: -webkit-gradient(linear, left top, left bottom, from(var(--color1)), to(var(--color2)));
  background-image: -o-linear-gradient(var(--color1), var(--color2));
  background-image: linear-gradient(var(--color1), var(--color2));
  background-position: 0 100%;
  background-repeat: no-repeat;
  background-size: 0 var(--bg-height);
  -webkit-animation: highlight 800ms 1 ease-out;
          animation: highlight 800ms 1 ease-out;
  -webkit-animation-fill-mode: forwards;
          animation-fill-mode: forwards;
  -webkit-animation-play-state: paused;
          animation-play-state: paused;
}


@-webkit-keyframes highlight {
  to {
    background-size: 100% var(--bg-height);
  }
}

.slash {
  margin-top: 0.5rem;
  position: absolute;
  display: inline;
  transform: translate(-50%, -50%);
  width: 0.005rem;
  height: 2rem;
  background-color: #fff;
  z-index: 1;
  transform: rotate(45deg);

}

</style>