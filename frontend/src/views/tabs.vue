<template>
  <div>


    <!-- 중첩 라우터 -->
    <!-- <p class="d-flex justify-content-around">
      <router-link to="article" class="p-3 text-decoration-none">
        <span class="tab bold">포스트</span>
      </router-link>
      <router-link to="repository" class="p-3 text-decoration-none">
        <span class="tab bold">레포지토리</span>
      </router-link>
      <router-link to="introduce" class="p-3 text-decoration-none">
        <span class="tab bold">소개</span>
      </router-link>
    </p> -->

    <!-- <div class="box">

      <div>
        <p class="bold" style="font-size: 1.5rem;"><strong>LOCKER </strong>열어보기</p>
        <div class="under-line"></div>
      </div>

      <div class="tab-wrap">
        
        <input type="radio" id="tab1" name="tabGroup1" class="tab">
        <label for="tab1"><span class="bold" style="font-size: 1.1rem;">포스트</span></label>

        <input type="radio" id="tab2" name="tabGroup1" class="tab">
        <label for="tab2"><span class="bold" style="font-size: 1.1rem;">레포지토리</span></label>

        <input type="radio" id="tab3" name="tabGroup1" class="tab">
        <label for="tab3"><span class="bold" style="font-size: 1.1rem;">소개</span></label>


        <div class="tab__content">
          <router-link to="article"></router-link>
          
        </div>

        <div class="tab__content">
          <p>Medium Section</p>
        </div>

        <div class="tab__content">
          <p>Long Section</p>
        </div>
        <router-view></router-view>
      </div>


    </div> -->



  </div>


</template>

<script>
import("../assets/css/tab.scss");

export default {
  name: 'tabs',

}
</script>

<style scoped>
  .under-line {
    height: 0.3rem;
    width: 3.5rem;
    margin-bottom: 3rem;
    background-color: #7C4DFF;
  }
</style>