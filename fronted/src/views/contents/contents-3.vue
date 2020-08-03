<template>
  <div class="contents-3">
    <v-container fill-height>
      <div class="diagonals">
        <div class="diagonal1"></div>
        <div class="diagonal2"></div>
      </div>

      <v-layout align-center>
        <v-flex style="padding: 15%;">
          <v-btn
            depressed
            block
            color="dark"
            class="mb-2"
          >
            <v-icon dark>mdi-github</v-icon>
            <span class="ml-2">Github</span>
          </v-btn>

          <v-btn
            depressed
            block
            color="dark"
          >
            <v-icon dark>mdi-google-plus</v-icon>
            <span class="ml-1">Google</span>
          </v-btn>
        </v-flex>

        <v-flex>
          <h1>Thanks for reading.</h1>
          <h1>Ready to upgrade to</h1>
          <h1>membership?</h1>
          <v-icon large color="black" class="my-5">mdi-archive-outline </v-icon>
          <h1>LOCKER</h1>
          <div class="under-line mb-5"></div>
          <v-btn color="#7C4DFF" class="my-5">Getting Started</v-btn>
        </v-flex>
      </v-layout>

    </v-container>
  </div>
</template>

<script>
export default {
  name: 'contents3',
  components: {

  },
  props: {

  },
}
</script>

<style>

</style>