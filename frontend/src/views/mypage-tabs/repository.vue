<template>
  <div class="repo">
    <div class="naccs">
      <div class="grid">
      <div class="gc gc--1-of-3">
        <div class="py-5" style="color: #7C4DFF; font-size: 3rem;"><span>Continue with</span></div>
        <div class="menu">
          <div class="active"><span class="light"></span><span>My Repository</span></div>
          <div><span class="light"></span><span>Organization</span></div>
        </div>
      </div>
      <div class="gc gc--2-of-3">
        <ul class="nacc">
          <li class="active">
            <div>
    
            </div>
          </li>
          <li>
            <div>

            </div>
          </li>
        </ul>
      </div>
      </div>
    </div>



  </div>
  
</template>

<script>
import $ from 'jquery'
import '../../assets/css/repository.scss'

export default {
  mounted () {
    $(document).on("click", ".naccs .menu div", function() {
    var numberIndex = $(this).index();

    if (!$(this).is("active")) {
        $(".naccs .menu div").removeClass("active");
        $(".naccs ul li").removeClass("active");

        $(this).addClass("active");
        $(".naccs ul").find("li:eq(" + numberIndex + ")").addClass("active");

        var listItemHeight = $(".naccs ul")
            .find("li:eq(" + numberIndex + ")")
            .innerHeight();
        $(".naccs ul").height(listItemHeight + "px");
    }
});
  }
}
</script>

<style>

</style>