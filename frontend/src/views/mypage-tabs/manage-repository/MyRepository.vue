<template>
<v-container>
  <div style="width: 100%;">
    <v-row style="min-height: 300px;">
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >My Locker Repository</div>
          <hr />
          <div
            class="list-group kanban-colum"
            :list="myLockerRepos"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in myLockerRepos"
              :key="element.repoUrl"
              style="border-radius: 10px;"
            >
              <img alt="left" id="stat" :src="element.src">
            </a>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</v-container>
</template>
<script>
// import { mapState } from 'vuex';
export default {
  created() {
    this.showRepo;
    this.myLockerRepos;
  },
  mounted() {},
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    myLockerRepos(){
      return this.$store.state.myLockerRepos;
    }
  },
  data: () => {
    return {
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
<style scoped>
</style>
