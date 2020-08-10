<template>
  <div>
    <div>
      <section class="strips">
        <article class="strips__strip">
          <div class="strip__content">
            <h1 class="strip__title" data-name="Lorem">Awesome</h1>
            <div class="strip__inner-text">
              <h2>Ettrics</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia sapiente deserunt consectetur, quod reiciendis corrupti quo ea aliquid! Repellendus numquam quo, voluptate. Suscipit soluta omnis quibusdam facilis, illo voluptates odit!</p>
              <p>
                <a href="https://twitter.com/ettrics" target="_blank"></a>
              </p>
            </div>
          </div>
        </article>
        <article class="strips__strip">
          <div class="strip__content">
            <h1 class="strip__title" data-name="Ipsum">Info</h1>
            <div class="strip__inner-text">
              <h2>Ettrics</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia sapiente deserunt consectetur, quod reiciendis corrupti quo ea aliquid! Repellendus numquam quo, voluptate. Suscipit soluta omnis quibusdam facilis, illo voluptates odit!</p>
              <p>
                <a href="https://twitter.com/ettrics" target="_blank"></a>
              </p>
            </div>
          </div>
        </article>
        <article class="strips__strip">
          <div class="strip__content">
            <h1 class="strip__title" data-name="Dolor">Go</h1>
            <div class="strip__inner-text">
              <h2>Ettrics</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia sapiente deserunt consectetur, quod reiciendis corrupti quo ea aliquid! Repellendus numquam quo, voluptate. Suscipit soluta omnis quibusdam facilis, illo voluptates odit!</p>
              <p>
                <a href="https://twitter.com/ettrics" target="_blank"></a>
              </p>
            </div>
          </div>
        </article>
        <article class="strips__strip">
          <div class="strip__content">
            <h1 class="strip__title" data-name="Sit">Inside</h1>
            <div class="strip__inner-text">
              <h2>Ettrics</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia sapiente deserunt consectetur, quod reiciendis corrupti quo ea aliquid! Repellendus numquam quo, voluptate. Suscipit soluta omnis quibusdam facilis, illo voluptates odit!</p>
              <p>
                <a href="https://twitter.com/ettrics" target="_blank"></a>
              </p>
            </div>
          </div>
        </article>

        <article class="strips__strip">
          <div class="strip__content">
            <h1 class="strip__title" data-name="Amet">Here</h1>
            <div class="strip__inner-text">
              <h2>Ettrics2</h2>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia sapiente deserunt consectetur, quod reiciendis corrupti quo ea aliquid! Repellendus numquam quo, voluptate. Suscipit soluta omnis quibusdam facilis, illo voluptates odit!</p>
              <p>
                <a href="https://twitter.com/ettrics" target="_blank"></a>
              </p>
            </div>
          </div>
        </article>

        <v-icon class="strip__close" color="white">mdi-close</v-icon>
      </section>
    </div>

    <v-container>
      <div style="height: 6rem;"></div>
    </v-container>

    <!-- <div>
      <p style="background-color: #333; color: #fff;">전체 글 목록 (임시)</p>
      <ul class="list-unstyled">
        <li
          v-for="post in postList"
          :key="post.pid"
          class="my-3"
        >
          <strong>{{ post.nickname }}</strong>
          <span>{{ post.title }}</span>
          <span>{{ post.content }}</span>
        </li>
      </ul>
    </div> -->

  </div>

</template>

<script>
import $ from 'jquery'
import '../../assets/css/main_post.scss'
import { mapState } from "vuex"

export default {
  name: 'mainPostList',
  components: {

  },
  computed: {
    ...mapState(["postList"])
  },
  data: function () {
    return {

    }
  },
  created() {
    this.$store.dispatch('getPostList')
    this.postList
  },
  mounted() {
    var Expand = (function() {
      var tile = $('.strips__strip');
      var tileLink = $('.strips__strip > .strip__content');
      var tileText = tileLink.find('.strip__inner-text');
      var stripClose = $('.strip__close');
      
      var expanded  = false;

      var open = function() {
          
        var tile = $(this).parent();

          if (!expanded) {
            tile.addClass('strips__strip--expanded');
            // add delay to inner text
            tileText.css('transition', 'all .5s .3s cubic-bezier(0.23, 1, 0.32, 1)');
            stripClose.addClass('strip__close--show');
            stripClose.css('transition', 'all .6s 1s cubic-bezier(0.23, 1, 0.32, 1)');
            expanded = true;
          } 
        };
      
      var close = function() {
        if (expanded) {
          tile.removeClass('strips__strip--expanded');
          // remove delay from inner text
          tileText.css('transition', 'all 0.15s 0 cubic-bezier(0.23, 1, 0.32, 1)');
          stripClose.removeClass('strip__close--show');
          stripClose.css('transition', 'all 0.2s 0s cubic-bezier(0.23, 1, 0.32, 1)')
          expanded = false;
        }
      }

        var bindActions = function() {
          tileLink.on('click', open);
          stripClose.on('click', close);
        };

        var init = function() {
          bindActions();
        };

        return {
          init: init
        };

      }());

    Expand.init();
  },
}
</script>

<style>
.box {
  box-sizing: border-box;
  border: 5px solid transparent;
  background-clip: padding-box;
  background-color: #333;
}
</style>