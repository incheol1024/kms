<template>
<v-content>
	qna {{id}}
	<v-btn color="primary" @click="writepage">Write</v-btn> 
</v-content>
</template>

<script>
  module.exports =  {
	 props : ['id'],
	 data: () => ({
		 
     }),
	 methods: {
		 writepage : function writepage(){
		 	router.push("/qna/write/" + this.id);
		 }
	 }
  }
</script>