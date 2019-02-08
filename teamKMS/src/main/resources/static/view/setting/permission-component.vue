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
                <v-card-title class="title">Detail</v-card-title>
                <v-layout>
                    <v-flex class="mr-3">

                    </v-flex>
                </v-layout>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            aclheader: [
                {text: 'aclId', value: 'aclId'},
                {text: 'aclName', value: 'aclName'},
                {text: "action", sortable : false}
            ]
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
            }

        }
    }
</script>