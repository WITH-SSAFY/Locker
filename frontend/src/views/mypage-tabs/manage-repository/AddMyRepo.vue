<template>
  <div class="container">
    <v-row>
      <v-col>
        <div class="p-2 alert-secondary" style="border-radius: 10px;">
          <div style="font-size: 1.5rem; font-weight: bold;" class="pl-5 pt-2">My Github Repository</div>
          <hr />
          <draggable
            class="list-group kanban-colum"
            :list="arrGitRepo"
            group="tasks"
            style="text-align: center;"
          >
            <a
              href
              class="list-group-item mb-2"
              v-for="element in arrGitRepo"
              :key="element.herf"
              style="border-radius: 10px;"
            >
              <img align="left" :src="element.src" />
            </a>
          </draggable>
        </div>
      </v-col>
      <v-col>
        <div class="p-2" style="background-color: #EDE7F6; border-radius: 10px;">
          <div style="font-size: 1.5rem; font-weight: bold;" class="pl-5 pt-2">My Locker Repository</div>
          <hr />
          <draggable class="list-group kanban-colum" :list="arrMyRepo" group="tasks">
            <a
              :href="element.href"
              class="list-group-item mb-2"
              v-for="element in arrMyRepo"
              :key="element.name"
              style="border-radius: 10px;"
            >
              <img align="left" :src="element.src" />
            </a>
          </draggable>
        </div>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import draggable from "vuedraggable";

export default {
  name: "kanban-board",
  components: {
    draggable
  },
  created() {
    this.showRepo;
  },
  computed: {
    showRepo() {
      return this.$store.state.showRepo;
    }
  },
  data() {
    return {
      newTask: "",
      arrGitRepo: [
        {
          href: "#",
          src:
            "https://github-readme-stats.vercel.app/api/pin/?username=junhok82&repo=Algorithm_study"
        },
        {
          href: "#",
          src:
            "https://github-readme-stats.vercel.app/api/pin/?username=junhok82&repo=ITEM"
        }
      ],
      arrMyRepo: []
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
.kanban-colum {
  min-height: 300px;
  /* height: auto; */
  /* min-height: 200px; */
}
</style>