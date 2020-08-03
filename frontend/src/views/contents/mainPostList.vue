<template>
  <v-container>
    <div class="row">
      <div class="col-md-9">
        <v-flex style="margin-top: 0;">
          <h1 class="mb-5" style="font-size: 3.3rem;"><strong>LOCKER</strong></h1>
          <!-- <h1 class="ml-2 mb-5" style="posigion: absolute; font-size: 4rem;"><strong>LOCKER</strong></h1> -->
          <div class="main-under-line"></div>
        </v-flex>
        <v-item-group
          v-model="selected"
          :mandatory="mandatory"
          :multiple="multiple"
        >
          <v-container class="pa-0">
            <v-row>
              <v-col
                md="3"
                offset-md="3"
              >
                <v-item v-slot:default="{ active, toggle }">
                  <v-card
                    v-if="type === 'cards'"
                    :color="active ? 'deep-purple lighten-5' : ''"
                    class="d-flex align-center"
                    dark
                    height="27vh"
                    @mouseover="toggle"
                  >
                    <v-scroll-y-transition>
                      <div
                        v-if="active"
                        class="display-3 flex-grow-1 text-center"
                      >
                        Locker
                      </div>
                    </v-scroll-y-transition>
                  </v-card>
                </v-item>
              </v-col>

              <v-col
                v-for="n in 3"
                :key="`a-${n}`"
                cols="12"
                md="3"
              >
                <v-item v-slot:default="{ active, toggle }">
                  <v-card
                    v-if="type === 'cards'"
                    :color="active ? 'deep-purple accent-2' : ''"
                    class="d-flex align-center"
                    dark
                    height="27vh"
                    @mouseover="toggle"
                  >
                    <v-scroll-y-transition>
                      <div
                        v-if="active"
                        class="display-3 flex-grow-1 text-center"
                      >
                        Locker
                      </div>
                    </v-scroll-y-transition>
                  </v-card>
                </v-item>
              </v-col>
              
              <v-col
                md="3"
                offset-md="3"
              >
                <v-item v-slot:default="{ active, toggle }">
                  <v-card
                    v-if="type === 'cards'"
                    :color="active ? 'deep-purple accent-2' : ''"
                    class="d-flex align-center"
                    dark
                    height="27vh"
                    @mouseover="toggle"
                  >
                    <v-scroll-y-transition>
                      <div
                        v-if="active"
                        class="display-3 flex-grow-1 text-center"
                      >
                        Locker
                      </div>
                    </v-scroll-y-transition>
                  </v-card>
                </v-item>
              </v-col>

              <v-col
                v-for="n in 1"
                :key="`b-${n}`"
                cols="12"
                md="3"
              >
                <v-item v-slot:default="{ active, toggle }">
                  <v-card
                    v-if="type === 'cards'"
                    :color="active ? 'deep-purple accent-2' : ''"
                    class="d-flex align-center"
                    dark
                    height="27vh"
                    @mouseover="toggle"
                  >
                    <v-scroll-y-transition>
                      <div
                        v-if="active"
                        class="display-3 flex-grow-1 text-center"
                      >
                        Locker
                      </div>
                    </v-scroll-y-transition>
                  </v-card>
                </v-item>
              </v-col>
            </v-row>
          </v-container>
        </v-item-group>
      </div>

      <div class="col-md-3">
        <!-- <ul class="list-unstyled">
          <li
            v-for="(myPost, index) in myPostList"
            :key="index"
          >
            {{ myPost.title }}
            {{ myPost.content }}
          </li>
        </ul> -->
      </div>

    </div>
  </v-container>
</template>

<script>
// import { mapState } from "vuex"

export default {
  name: 'mainPostList',
  components: {

  },
  computed: {
    // ...mapState(["myPostList"])
  },
  watch: {
    multiple (val) {
      this.selected = (val)
        ? this.selected >= 0 ? [this.selected] : []
        : this.selected.pop()
    }
  },
  data: function () {
    return {
      mandatory: false,
      multiple: true,
      selected: null,
      types: [
        'cards',
      ],
      type: 'cards',
    }
  },
  created() {
  
  },
}
</script>

<style>

</style>