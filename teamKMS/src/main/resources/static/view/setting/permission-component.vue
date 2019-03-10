<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
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
                                        <v-expansion-panel>
                                            <v-expansion-panel-content v-for="(item,i) in Items" :key="i">
                                                <template v-slot:header>
                                                    <div>{{item}}</div>
                                                </template>
                                                <v-card>
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
                                                </v-card>
                                            </v-expansion-panel-content>
                                        </v-expansion-panel>
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
                                        <v-layout>
                                            <v-spacer></v-spacer>
                                            <v-text-field v-model="search" append-icon="search" label="Search"
                                                          single-line
                                                          hide-details></v-text-field>
                                        </v-layout>
                                        <table-component :headers="headers" :page-req="ugPage" :page-res="ugPageRes"
                                                         :allow-select="true" :selection.sync="selection"
                                                         :search="search"></table-component>
                                    </v-flex>
                                    <v-flex>
                                        <v-chip v-for="(chip,i) in chipItem" :key="i" v-model="chip" close>{{chip.name}}</v-chip>
                                    </v-flex>
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
            Items: ["Create", "Delete", "Modify"],
            listData: [],
            headers: [
                {text: 'name', value: 'name'},
                {text: 'type', value: 'type', sortable: false}
            ],
            search: "",
            selection: [],
            aclText: "Make",
            curAcl: Object.assign({}, ACLMODEL),
            chipItem : []
        }),
        created: function created() {
            let _this = this;
            axios.get("acl", getJavaMaxPage()).then(res => {
                _this.listData = res.data.content;
            }).catch(reason => catchPromise(reason));
        },
        methods: {
            ugPage: function ugPage(page) {
                return axios.get("group/child", {
                    params: page
                })
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