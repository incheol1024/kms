<template>
<v-content>
	site {{id}}
	<v-btn color="primary"  @click="writepage">Write</v-btn> 
</v-content>
</template>

<script>
  module.exports =  {
	 props : ['id'],
	 data: () => ({
		 
     }),
	 methods: {
		 writepage : function writepage(){
		 	router.push("/sites/write/" + this.id);
		 }
	 }
  }
</script>