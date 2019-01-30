<template>
    <v-layout column>

        <v-flex class="mb-5">
            <v-layout align-center>
                <v-card raised>
                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">Current Group</h3>
                        </div>
                    </v-card-title>
                    <tree-component ref="tree" :items="items" :busname="'tree'" :cachekey="'id'"
                                    @nodeevent="actived"></tree-component>
                    <v-btn color="primary" @click="NewItem">New</v-btn>
                    <v-btn color="primary" @click="EditName">EditName</v-btn>
                    <v-btn color="primary" @click="DeleteItem">Delete</v-btn>
                </v-card>

                <v-tooltip bottom>
                    <v-icon slot="activator" color="pink" @click="Move">arrow_forward</v-icon>
                    <span>Move Group</span>
                </v-tooltip>

                <v-card raised>
                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">Moved Group</h3>
                        </div>
                    </v-card-title>
                    <tree-component ref="subtree" :items="items" :busname="'subtree'" :cachekey="'id'"></tree-component>
                </v-card>
            </v-layout>
        </v-flex>

        <v-flex>
            <v-card raised>
                <v-card-title>Child User, Group
                    <v-spacer></v-spacer>
                    <v-text-field v-model="search" append-icon="search" label="Search" single-line
                                  hide-details></v-text-field>
                </v-card-title>
                <v-data-table :headers="headers" :items="childitems" :loading="loading" :search="search"
                              class="elevation-1">
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
        created: function () {
            var _this = this;
            axios.get("group")
                .then(value => {
                    _this.items = response.data;
                    _this.$refs.tree.recurCache(_this.items);
                    _this.$refs.subtree.recurCache(_this.items);
                }).catch(catchPromise);
        },
        data: () => ({
            items: {},
            childitems: [],
            newname: "",
            search: "",
            loading: false,
            headers: [{text: "name", value: "name"}],
            dialog: false,
            updateMode: false
        }),
        methods: {
            actived: function actived() {
                let _this = this;
                this.loading = true;
                if (_this.$refs.tree.active.items) {
                    _this.childitems = [];
                    axios.get("group/child/" + _this.$refs.tree.active.items.id)
                        .then(response => {
                            for (let i = 0; i < response.data.groupList.length; i++)
                                _this.childitems.push(response.data.groupList[i]);
                            for (let i = 0; i < response.data.userList.length; i++)
                                _this.childitems.push(response.data.userList[i]);
                        }).catch(catchPromise);
                }
                _this.loading = false;
            },
            confirm: function confirm() {
                let _this = this;
                if (this.updateMode) {
                    _this.$refs.tree.active.items.name = _this.newname;
                    axios.post("group", _this.$refs.tree.active.items)
                        .then(value => _this.$refs.tree.updateNode(_this.newname))
                        .catch(catchPromise);
                } else {
                    let temp = JSON.parse(JSON.stringify(GroupModel));
                    temp.parentId = _this.$refs.tree.active.items.id;
                    temp.name = this.newname;
                    axios.put("group", temp)
                        .then(value => {
                            temp.id = response.data;
                            _this.$refs.tree.addNode(temp)
                        })
                        .catch(catchPromise);
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
                let _this = this;
                let temp = JSON.parse(JSON.stringify(GroupModel));
                temp.id = _this.$refs.tree.active.items.id;
                temp.parentId = _this.$refs.subtree.active.items.id;
                temp.name = _this.$refs.tree.active.items.name;
                axios.post("group", temp)
                    .then(value => _this.$refs.tree.moveNode(_this.$refs.subtree.active.items.id))
                    .catch(catchPromise);
            },
            DeleteItem: function DeleteItem() {
                let _this = this;
                axios.delete("group/" + _this.$refs.tree.active.items.id)
                    .then(value => _this.$refs.tree.deleteNode())
                    .catch(catchPromise);
            }
        }
    };
</script>
