<template>
<v-layout column>
	<v-flex>
		<v-card>
    		<v-card-title>
      			User List
      			<v-spacer></v-spacer>
	        	<v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
    		</v-card-title>
  			<v-data-table :headers="headers" :items="items" :loading="loading" :search="search" class="elevation-1">
		    	<template slot="items" slot-scope="props">
		      		<td>{{ props.item.id }}</td>
		      		<td>{{ props.item.name }}</td>
		      		<td>{{ props.item.type }}</td>
		      		<td>{{ props.item.groupId }}</td>
		    	</template>
			</v-data-table>
  		</v-card>	
	</v-flex>
	
	<v-flex>
		<v-layout>
			<v-flex>
				<div>user infomation</div>
			</v-flex>
			<v-flex>
				
			</v-flex>
		</v-layout>
	</v-flex>
</v-layout>
</template>

<script>
module.exports =  {
	 created  : function() {
		 this.loading = true;
		 var _this = this;
		 axios.post("getUserList")
		 .then(function (response) {
			 for (var i = 0; i < response.data.length; i++) {
				 _this.items.push(response.data[i]);
			 }
		 }).catch(function (error) {
			 console.log(error);
		 })
	     this.loading = false;
	 },
	 data: () => ({
		 search: '',
	     loading: true,
	     pagination: {},
		  headers: [
	          { text: 'id', value: 'id' },
	          { text: 'name', value: 'name' },
	          { text: 'type', value: 'type' , sortable: false},
	          { text: 'groupId', value: 'groupId' , sortable: false }
	        ],
	        items: [
	         
	        ]
     }),
	 methods: {
		 
	 }
}
</script>