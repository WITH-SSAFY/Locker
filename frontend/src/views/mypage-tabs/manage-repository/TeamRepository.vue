<template>
  <div class="mt-10" style="width: 100%;">
    <v-row style="min-height: 300px;">
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div
            style="font-size: 1.5rem; font-weight: bold;"
            class="pl-5 pt-2"
          >Team Blog Repository</div>
          <hr />
          <div
            class="list-group kanban-colum"
            :list="teamLockerRepos"
            group="tasks"
            style="text-align: center;"
          >
            <a
              class="list-group-item mb-2"
              v-for="element in teamLockerRepos"
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
</template>
<script>
import { mapState } from 'vuex';
export default {
  created() {
    this.showRepo;
    // this.showAction(0);
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    },
    ...mapState(["teamLockerRepos"]),
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