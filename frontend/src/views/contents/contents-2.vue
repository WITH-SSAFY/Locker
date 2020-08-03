<template>
  <div class="contents-2">
    <v-container fill-height style="position: relative; padding: 0;">

      <v-flex class="align-center text-section">
        <v-flex class="logo">
          <v-icon large color="white" class="mb-5">mdi-archive-outline </v-icon>
          <h1>LOCKER</h1>
          <div class="under-line"></div>
        </v-flex>
        <v-flex class="sentence">
          <h2>Thousands of Perspectives,</h2>
          <h2>One LOCKER</h2>
        </v-flex>
      </v-flex>

      <div class="lockers-back">
        <div class="diagonals">
          <div class="diagonal1"></div>
          <div class="diagonal2"></div>
        </div>

        <div class="lockers">
          <v-row>
            <v-col sm="4">
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
              offset-sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
          </v-row>

          <v-row>
            <v-col
              sm="4"
              offset-sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
                color="#999"
              >
              </v-card>
            </v-col>
          </v-row>

          <v-row>
            <v-col sm="4">
              <v-card
                class="locker pa-1"
                tile
              >
              </v-card>
            </v-col>
            <v-col
              sm="4"
            >
              <v-card
                class="locker pa-1"
                tile
                color="#999"
              >
              </v-card>
            </v-col>
          </v-row>

        </div>
      </div>

    </v-container>
  </div>
</template>

<script>
export default {
  name: 'contents2',
  components: {

  },
  props: {

  },
}
</script>

<style>

</style>