.
<template>
    <v-layout column>

        <v-flex class="mb-5">
            <v-layout align-center>
                <v-card raised>
                    <v-card-title primary-title>
                        <div>
                            <h4 class="headline mb-0">Current Group</h4>
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
                            <h4 class="headline mb-0">Moved Group</h4>
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
                <table-component ref="table" :headers="headers" :search="search"></table-component>
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
            axios.get("group").then(value => {
                    this.items = value.data;
                    this.$refs.tree.recurCache(this.items);
                    this.$refs.subtree.recurCache(this.items);
                }).catch(reason => catchPromise(reason));
        },
        data: () => ({
            items: {},
            newname: "",
            search: "",
            headers: [
                {text: "name", value: "name", sortable : false},
                {text: "type", value: "type", sortable : false},
            ],
            dialog: false,
            updateMode: false
        }),
        methods: {
            actived: function actived(page) {
                let _this = this;
                _this.$refs.table.clear();
                if (_this.$refs.tree.active.items) {
                    axios.get("group/child/" + _this.$refs.tree.active.items.id, {
                        params : page
                    }).then(response => {
                        let groupList = response.data.groupList.content;
                        let userList = response.data.userList.content;
                        _this.$refs.table.total = groupList.length + userList.length;
                        for (let i = 0; i < groupList.length; i++) {
                            groupList[i].type = "Group";
                            _this.$refs.table.addFunction(groupList[i]);
                        }
                        for (let i = 0; i < userList.length; i++) {
                            userList[i].type = "User";
                            _this.$refs.table.addFunction(userList[i]);
                        }
                    }).catch(reason => catchPromise(reason));
                }
            },
            confirm: function confirm() {
                let _this = this;
                if (this.updateMode) {
                    _this.$refs.tree.active.items.name = _this.newname;
                    axios.post("group", _this.$refs.tree.active.items)
                        .then(res => {_this.$refs.tree.updateNode(_this.newname)})
                        .catch(reason => catchPromise(reason));
                } else {
                    let temp = copyObject( GroupModel);
                    temp.parentId = _this.$refs.tree.active.items.id;
                    temp.name = this.newname;
                    axios.put("group", temp).then(value => {
                        temp.id = value.data;
                        _this.$refs.tree.addNode(temp)
                    }).catch(reason => catchPromise(reason));
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
                let temp = copyObject(GroupModel);
                temp.id = _this.$refs.tree.active.items.id;
                temp.parentId = _this.$refs.subtree.active.items.id;
                temp.name = _this.$refs.tree.active.items.name;
                axios.post("group", temp)
                    .then(res => {_this.$refs.tree.moveNode(_this.$refs.subtree.active.items.id)})
                    .catch(reason => catchPromise(reason));
            },
            DeleteItem: function DeleteItem() {
                let _this = this;
                axios.delete("group/" + _this.$refs.tree.active.items.id)
                    .then(res => {_this.$refs.tree.deleteNode()})
                    .catch(reason => catchPromise(reason));
            }
        }
    };
</script>
