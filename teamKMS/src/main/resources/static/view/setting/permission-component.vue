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
                        <v-btn color="primary" @click="newAcl">New</v-btn>
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
                                        <v-layout v-for="i in 3" :key="i">
                                            <v-checkbox v-model="curAcl.hasPermission" label="User"
                                                        :value="i"></v-checkbox>
                                            <v-checkbox v-model="curAcl.hasPermission" label="Group"
                                                        :value="i+3"></v-checkbox>
                                            <v-checkbox v-model="curAcl.hasPermission" label="Permission"
                                                        :value="i+6"></v-checkbox>
                                            <v-checkbox v-model="curAcl.hasPermission" label="Qna"
                                                        :value="i+9"></v-checkbox>
                                            <v-checkbox v-model="curAcl.hasPermission" label="SOL"
                                                        :value="i+12"></v-checkbox>
                                            <v-checkbox v-model="curAcl.hasPermission" label="SITE"
                                                        :value="i+15"></v-checkbox>
                                        </v-layout>
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
                                    <v-layout>
                                        <v-spacer></v-spacer>
                                        <v-text-field v-model="search" append-icon="search" label="Search" single-line
                                                      hide-details></v-text-field>
                                    </v-layout>
                                    <table-component :headers="headers" :page-req="ugPage" :page-res="ugPageRes" :allow-select="true" :selection.sync="selection"
                                                     :search="search"></table-component>
                                </v-layout>
                            </v-card-title>
                            <v-card-actions>
                                <v-btn color="primary" @click="setACE">Set</v-btn>
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
            listData: [],
            headers: [
                {text: 'name', value: 'name'},
                {text: 'type', value: 'type', sortable: false}
            ],
            search: "",
            selection : [],
            aclText: "Make",
            curAcl: Object.assign({}, ACLMODEL),
            curAce: Object.assign({}, ACEMODEL)
        }),
        created: function created() {
            let _this = this;
            axios.get("acl", getJavaMaxPage()).then(res => {
                _this.listData = res.data.content;
            }).catch(reason => catchPromise(reason));
        },
        methods: {
            ugPage: function ugPage(page) {
                return axios.get("group/child",  {
                    params : page
                })
            },
            ugPageRes : function ugPageRes(response, _this){
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
            },
            confirmAcl: function confirmAcl() {
                return axios.put("acl", this.curAcl)
            },
            setACE: function setList(item) {
                //let _this = this;
                //axios.put("ace", item).then().catch(reason => catchPromise(reason));
            },
            newAcl: function newAcl() {
                this.curAcl = Object.assign({}, ACLMODEL);
                this.aclText = "Make";
            },
            setItem: function setItem(item) {
                this.curAcl = item;
                this.aclText = "Update";
            }

        }
    }
</script>