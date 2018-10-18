<template>
<v-layout column>
	<v-flex>
		<v-treeview :items="items"></v-treeview>
	</v-flex>
</v-layout>
</template>

<script>
module.exports =  {
	created  : function() {
		var _this = this;
		axios.post("getAllGroupList")
		.then(function (response) {
			console.log(response.data);
			_this.items.push(response.data);
		}).catch(function (error) {
			console.log(error);
		})		
	},
	data: () => ({
		items : []
	})
}
</script>
