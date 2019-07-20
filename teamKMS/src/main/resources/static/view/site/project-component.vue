<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
               <v-flex>
            <v-window v-model="window" class="elevation-1" vertical>

                <v-window-item>
                    <h3>Site : {{curSite.name}}</h3>
                    <table-component ref="table" :headers="projectHeader" :page-req="getProjects" :allow-delete="true"
                                     :delete-function="deleteProject" :click-row="getBoard"></table-component>
                    <v-btn color="primary" @click="dialog = true">Add Project</v-btn>
                </v-window-item>
            </v-window>
        </v-flex>

        <v-dialog v-model="dialog" width="500" persistent="true">
            <v-card>
                <v-card-title class="headline primary lighten-2" primary-title>
                    ADD NEW PROJECT
                </v-card-title>
                <v-card-text>
                    <v-layout column>
                        <v-flex xs12 sm6 md4>
                            <v-text-field v-model="curProject.name" label="Name"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-menu v-model="startDialog" :close-on-content-click="false" :nudge-right="40" lazy
                                    transition="scale-transition" offset-y full-width min-width="290px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field v-model="curProject.startDate" label="Start Date"
                                                  prepend-icon="event"
                                                  readonly v-on="on">
                                    </v-text-field>
                                </template>
                                <v-date-picker v-model="curProject.startDate"
                                               @input="startDialog = false"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-menu v-model="endDialog" :close-on-content-click="false" :nudge-right="40" lazy
                                    transition="scale-transition" offset-y full-width min-width="290px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field v-model="curProject.endDate" label="End Date"
                                                  prepend-icon="event" readonly v-on="on">
                                    </v-text-field>
                                </template>
                                <v-date-picker v-model="curProject.endDate" @input="endDialog = false"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                            <v-text-field v-model="curProject.manager" label="Manager"></v-text-field>
                        </v-flex>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialog =false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="addProject">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
    module.exports = {
        props: ["id"],
        mounted: function () {
            //this.getSiteList(this.id);
        },
        watch: {
            id: function (id) {
                this.window=0;
                this.getSiteList(id);
            },
            'curSite.siteId': function () {
               this.$refs.table.sync();
            }
        },
        data: () => ({
            window: 0,
            curProject: copyObject(PROJECTMODEL),
            dialog: false,
            startDialog: false,
            endDialog: false,
            projectHeader: [
                {text: "프로젝트명", value: "name"},
                {text: "등급(상,중,하)", value: "grade"},
                {text: "진척현황", value: "step"},
                {text: "시작일자", value: "startDate"},
                {text: "완료일자", value: "endDate"},
                {text: "관리자", value: "manger", sortable: false},
                {text: "action", value: "action", sortable: false}
             ]
        }),
        methods: {
            getProjects: function (page) {
                if (this.curSite.siteId === 0) return;
                //  _this.$refs.table.clear();//중복으로 데이터 첨가 되는거 클리어 처리 .
                return axios.get(`site/${this.id}/${this.curSite.siteId}`, {
                    params: page
                })
            },
            addProject: function () {
                let _this = this;
                _this.curProject.siteId = _this.curSite.siteId;
                _this.curProject.projectId=0;
                axios.put(`site/${_this.curSite.siteId}`, _this.curProject).then(res => {
                    _this.curProject.projectId = res.data;
                    _this.$refs.table.addFunction(_this.curProject);
                }).catch(reason => catchPromise(reason));
                this.dialog = false;
            },
            deleteProject: function (item) {
                if (confirm("삭제하시겠습니까?"))
                    return axios.delete(`site/${item.siteId}/${item.projectId}`)
            },
            getBoard: function (item) {
                let _this = this;
                _this.curProject.name=item.name;
                _this.curProject.projectId = item.projectId;
                this.window = 2;
                this.$refs.table2.sync();
            },
            getPage_board: function (page) {
                let _this = this;
                if (this.curSite.siteId === 0) return;
                return axios.get(`site/${_this.id}/${_this.curSite.siteId}/${_this.curProject.projectId}`, {
                    params: page
                });
            }
        }
    };
</script>