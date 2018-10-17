<template>
<v-layout column>
	<v-flex>
		<v-treeview :active.sync="active" :items="items" :load-children="fetchGroups"
            :open.sync="open" activatable active-class="primary--text" class="grey lighten-5"
            open-on-click>
            <v-icon v-if="!item.children" slot="prepend"
              slot-scope="{ item, active }" :color="active ? 'primary' : ''">
              mdi-account
            </v-icon>
          </v-treeview>
	</v-flex>
</v-layout>
</template>

<script>
//this.users.find(user => user.id === id)
/*  axios.post("getGroupChild",GroupModel)
		 .then(function (response) {
		 	 for (var i = 0; i < response.data.length; i++) {
		 		
			 }
		 }).catch(function (error) {
			 console.log(error);
		 }) */
 module.exports =  {
	 data: () => ({
		    active: [],
		    open: [],
		    groups: []
		  }),

		  computed: {
		    items () {
		      return [
		        {
		          name: 'Root',
		          children: this.groups
		        }
		      ]
		    }
		  },
		  methods: {
			  fetchGroups () {
		      
		    }
		  }
}
</script>
