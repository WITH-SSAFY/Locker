<template>
  <div class="locker-container">
    <div class="row">
      <div class="col-md-4">
        <div style="margin-left: 4.5rem;">
          <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>의 공지사항</p>
          <div class="under-line"></div>
        </div>
        <section id="container">
          <ul id="box" class="medium" style="font-size: 1.1rem;">
            <li>공지 1. Lorem ipsum dolor sit amet.</li>
            <li>공지 2. Lorem ipsum dolor sit amet.</li>
            <li>공지 3. Lorem ipsum dolor sit amet.</li>
            <li>공지 4. Lorem ipsum dolor sit amet.</li>
          </ul>
        </section>

      </div>
      <div class="col-md-8">
        <div style="margin-left: 4.5rem;">
          <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>가 선정한 이 달의 레포지토리</p>
          <div class="under-line"></div>
        </div>
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
          </div>
          <!-- cont inner end -->
        </div>  
      </div>
    </div>
  </div>
  

</template>

<script>
import $ from 'jquery'
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

    // 공지사항
    function rotator() {
		
		$('ul#box li:first-child').slideUp(1000, function() {
			$(this).appendTo($('ul#box'));
		});
		
	}

 	setInterval(rotator, 3000);
  },
}
</script>

<style scoped>
  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7C4DFF;
  }

  section#container {
    overflow: hidden;
    border: solid 3px #eceffc;
    width: 500px;
    margin-left: 4rem;
    padding: 10px 20px;
    height: 150px;

  }

  ul#box {
    margin: 0;
    padding: 0; 
  }

  ul#box li {
    list-style: none;
    height: 45px;
    margin: 15px 0;
    border-bottom: solid 1.3px #7C4DFF;
    padding-bottom: 10px;
  }
  
</style>