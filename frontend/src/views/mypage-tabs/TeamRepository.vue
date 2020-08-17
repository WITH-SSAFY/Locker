<template>
  <div class="mt-10" style="width: 100%;">
    <v-row>
      <v-col cols="8">
        <!-- {{showRepo}} -->
        <h2>팀 블로그</h2>
      </v-col>
      <v-col cols="4">
        <v-btn depressed @click="showAction(3)">Github에서 가져오기</v-btn>
      </v-col>
    </v-row>
    <hr />
    <v-row>
      <v-col></v-col>
    </v-row>
  </div>
</template>
<script>
export default {
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