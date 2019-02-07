<template>
    <v-layout column>
        <v-flex>
            <v-card>
                <v-card-title class="title">Acl List</v-card-title>
                <v-layout>
                    <table-component :header="aclheader" :pagefunction="aclpage" :addfunction=null :deletefunction="delacl" :editfunction="editacl" :search=null></table-component>
                </v-layout>
                <v-card-actions>
                    <v-btn color="primary">New ACL</v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>

        <v-flex>
            <v-card>
                <h5 class="title mb-0">User/Group</h5>
                <v-layout>

                    <v-flex class="mr-3">
                        <table-component :header="ugheader" :pagefunction="ugpage" :addfunction=null :deletefunction=null :editfunction=null :search=null></table-component>
                    </v-flex>

                    <v-flex>
                        <v-list>
                            <v-list-tile v-for="value in acllist">
                                <v-list-tile-action>
                                    <v-checkbox></v-checkbox>
                                </v-list-tile-action>

                                <v-list-tile-content>
                                    <v-list-tile-title>{{value.aclName}}</v-list-tile-title>
                                    <v-list-tile-sub-title></v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>
                        <v-btn color="primary">Confirm</v-btn>
                    </v-flex>

                </v-layout>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        created: function created() {
            let _this = this;
            axios.get("acl", {
                params : getJavaMaxPage()
            }).then(value => {
                let max = value.data.content.length;
                for (let i = 0; i < max; i++) {
                    _this.acllist.push(value.data.content[i]);
                }
            }).catch(reason => catchPromise(reason));
        },
        data: () => ({
            aclheader: [
                {text: 'aclId', value: 'aclId'},
                {text: 'aclName', value: 'aclName'},
                {text: "action", sortable : false}
            ],
            ugheader: [
                {text: 'name', value: 'name'}
            ],
            acllist : []
        }),
        methods: {
            aclpage: function aclpage(page) {
                return axios.get("acl", {
                    params : page
                })
            },
            delacl: function delacl(item) {
                return axios.delete("acl/" + item.aclId)
            },
            editacl: function editacl(item) {
                return axios.post("acl" ,item)
            },
            ugpage: function ugpage(page) {
                return axios.get("group/list",{
                    params : page
                })
            }

        }
    }
</script>