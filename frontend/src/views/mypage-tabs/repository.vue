<template>
  <div class="row category-back">

    <div class="top-left-box">
      <div class="category-title">
        <div class="under-line" style="width: 2.5rem;"></div>
        <h1 class="my-3" style="font-size: 1.5rem;"><strong>category</strong></h1>
      </div>
    </div>

    <div class="category-content">
      <div class="">
        <v-row class="no-gutters">
          <v-col sm="6">
            <v-card
              style="height: 14rem;"
              tile
              flat
              color="#fff"
            >
            </v-card>
          </v-col>
          <v-col
            sm="6"
            offset-sm="6"
          >
            <v-card
              style="height: 14rem;"
              tile
              flat
              color="#fff"
            >
            </v-card>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
  
</template>

<script>
export default {
  name: "repository",

}
</script>

<style>
.category-back {
  height: 100vh;
  background-color: rgb(237, 231, 224);
  margin-top: 5rem;
}

.top-left-box {
  position: relative;
  background-color: #fff;
  height: 60%;
  width: 30%;
  z-index: 1;
}

.right-box {
  width: 60%;
}

.category-title {
  position: absolute;
  top: 50%;
  right: 1rem;
}

.category-content {
  width: 70%;
  height: 60%;
  padding: 10%;
}

</style>