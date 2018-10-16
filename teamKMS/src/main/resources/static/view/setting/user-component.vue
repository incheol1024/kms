<template>
<v-layout column>
	<v-flex>
		<v-data-table :headers="headers" :items="items"  :pagination.sync="pagination" :loading="loading"
		 	 :total-items="totalitems" class="elevation-1">
		    <template slot="items" slot-scope="props">
		      <td>{{ props.item.id }}</td>
		      <td>{{ props.item.name }}</td>
		      <td>{{ props.item.type }}</td>
		      <td>{{ props.item.groupId }}</td>
		    </template>
		</v-data-table>
	</v-flex>
	
	<v-flex>
		<tree :data="treeData"/>
	</v-flex>
</v-layout>
</template>

<script>
  module.exports =  {
		  created: function () {
				var _this = this;
				axios.post("getUserList", PageModel)
				.then(function (response) {
					for(i = 0 ; i < response.data.content.length; i++)
						_this.items.push(response.data.content[i]);
				}).catch(function (error) {
					console.log(error);
				})
			},
	 data: () => ({
		 totalitems: 0,
	     loading: true,
	     pagination: {},
		  headers: [
	          { text: 'id', value: 'id' },
	          { text: 'name', value: 'name' },
	          { text: 'type', value: 'type' },
	          { text: 'groupId', value: 'groupId' }
	        ],
	        items: [
	         
	        ],
	        treeData: [
	            { text: 'Item 1' },
	            { text: 'Item 2' },
	            { text: 'Item 3' },
	            { text: 'Item 4' }
	        ]
     }),
	 methods: {
		 
	 }
  }
</script>