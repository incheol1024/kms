<template>
    <v-container fluid grid-list-lg>
        <v-layout>
            <v-flex align-space-around>
                <v-card>
                    <v-card-title>
                        <v-layout wrap column>
                            <h3 class="headline mb-0">Acl List</h3>
                            <v-list two-line>
                                <template v-for="data in listData">
                                    <v-list-tile :key="data.aclId" ripple @click="setItem(data)">
                                        <v-list-tile-content>
                                            <v-list-tile-title>{{data.aclId}}</v-list-tile-title>
                                            <v-list-tile-sub-title>{{data.aclName}}</v-list-tile-sub-title>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                    <v-divider></v-divider>
                                </template>
                            </v-list>
                        </v-layout>
                    </v-card-title>
                    <v-card-actions>
                        <v-btn color="primary" @click="newAcl">NewAcl</v-btn>
                        <v-btn color="primary" @click="deleteAcl">Delete</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>

            <v-flex>
                <v-card>
                    <v-tabs color="transparent">
                        <v-tab>Edit Acl</v-tab>
                        <v-tab>Set User/Group</v-tab>
                        <v-tab-item>
                            <v-card-title class="title">
                                <v-layout wrap row>
                                    <v-form>
                                        <v-text-field label="Acl Id" v-model="curAcl.aclId"></v-text-field>
                                        <v-text-field label="Acl Name" v-model="curAcl.aclName"></v-text-field>
                                        <h6>Has Permission</h6>
                                        <v-chip v-for="value in curAcl.hasPermission" :key="value.value" v-if="value.has" close v-model="value.has" @input="deleteRule(value)">{{value.name}}</v-chip>
                                        <h6>Add NewRule</h6>
                                        <v-select :items="addPermission" outline item-text="name" @change="addRule"></v-select>
                                    </v-form>
                                </v-layout>
                            </v-card-title>
                            <v-card-actions>
                                <v-btn color="primary" @click="confirmAcl">{{aclText}}</v-btn>
                            </v-card-actions>
                        </v-tab-item>
                        <v-tab-item>
                            <v-card-title class="title">
                                <v-layout wrap column>
                                    <v-flex>
                                        <table-component ref="table" :headers="headers" :page-res="ugPageRes"></table-component>
                                    </v-flex>
                                </v-layout>
                            </v-card-title>
                            <v-card-actions>
                                <v-btn color="primary">Set</v-btn>
                            </v-card-actions>
                        </v-tab-item>
                    </v-tabs>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    module.exports = {
        data: () => ({
            curAcl: Object.assign({}, ACLMODEL),
            listData: [],
            updateMode : false,
            aclText : "Make",
            headers: [
                {text: 'name', value: 'name'},
                {text: 'type', value: 'type', sortable: false}
            ]
        }),
        created: function created() {
            let _this = this;
            axios.get("acl", getJavaMaxPage()).then(res => {
                _this.listData = res.data.content;
            }).catch(reason => catchPromise(reason));
        },
        computed : {
            addPermission: function () {
                return this.curAcl.hasPermission.filter(permission => !permission.has);
            }
        },
        methods: {
            confirmAcl: function confirmAcl() {
                let _this = this;
                if(this.updateMode)
                    axios.post("acl", _this.curAcl).catch(reason => openError(reason));
                else
                    axios.put("acl", _this.curAcl).then(_this.listData.push(_this.curAcl)).catch(reason => openError(reason));
                this.updateMode = false;
                this.curAcl = Object.assign({}, ACLMODEL);
            },
            deleteAcl : function deleteAcl(){
                let _this = this;
                axios.delete("acl/" + _this.curAcl.aclId).then(_this.listData.splice(_this.listData.indexOf(_this.curAcl),1)).catch(reason => openError(reason));
                this.newAcl();
            },
            newAcl: function newAcl() {
                this.updateMode = false;
                this.curAcl = Object.assign({}, ACLMODEL);
                this.aclText = "Make";
            },
            setItem: function setItem(item) {
                this.curAcl = item;
                this.aclText = "Update";
                this.updateMode = true;
            },
            addRule : function addRule(item) {
                this.curAcl.hasPermission.filter(it => it.value === item)[0].has = true;
            },
            deleteRule : function deleteRule(item) {
                item.has = false;
            },
            ugPageRes: function ugPageRes(response, _this) {
                let groupList = response.data.groupList.content;
                let userList = response.data.userList.content;
                _this.total = groupList.length + userList.length;
                for (let i = 0; i < groupList.length; i++) {
                    groupList[i].type = "Group";
                    _this.addFunction(groupList[i]);
                }
                for (let i = 0; i < userList.length; i++) {
                    userList[i].type = "User";
                    _this.addFunction(userList[i]);
                }
            }
        }
    }
</script>