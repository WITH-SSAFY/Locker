<template>
  <div class="container mt-5">
    <!-- {{ arrGitRepo }}
    <br />
    {{ arrMyRepo }}-->
    <v-row>
      <v-col style="border-radius: 20px 40px 60px 80px;">
        <div class="p-2 alert-secondary">
          <h3>My Github Organization</h3>
          <draggable class="list-group kanban-colum" :list="arrGitRepo" group="tasks">
            <div
              class="list-group-item"
              v-for="element in arrGitRepo"
              :key="element.name"
            >{{element.name}}</div>
          </draggable>
        </div>
      </v-col>
      <v-col style="border-radius: 20px 40px 60px 80px;">
        <div class="p-2" style="background-color: #EDE7F6; font-color: #7C4DFF">
          <h3>Team Locker Repository</h3>
          <draggable class="list-group kanban-colum" :list="arrMyRepo" group="tasks">
            <div
              class="list-group-item"
              v-for="element in arrMyRepo"
              :key="element.name"
            >{{element.name}}</div>
          </draggable>
        </div>
      </v-col>
      <!-- </div> -->
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
  data() {
    return {
      newTask: "",
      arrBacklog: [
        { name: "Code SignUpPage" },
        { name: "Test Dashboard" },
        { name: "Style Registration" },
        { name: "Help with Designs" }
      ],
      arrInProgress: []
    };
  },
  methods: {
    add() {
      if (this.newTask) {
        this.arrBacklog.push({ name: this.newTask });
        this.newTask = "";
      }
    }
  }
};
</script>
<style scoped>
.kanban-colum {
  min-height: 300px;
}
</style>