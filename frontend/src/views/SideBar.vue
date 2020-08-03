<template>
  <v-flex class="side">
    <div class="diagonal"></div>
    <div class="tag-list-wrap">
      <div class="title">Categories({{totalTagNum}})</div>
      <ul class="tag-list"> 
          <li v-for="(tag, t) in tags" :key="t">
              <a href="#">#{{tag.name}} ({{tag.num}})</a>
          </li> 
      </ul>
    </div>
  </v-flex>
</template>

<script>
  export default {
    data: () => ({
      totalTagNum: 30,
      tags: [
        { name: "python", num: 10 },
        { name: "java", num: 12 },
        { name: "vue.js", num: 8 }, 
      ],
    })
  }
</script>