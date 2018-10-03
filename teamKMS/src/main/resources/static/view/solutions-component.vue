<template>
<v-content>
	solutions
</v-content>
</template>

<script>
  module.exports =  {
	 data: () => ({
		 curItem : JSON.parse(JSON.stringify(MessageDto))
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