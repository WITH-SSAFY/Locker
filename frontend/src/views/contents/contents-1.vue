<template>
  <div class="contents-1">
    <v-container fill-height style="padding: 0;">
      <v-layout align-center style="position: relative;">
        <div>
          <v-flex class="logo">
            <v-icon large color="black" class="mb-5">mdi-archive-outline </v-icon>
            <h1>LOCKER</h1>
            <div class="under-line mb-5"></div>
          </v-flex>
          <v-btn color="#7C4DFF" class="my-5">Getting Started</v-btn>
          <v-flex>
            <h5>Already have an accounts?</h5>
            <a><h5 style="color: #7C4DFF">Sign in</h5></a>
          </v-flex>
        </div>

        <div class="mx-auto ml-5">
          <div class="box-top"></div>
          <div class="box-under"></div>
        </div>
        <div style="position: relative; margin-top: 2.5rem; z-index: 1;">
          <h1><strong style="font-size: 3.5rem;">Dive deeper on topics</strong></h1>
          <h1><strong style="font-size: 3.5rem;">that matter to you.</strong></h1>
          <!-- <div class="box-over"></div> -->
        </div>

      </v-layout>
    </v-container>

  </div>
</template>

<script>
export default {
  name: 'contents1',
  components: {

  },
  props: {

  },
}
</script>

<style>

</style>