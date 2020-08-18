<template>
  <div>
    <div>
      <section class="strips">

        <article
          class="strips__strip"
          v-for="member in members"
          :key="member.idx"
        >
          <div class="strip__content">
            <h1 class="strip__title">{{ member }}</h1>
            <div class="strip__inner-text">
              <p>어쩌고</p>
            </div>
          </div>
        </article>

        <v-icon class="strip__close" color="white">mdi-close</v-icon>
      </section>
    </div>
  </div>

</template>

<script>
import $ from 'jquery'
import '../assets/css/member_info.scss'

export default {
  name: 'memberInfo',
  components: {

  },
  computed: {

  },
  data: function () {
    return {
      members : ['소개', '김윤진', '김준호', '배현석', '윤재원']
    }
  },
  created() {

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

</style>