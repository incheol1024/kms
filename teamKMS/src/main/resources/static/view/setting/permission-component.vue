<template>
    <v-layout>
        <v-flex>
            <v-card>
                <v-card-title>
                    <div>
                        <h3 class="headline mb-0">Acl List</h3>
                        <v-list two-line>
                            <template v-for="data in listData" :key="i">
                                <v-list-tile @click="">
                                    <v-list-tile-content>
                                        <v-list-tile-title>{{data.aclId}}</v-list-tile-title>
                                        <v-list-tile-sub-title>{{data.aclName}}</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </template>
                        </v-list>
                    </div>
                </v-card-title>
                <v-card-actions>
                    <v-btn color="primary" @click="newAcl">New</v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>

        <v-flex v-if="showform">
            <v-card>
                <v-card-title class="title">Edit Acl</v-card-title>
                <v-layout>
                    <v-form>
                        <v-text-field label="Acl Id"></v-text-field>
                        <v-text-field label="Acl Name"></v-text-field>
                        <v-container fluid>
                            <v-layout v-for="i in 3" :key="i">
                                <v-checkbox v-model="selected" label="User" :value="i"></v-checkbox>
                                <v-checkbox v-model="selected" label="Group" :value="i+3"></v-checkbox>
                                <v-checkbox v-model="selected" label="Permission" :value="i+6"></v-checkbox>
                                <v-checkbox v-model="selected" label="Qna" :value="i+9"></v-checkbox>
                                <v-checkbox v-model="selected" label="SOL" :value="i+12"></v-checkbox>
                                <v-checkbox v-model="selected" label="SITE" :value="i+15"></v-checkbox>
                            </v-layout>
                        </v-container>
                    </v-form>
                </v-layout>
                <v-card-actions>
                    <v-btn color="primary" @click="confirmAcl">Confirm</v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>

        <v-flex v-if="showlist">
            <v-card>
                <v-card-title class="title">User/Group List</v-card-title>
                <v-layout>
                    <table-component :header="header" :pagefunction="ugpage" :addfunction=null
                                     :deletefunction="delacl" :editfunction="editacl" :search=null></table-component>
                </v-layout>
                <v-card-actions>
                    <v-btn color="primary" @click="setList">Set</v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    module.exports = {
        data: () => ({
            showlist: true,
            showform: true,
            listData: [],
            header: [
                {text: 'name', value: 'name'},
                {text: 'type', value: 'type'}
            ],

            selected: []
        }),
        created: function created() {
            let _this = this;
            axios.get("acl", getJavaMaxPage())
                .then(res => {
                    _this.listData = res.data.content;
                })
                .catch(reason => catchPromise(reason));
        },
        methods: {
            ugpage: function aclpage(page) {
                return new Promise(page);
            },
            confirmAcl: function confirmAcl(item) {
            },
            setList: function setList(item) {
            },
            newAcl: function newAcl() {

            }

        }
    }
</script>