<template>
    <v-layout column>
        <v-flex class="mb-5">
            <v-card>
                <v-card-title> User List
                    <v-spacer></v-spacer>
                    <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                    <v-btn color="primary" dark class="mb-2" @click="addItem">New User</v-btn>
                </v-card-title>
                <table-component ref="table" :headers="headers" :search="search" :allow-edit="true" :allow-delete="true"
                                 :edit-function="editItem" :delete-function="deleteItem" :page-req="page" >
                </table-component>
            </v-card>
        </v-flex>

        <v-flex v-if="hideInput">
            <v-stepper v-model="stage">
                <v-stepper-header>
                    <v-stepper-step :complete="stage > 1" step="1">Input Information</v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step :complete="stage > 2" step="2">Select Group</v-stepper-step>
                </v-stepper-header>

                <v-stepper-items>
                    <v-stepper-content step="1">
                        <v-card class="mb-5" color="lighten-1">
                            <v-text-field :disabled="updateMode" v-model="curItem.id" label="ID"></v-text-field>
                            <v-text-field v-model="curItem.name" label="NAME"></v-text-field>
                            <v-text-field v-model="curItem.password" label="PASSWORD" type="password"></v-text-field>
                            <v-text-field v-model="samepass" label="PASSWORD CHECK" type="password"></v-text-field>
                            <v-text-field v-model="curItem.groupName" label="Group Name" disabled></v-text-field>
                            <v-select :items="usertype" label="Type" solo></v-select>
                        </v-card>
                        <v-btn color="primary" @click="stage = 2">Continue</v-btn>
                    </v-stepper-content>

                    <v-stepper-content step="2">
                        <v-card class="mb-5" color="lighten-1">
                            <v-treeview :items="groupItem" activatable :active.sync="active"
                                        active-class="primary--text">
                                <v-icon slot="prepend" slot-scope="{ item, active }" :color="active ? 'primary' : ''">
                                    mdi-account
                                </v-icon>
                            </v-treeview>
                        </v-card>
                        <v-btn color="primary" @click="send">Confirm</v-btn>
                    </v-stepper-content>
                </v-stepper-items>
            </v-stepper>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            search: "",
            headers: [
                {text: "id", value: "id"},
                {text: "name", value: "name"},
                {text: "type", value: "type", sortable: false},
                {text: "groupName", value: "groupName", sortable: false},
                {text: "actions", value: "actions", sortable: false}
            ],
            curItem: Object.assign({},UserModel),
            stage: 3,
            updateMode: false,
            hideInput: false,
            samepass: "",
            usertype: ["USER", "ADMIN"],
            active: [],
            groupItem: [],
        }),
        created: function () {
            let _this = this;
            axios.get("group")
                .then(value => _this.groupItem.push(value.data))
                .catch(reason => catchPromise(reason));
        },
        methods: {
            page : function (page){
                return axios.get("user", {
                    params : page
                })
            },
            selectedTreeId() {
                if (!this.active.length) return undefined;
                return this.active[0];
            },
            addItem() {
                this.updateMode = false;
                this.curItem = Object.assign({},UserModel);
                this.stage = 1;
                this.hideInput = true;
            },
            editItem(item) {
                this.updateMode = true;
                this.curItem = item;
                this.stage = 1;
                this.hideInput = true;
            },
            deleteItem(item) {
                if (confirm("Are you sure you want to delete this item?")) {
                    return axios.delete("user/" + item.id)
                }
            },
            send() {
                let _this = this;
                this.curItem.groupId = this.selectedTreeId();
                let promise = axios.put("user", _this.curItem);
                if (this.updateMode) promise = axios.post("user", _this.curItem)
                promise.then(value => {
                    console.log(value);
                    if (!_this.updateMode)
                    _this.$refs.table.addFunction(_this.curItem)
                }).catch(reason => catchPromise(reason));
                this.stage = 3;
                this.hideInput = false;
            }
        }
    };
</script>