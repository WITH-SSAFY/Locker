<template>
  <div style="margin-left:20px;">
    <!-- {{showRepo}} -->
    <v-row>
      <v-col>
        <v-btn
          tile
          class="mx-2"
          elevation="0"
          x-large
          color="#7C4DFF"
          dark
          @click="showAction(0)"
          style="font-size: 1.5rem;"
        >내 블로그</v-btn>
        <v-btn
          @click="showAction(2)"
          tile
          class="mx-2"
          elevation="0"
          x-large
          color="#7C4DFF"
          dark
          style="font-size: 1.5rem;"
        >팀 블로그</v-btn>
      </v-col>
    </v-row>
    <v-row v-if="showRepo[0]">
      <my-repository></my-repository>
    </v-row>
    <v-row v-if="showRepo[1]">
      <add-my-repo></add-my-repo>
    </v-row>
    <v-row v-if="showRepo[2]">
      <team-repository></team-repository>
    </v-row>
    <v-row v-if="showRepo[3]">
      <add-team-repo></add-team-repo>
    </v-row>
  </div>
</template>

<script>
import "../../assets/css/repository.scss";
import MyRepository from "./MyRepository.vue";
import AddMyRepo from "./AddMyRepo.vue";
import TeamRepository from "./TeamRepository.vue";
import AddTeamRepo from "./AddMyRepo.vue";

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
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    }
  },
  data: () => {
    return {
      cards: [
        {
          herf: "#",
          src:
            "https://github-readme-stats.vercel.app/api/pin/?username=junhok82&repo=Algorithm_study"
        },
        {
          herf: "#",
          src:
            "https://github-readme-stats.vercel.app/api/pin/?username=junhok82&repo=ITEM"
        }
      ],
      link: ""
    };
  },
  methods: {
    showAction(num) {
      for (var i in this.showRepo) {
        this.showRepo.splice(i, 1, false);
      }
      this.showRepo.splice(num, 1, true);
    }
  }
};
</script>

<style>
</style>