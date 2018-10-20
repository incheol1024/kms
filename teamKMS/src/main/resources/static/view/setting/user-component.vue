<template>
<v-layout column>
	<v-flex class="mb-5">
		<v-card>
    		<v-card-title>
      			User List
      			<v-spacer></v-spacer>
	        	<v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                <v-btn color="primary" dark class="mb-2" @click="addItem">New User</v-btn>
    		</v-card-title>
  			<v-data-table :headers="headers" :items="items" :loading="loading" :search="search" class="elevation-1">
		    	<template slot="items" slot-scope="props">
		      		<td>{{ props.item.id }}</td>
		      		<td>{{ props.item.name }}</td>
		      		<td>{{ props.item.type }}</td>
		      		<td>{{ props.item.groupId }}</td>
                    <td>
                      <v-icon small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                      <v-icon small @click="deleteItem(props.item)">delete</v-icon>
                    </td>
		    	</template>
			</v-data-table>
  		</v-card>	
	</v-flex>
	
	<v-flex>
		<v-stepper v-model="stage">
          <v-stepper-header>
            <v-stepper-step :complete="stage > 1" step="1">Input Infomation</v-stepper-step>
            <v-divider></v-divider>
            <v-stepper-step :complete="stage > 2" step="2">Select Group</v-stepper-step>
          </v-stepper-header>

        <v-stepper-items>
          <v-stepper-content step="1">
            <v-card class="mb-5" color="grey lighten-1"></v-card>
            <v-btn color="primary" @click="stage = 2">Continue</v-btn>
          </v-stepper-content>

          <v-stepper-content step="2">
            <v-card class="mb-5" color="grey lighten-1"></v-card>
            <v-btn color="primary" @click="kkk">Confirm</v-btn>
          </v-stepper-content>
        </v-stepper-items>
      </v-stepper>
	</v-flex>
</v-layout>
</template>

<script>
module.exports = {
  created: function() {
    this.loading = true;
    var _this = this;
    axios
      .post("getUserList")
      .then(function(response) {
        for (var i = 0; i < response.data.length; i++) {
          _this.items.push(response.data[i]);
        }
      })
      .catch(function(error) {
        console.log(error);
      });
    this.loading = false;
  },
  data: () => ({
    search: "",
    loading: true,
    pagination: {},
    headers: [
      { text: "id", value: "id" },
      { text: "name", value: "name" },
      { text: "type", value: "type", sortable: false },
      { text: "groupId", value: "groupId", sortable: false },
      { text: "actions", value: "actions", sortable: false }
    ],
    items: [],
    curitem: JSON.parse(JSON.stringify(UserModel)),
    stage : 0
  }),
  methods: {
    addItem() {
      this.curitem = JSON.parse(JSON.stringify(UserModel));
    },
    editItem(item) {
      this.curitem = item;
    },
    deleteItem(item) {
      var _this = this;
      if (confirm("Are you sure you want to delete this item?")) {
        axios
          .post("deleteUser", { UserDao: item })
          .then(function(response) {
            _this.items.splice(item, 1);
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    kkk(){
       this.stage = 3;
    }
  }
};
</script>