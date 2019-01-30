<template>
    <v-layout column>
        <v-flex class="mb-5">
            <v-card>
                <v-card-title> User List
                    <v-spacer></v-spacer>
                    <v-text-field v-model="search" append-icon="search" label="Search" single-line
                                  hide-details></v-text-field>
                    <v-btn color="primary" dark class="mb-2" @click="addItem">New User</v-btn>
                </v-card-title>
                <v-data-table :headers="headers" :items="items" :loading="loading" :search="search" class="elevation-1">
                    <template slot="items" slot-scope="props">
                        <td>{{ props.item.id }}</td>
                        <td>{{ props.item.name }}</td>
                        <td>{{ props.item.type }}</td>
                        <td>{{ props.item.groupName }}</td>
                        <td>
                            <v-icon small class="mr-2" @click="editItem(props.item)">edit</v-icon>
                            <v-icon small @click="deleteItem(props.item)">delete</v-icon>
                        </td>
                    </template>
                </v-data-table>
            </v-card>
        </v-flex>

        <v-flex v-if="hideInput">
            <v-stepper v-model="stage">
                <v-stepper-header>
                    <v-stepper-step :complete="stage > 1" step="1">Input Infomation</v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step :complete="stage > 2" step="2">Select Group</v-stepper-step>
                </v-stepper-header>

                <v-stepper-items>
                    <v-stepper-content step="1">
                        <v-card class="mb-5" color="lighten-1">
                            <v-text-field :disabled="updateMode" v-model="curitem.id" label="ID"></v-text-field>
                            <v-text-field v-model="curitem.name" label="NAME"></v-text-field>
                            <v-text-field v-model="curitem.password" label="PASSWORD" type="password"></v-text-field>
                            <v-text-field v-model="samepass" label="PASSWORD CHECK" type="password"></v-text-field>
                            <v-text-field v-model="curitem.groupName" label="Group Name" disabled></v-text-field>
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
            loading: true,
            pagination: {},
            headers: [
                {text: "id", value: "id"},
                {text: "name", value: "name"},
                {text: "type", value: "type", sortable: false},
                {text: "groupName", value: "groupName", sortable: false},
                {text: "actions", value: "actions", sortable: false}
            ],
            items: [],
            curitem: JSON.parse(JSON.stringify(UserModel)),
            stage: 3,
            updateMode: false,
            hideInput: false,
            samepass: "",
            usertype: ["USER", "ADMIN"],
            active: [],
            groupItem: [],
        }),
        created: function () {
            this.loading = true;
            let _this = this;
            axios.get("user").then(value => {
                for (let i = 0; i < response.data.list.length; i++)
                    _this.items.push(response.data.list[i]);
            }).catch(catchPromise);
            axios.get("group")
                .then(value => _this.groupItem.push(response.data))
                .catch(catchPromise);
            this.loading = false;
        },
        methods: {
            selectedTreeId() {
                if (!this.active.length) return undefined;
                const id = this.active[0];
                return id;
            },
            addItem() {
                this.updateMode = false;
                this.curitem = JSON.parse(JSON.stringify(UserModel));
                this.stage = 1;
                this.hideInput = true;
            },
            editItem(item) {
                this.updateMode = true;
                this.curitem = item;
                this.stage = 1;
                this.hideInput = true;
            },
            deleteItem(item) {
                var _this = this;
                if (confirm("Are you sure you want to delete this item?")) {
                    axios.delete("user/" + item.id)
                        .then(value => _this.items.splice(_this.items.indexOf(item), 1))
                        .catch(catchPromise);
                }
            },
            send() {
                let _this = this;
                this.curitem.groupId = this.selectedTreeId();
                let promise = axios.put("user", _this.curitem);
                if (this.updateMode) promise = axios.post("user", _this.curitem)
                promise.then(value => { if (!_this.updateMode) _this.items.push(response.data[0]) })
                    .catch(catchPromise);
                this.stage = 3;
                this.hideInput = false;
            }
        }
    };
</script>