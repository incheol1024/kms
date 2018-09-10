<template>
<v-content>
	<v-container fluid fill-height>
		<span>search title-component.vue</span>
		<v-btn @click="sendClick">Send Message Test</v-btn>
	</v-container>
</v-content>
</template>

<script>
  module.exports =  {
	 data: () => ({
		 curItem : JSON.parse(JSON.stringify(MessageDto))
     }),
	 methods: {
		 sendClick : function deleteClick(item,index){
				var _this = this;
				axios.post("sendmessage", {
					body : JSON.stringify(_this.curItem)					
				}).then(function (response) {
					if (response.status === 200) {
						console.log('suc');
					} else {
						console.log(response.data);
					}
				}).catch(function (error) {
					console.log(error);
				});		
			}
	 }
  }
</script>