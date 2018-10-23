<template>
<v-layout column>
  
  <v-flex>  
    <v-layout align-center>
      <v-treeview :items="items" activatable :active.sync="active" active-class="primary--text" selectable selected-color="primary"
        expand-icon="keyboard_arrow_down" on-icon="bookmark" off-icon="bookmark_border" indeterminate-icon="book">       
      </v-treeview>
          
      <v-tooltip bottom>          
        <v-icon slot="activator" color="pink" @click="Move">arrow_forward</v-icon>        
        <span>Move Group</span>
      </v-tooltip>

      <v-treeview :items="items" activatable :active.sync="activeSub" active-class="primary--text" selectable selected-color="primary"
        expand-icon="keyboard_arrow_down" on-icon="bookmark" off-icon="bookmark_border" indeterminate-icon="book">	          
      </v-treeview>
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
      <v-text-field v-model="curitem.name" label="Name"></v-text-field>
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
        _this.items.push(response.data);
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  data: () => ({
    active: [],
    activeSub: [],
    items: [],
    childitems: [],
    search: "",
    loading: true,
    headers: [{ text: "name", value: "name" }],
    dialog: false,
    updateMode: false,
    curitem: JSON.parse(JSON.stringify(GroupModel))
  }),
  watch: {
    active: function active() {
      var _this = this;
      _this.childitems = [];
      this.loading = true;
      axios
        .post("getAllChildList", { id: this.selected() })
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
  },
  methods: {
    confirm: function confirm() {
      console.log("aaa");
      if (this.updateMode) {
        axios
          .post("updateGroup", this.curitem)
          .then(function(response) {})
          .catch(function(error) {
            console.log(error);
          });
      } else {
        var _this =  this;
        var temp = JSON.parse(JSON.stringify(GroupModel));
        temp.parentid = this.curitem.id;
        temp.name = this.curitem.name;
        axios
          .post("addGroup", temp)
          .then(function(response) {
            if(!_this.curitem.children)
              _this.curitem.children = [];
            temp.id = response.data;
            temp.children = null;
            _this.curitem.children.push(temp);
          })
          .catch(function(error) {
            console.log(error);
          });
      }
      this.dialog = false;
    },
    NewItem: function NewItem() {
      console.log("bb");
      this.dialog = true;
      var id = this.selected();
      this.curitem = this.recurFind(this.items[0], id);
      this.updateMode = false;
    },
    EditName: function EditName() {
      console.log("bb");
      this.dialog = true;
      var id = this.selected();
      this.curitem = this.recurFind(this.items[0], id);
      this.updateMode = true;
    },
    Move: function Move() {
      //TODO tomorrow
      var id = this.selected();
      var moved = this.subSelected();
      axios
        .post("updateGroup", { id: this.selected(), parentid: moved })
        .then(function(response) {


        })
        .catch(function(error) {
          console.log(error);
        });
    },
    DeleteItem: function DeleteItem() {
      var curId = this.selected();
      var _this = this;
      axios
        .post("deleteGroup", { id: curId })
        .then(function(response) {
          cur = _this.recurFind(_this.items[0], curId);
          parent = _this.recurFind(_this.items[0], cur.parentid);
          parent.children.splice(parent.children.indexOf(cur), 1);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    selected() {
      if (!this.active.length) return undefined;
      const id = this.active[0];
      return id;
    },
    subSelected() {
      if (!this.activeSub.length) return undefined;
      const id = this.activeSub[0];
      return id;
    },
    recurFind(item, id) {
      if (item.id === id) return item;
      if (item.children) {
        for (i = 0; i < item.children.length; i++) {
          return this.recurFind(item.children[i], id);
        }
      }
    }
  }
};
</script>
