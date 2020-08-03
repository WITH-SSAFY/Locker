<template>
  <div>
    <h1>MyPost Test!!</h1>
  </div>
</template>

<script>
  export default {
    data: () =>{
      return{
        isNotMain: true,
        myPost: [
            { title: "title1", content: "content1"},
            { title: "title2", content: "content2"},
            { title: "title3", content: "content3"},
        ]
      }
    }
  }
</script>