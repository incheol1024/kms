<template>
<v-layout column>
  
  <v-flex>  
    <v-layout align-center>
      <tree-component ref="tree" :items="items" :busname="'tree'" :cachekey="'id'"></tree-component>
          
      <v-tooltip bottom>          
        <v-icon slot="activator" color="pink" @click="Move">arrow_forward</v-icon>        
        <span>Move Group</span>
      </v-tooltip>

     <tree-component ref="subtree" :items="items" :busname="'subtree'" :cachekey="'id'"></tree-component>
    </v-layout>		  
  </v-flex>        

  <v-flex >
    <v-layout align-center justify-center>
      <v-btn color="primary" @click="NewItem">New</v-btn>
      <v-btn color="primary" @click="EditName">EditName</v-btn>    
      <v-btn color="primary" @click="DeleteItem">Delete</v-btn>
    </v-layout>
  </v-flex>

  <v-flex>
    <v-card>
      <v-card-title>Child User, Group 
        <v-spacer></v-spacer>
          <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
        </v-card-title>
        <v-data-table :headers="headers" :items="childitems" :loading="loading" :search="search" class="elevation-1">
          <template slot="items" slot-scope="props">
            <td>{{ props.item.name }}</td>       
          </template>
        </v-data-table>
    </v-card>
  </v-flex>

<v-dialog v-model="dialog" width="500">
  <v-card>
    <v-card-title class="headline primary lighten-2" primary-title>
      Set New Name
    </v-card-title>
    <v-card-text>
      <v-text-field v-model="newname" label="Name"></v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="primary" flat @click="confirm">Confirm</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>

</v-layout>
</template>

<script>
module.exports = {
  created: function() {
    var _this = this;
    axios
      .post("getAllGroupList")
      .then(function(response) {
        _this.items = response.data;
        _this.$refs.tree.recurCache(_this.items);
        _this.$refs.subtree.recurCache(_this.items);
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  data: () => ({
    items: {},
    childitems: [],
    newname: "",
    search: "",
    loading: false,
    headers: [{ text: "name", value: "name" }],
    dialog: false,
    updateMode: false
  }),
  watch: {
    actived: function actived() {
      var _this = this;
      if (_this.actived.length > 0) {
        _this.childitems = [];
        this.loading = true;
        axios
          .post("getAllChildList", { id: _this.$refs.tree.active.items.id })
          .then(function(response) {
            for (i = 0; i < response.data.groupList.length; i++) {
              _this.childitems.push(response.data.groupList[i]);
            }
            for (i = 0; i < response.data.userList.length; i++) {
              _this.childitems.push(response.data.userList[i]);
            }
            _this.loading = false;
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    }
  },
  methods: {
    confirm: function confirm() {
      var _this = this;
      if (this.updateMode) {
        _this.$refs.tree.active.items.name = _this.newname;
        axios
          .post("updateGroup", _this.$refs.tree.active.items)
          .then(function(response) {
            _this.$refs.tree.updateNode(_this.newname);
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        var temp = JSON.parse(JSON.stringify(GroupModel));
        temp.parentid = _this.$refs.tree.active.items.id;
        temp.name = this.newname;
        axios
          .post("addGroup", temp)
          .then(function(response) {
            temp.id = response.data;
            _this.$refs.tree.addNode(temp);
          })
          .catch(function(error) {
            console.log(error);
          });
      }
      this.dialog = false;
    },
    NewItem: function NewItem() {
      this.dialog = true;
      this.updateMode = false;
      this.newname = "";
    },
    EditName: function EditName() {
      this.dialog = true;
      this.updateMode = true;
      this.newname = this.$refs.tree.active.items.name;
    },
    Move: function Move() {
       console.log('a');
      var _this = this;
      axios
        .post("updateGroup", {
          id: _this.$refs.tree.active.items.id,
          parentid: _this.$refs.subtree.active.items.id
        })
        .then(function(response) {
          this.$refs.tree.moveNode(_this.$refs.subtree.active.items.id);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    DeleteItem: function DeleteItem() {
      var _this = this;
      axios
        .post("deleteGroup", { id: _this.$refs.tree.active.items.id })
        .then(function(response) {
          _this.$refs.tree.deleteNode();
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>
