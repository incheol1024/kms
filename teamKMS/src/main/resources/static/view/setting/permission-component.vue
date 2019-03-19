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
                                        <v-chip v-for="value in curAcl.hasPermission" :key="value.value"
                                                v-if="value.has" close v-model="value.has" @input="deleteRule(value)">
                                            {{value.name}}
                                        </v-chip>
                                        <h6>Add NewRule</h6>
                                        <v-select :items="addPermission" outline item-text="name"
                                                  @change="addRule"></v-select>
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
                                    <table-component ref="table" :headers="headers" :page-req="aceReq"
                                                     :allow-delete="true" :delete-function="deleteAce"></table-component>
                                </v-layout>
                            </v-card-title>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-text-field class="mr2" label="Ace(Gropp/User) Id" v-model="aceText"></v-text-field>
                                <v-btn color="primary" @click="addAce">AddAce</v-btn>
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
            curAcl: copyObject( ACLMODEL),
            listData: [],
            updateMode: false,
            aclText: "Make",

            curAce : copyObject( ACEMODEL),
            aceText : "",
            headers: [
                {text: 'aceId', value: 'aceId'},
                {text: 'action', value: 'action', sortable: false}
            ]
        }),
        created: function created() {
            let _this = this;
            axios.get("acl", getJavaMaxPage()).then(res => {
                _this.listData = res.data.content;
            }).catch(reason => catchPromise(reason));
        },
        computed: {
            addPermission: function () {
                return this.curAcl.hasPermission.filter(permission => !permission.has);
            }
        },
        methods: {
            confirmAcl: function confirmAcl() {
                let _this = this;
                if (this.updateMode)
                    axios.post("acl", _this.curAcl).catch(reason => openError(reason));
                else
                    axios.put("acl", _this.curAcl).then(res => {
                        _this.listData.push(_this.curAcl)
                    }).catch(reason => openError(reason));
                this.updateMode = false;
                this.curAcl = copyObject( ACLMODEL);
            },
            deleteAcl: function deleteAcl() {
                let _this = this;
                axios.delete("acl/" + _this.curAcl.aclId).then(res => {
                    _this.listData.splice(_this.listData.indexOf(_this.curAcl), 1)
                }).catch(reason => openError(reason));
                this.newAcl();
            },
            newAcl: function newAcl() {
                this.updateMode = false;
                this.curAcl = copyObject( ACLMODEL);
                this.aclText = "Make";
            },
            setItem: function setItem(item) {
                this.curAcl = item;
                this.aclText = "Update";
                this.updateMode = true;
                this.$refs.table.sync();
            },
            addRule: function addRule(item) {
                this.curAcl.hasPermission.filter(it => it.value === item)[0].has = true;
            },
            deleteRule: function deleteRule(item) {
                item.has = false;
            },
            aceReq: function aceReq(page) {
                if (this.curAcl.aclId !== "") {
                    return axios.get("ace/" + this.curAcl.aclId, {
                        params: page
                    })
                }
            },
            deleteAce : function deleteAce(item) {
                return axios.delete("ace/" + item.aclId + "/" + item.aceId);
            },
            addAce : function addAce(){
                if (this.curAcl.aclId !== "") {
                    _this = this;
                    let ace = {"aclId": _this.curAcl.aclId, "aceId": _this.aceText};
                    axios.put("ace", ace).then(res => {_this.$refs.table.addFunction(ace)}).catch(reason => openError(reason));
                }
            }
        }
    }
</script>