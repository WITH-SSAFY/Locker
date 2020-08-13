<template>
  <div class="">
    <!-- <div class="">
      <h1 style="margin: 0 3rem; font-size: 3.3rem;"><strong>LOCKER</strong></h1>
      <div class="main-under-line my-3" style="margin: 0 3rem;"></div>
    </div> -->
    <!-- <div class="m-5">
      <p style="positon: relative; font-size: 1.5rem;">The 5 Best Repositories</p>
    </div> -->
    <div class="cont s--inactive">
      <!-- cont inner start -->
      <div class="cont__inner">
        <!-- el start -->
        <div class="el">
          <div class="el__overflow">
            <div class="el__inner">
              <div class="el__bg"></div>
              <div class="el__preview-cont">
                <h2 class="el__heading">Section 1</h2>
              </div>
              <div class="el__content">
                <div class="el__text">Whatever</div>
                <div class="el__close-btn"></div>
              </div>
            </div>
          </div>
          <div class="el__index">
            <div class="el__index-back">1</div>
            <div class="el__index-front">
              <div class="el__index-overlay" data-index="1">1</div>
            </div>
          </div>
        </div>
        <!-- el end -->
        <!-- el start -->
        <div class="el">
          <div class="el__overflow">
            <div class="el__inner">
              <div class="el__bg"></div>
              <div class="el__preview-cont">
                <h2 class="el__heading">Section 2</h2>
              </div>
              <div class="el__content">
                <div class="el__text">Whatever</div>
                <div class="el__close-btn"></div>
              </div>
            </div>
          </div>
          <div class="el__index">
            <div class="el__index-back">2</div>
            <div class="el__index-front">
              <div class="el__index-overlay" data-index="2">2</div>
            </div>
          </div>
        </div>
        <!-- el end -->
        <!-- el start -->
        <div class="el">
          <div class="el__overflow">
            <div class="el__inner">
              <div class="el__bg"></div>
              <div class="el__preview-cont">
                <h2 class="el__heading">Section 3</h2>
              </div>
              <div class="el__content">
                <div class="el__text">Whatever</div>
                <div class="el__close-btn"></div>
              </div>
            </div>
          </div>
          <div class="el__index">
            <div class="el__index-back">3</div>
            <div class="el__index-front">
              <div class="el__index-overlay" data-index="3">3</div>
            </div>
          </div>
        </div>
        <!-- el end -->
        <!-- el start -->
        <div class="el">
          <div class="el__overflow">
            <div class="el__inner">
              <div class="el__bg"></div>
              <div class="el__preview-cont">
                <h2 class="el__heading">Section 4</h2>
              </div>
              <div class="el__content">
                <div class="el__text">Whatever</div>
                <div class="el__close-btn"></div>
              </div>
            </div>
          </div>
          <div class="el__index">
            <div class="el__index-back">4</div>
            <div class="el__index-front">
              <div class="el__index-overlay" data-index="4">4</div>
            </div>
          </div>
        </div>
        <!-- el end -->
        <!-- el start -->
        <div class="el">
          <div class="el__overflow">
            <div class="el__inner">
              <div class="el__bg"></div>
              <div class="el__preview-cont">
                <h2 class="el__heading">Section 5</h2>
              </div>
              <div class="el__content">
                <div class="el__text">Whatever</div>
                <div class="el__close-btn"></div>
              </div>
            </div>
          </div>
          <div class="el__index">
            <div class="el__index-back">5</div>
            <div class="el__index-front">
              <div class="el__index-overlay" data-index="5">5</div>
            </div>
          </div>
        </div>
        <!-- el end -->
      </div>
      <!-- cont inner end -->
      
    </div>
  </div>
  

</template>

<script>
// import $ from 'jquery'
import '../../assets/css/main_post.scss'

export default {
  name: 'mainPostList',
  components: {

  },
  computed: {

  },
  data: function () {
    return {

    }
  },
  created() {

  },
  mounted() {
    var $cont = document.querySelector('.cont');
    var $elsArr = [].slice.call(document.querySelectorAll('.el'));
    var $closeBtnsArr = [].slice.call(document.querySelectorAll('.el__close-btn'));

    setTimeout(function() {
      $cont.classList.remove('s--inactive');
    }, 200);

    $elsArr.forEach(function($el) {
      $el.addEventListener('click', function() {
        if (this.classList.contains('s--active')) return;
        $cont.classList.add('s--el-active');
        this.classList.add('s--active');
      });
    });

    $closeBtnsArr.forEach(function($btn) {
      $btn.addEventListener('click', function(e) {
        e.stopPropagation();
        $cont.classList.remove('s--el-active');
        document.querySelector('.el.s--active').classList.remove('s--active');
      });
    });
  },
}
</script>

<style>

</style>