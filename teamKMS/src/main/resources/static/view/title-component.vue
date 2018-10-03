<template>
<v-content>
	<v-layout fill-height column justify-center>
		<v-carousel>
    		<v-carousel-item v-for="(item,i) in items" :key="i" :src="item" contain></v-carousel-item>
    	</v-carousel>
    	<v-btn @click="sendClick">Send Message Test</v-btn>
	</v-layout>
	
</v-content>
</template>

<script>
  module.exports =  {
	 data: () => ({
		 curItem : JSON.parse(JSON.stringify(MessageDto)),
		 items : ['../assets/kms1.png','../assets/kms2.jpg','../assets/kms3.png']
     }),
	 methods: {
		 sendClick : function sendClick(item,index){
				var _this = this;
				axios.post("sendmessage", {
					body : JSON.stringify(_this.curItem)					
				}).then(function (response) {
					console.log(response.data);
				}).catch(function (error) {
					console.log(error);
				});		
			}
	 }
  }
</script>