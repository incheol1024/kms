<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
        <v-item-group v-model="window" class="shrink mr-4" mandatory tag="v-flex">
            <v-item v-for="n in 3" :key="n">
                <div slot-scope="{ active, toggle }">
                    <v-btn :input-value="active" icon @click="toggle">
                        <v-icon>fiber_manual_record</v-icon>
                    </v-btn>
                </div>
            </v-item>
        </v-item-group>

        <v-flex>
            <v-window v-model="window" class="elevation-1" vertical>
                <v-window-item>
                    <h3>Site List</h3>
                    <v-card>
                        <v-list>
                            <v-list-tile v-for="site in siteData" :key="site.siteId" @click="setSite(site)">
                                <v-list-tile-content>
                                    <v-list-tile-title v-html="site.name"></v-list-tile-title>
                                </v-list-tile-content>
                                <v-list-tile-action>
                                    <v-btn icon ripple>
                                        <v-icon color="grey lighten-1" @click="deleteSite(site)">close</v-icon>
                                    </v-btn>
                                </v-list-tile-action>
                            </v-list-tile>
                        </v-list>
                        <v-text-field v-model="siteName" label="SiteName" single-line hide-details></v-text-field>
                        <v-btn color="primary" @click="addSite">Add Site</v-btn>
                    </v-card>
                </v-window-item>

                <v-window-item>
                    <h3>Site : {{curSite.name}}</h3>
                    <table-component ref="table" :headers="siteHeader" :page-req="getProjects" :allow-delete="true"
                                     :delete-function="deleteProject" :click-row="getBoard"></table-component>
                    <v-btn color="primary" @click="dialog = true">Add Project</v-btn>
                </v-window-item>

                <v-window-item>
                    <h3>Project : {{curProject.name}}</h3>
                    <table-component ref="table" :headers="boardHeader"></table-component>
                    <v-btn color="primary" @click="addBoard">Add Board</v-btn>
                </v-window-item>
            </v-window>
        </v-flex>

        <v-dialog v-model="dialog" width="500">
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
            this.getSiteList(this.id);
        },
        watch: {
            id: function (id) {
                this.getSiteList(id);
            }
        },
        data: () => ({
            window: 0,
            siteData: [],
            curSite: copyObject(SITEMODEL),
            siteName: "",

            curProject: copyObject(PROJECTMODEL),
            dialog: false,
            startDialog: false,
            endDialog: false,
            siteHeader: [
                {text: "name", value: "name"},
                {text: "startDate", value: "startDate"},
                {text: "endDate", value: "endDate"},
                {text: "mangerName", value: "mangerName", sortable: false},
                {text: "action", value: "action", sortable: false}
            ],

            boardHeader : [

            ]
        }),
        methods: {
            writePage: function writepage() {
                router.push("sites/write/" + this.id);
            },
            getSiteList: function (id) {
                console.log("/getSiteList : "+id+" this"+this.data );
                _this = this;
                axios.get(`site/${id}`).then(res => {
                    _this.siteData = res.data;
                }).catch(reason => catchPromise(reason))
            },
            setSite: function (site) {
                let _this = this;
                this.curSite = site;
                this.getProjects().then(res => {
                    for (const key in res.data.content)
                        _this.$refs.table.addFunction(res.data.content[key])
                }).catch(reason => catchPromise(reason));
                this.window = 1;
            },
            getProjects: function (page) {
                if (this.curSite.siteId === 0) return;
                return axios.get(`site/${this.id}/${this.curSite.siteId}`, {
                    params: page
                })
            },
            addSite: function () {
                let _this = this;
                let site = copyObject(SITEMODEL);
                site.menuId = this.id;
                site.name = this.siteName;
                axios.put(`site`, site).then(res => {
                    site.siteId = res.data;
                    _this.siteData.push(site);
                }).catch(reason => catchPromise(reason))
            },
            deleteSite: function (site) {
                let _this = this;
                axios.delete(`site/${site.siteId}`).then(res => {
                    _this.siteData.splice(_this.siteData.indexOf(site), 1)
                }).catch(reason => catchPromise(reason));
            },
            addProject: function () {
                let _this = this;
                _this.curProject.siteId = _this.curSite.siteId;
                axios.put(`site/${_this.curSite.siteId}`, _this.curProject).then(res => {
                    _this.curProject.projectId = res.data;
                    _this.$refs.table.addFunction(_this.curProject);
                }).catch(reason => catchPromise(reason));
                this.dialog = false;
            },
            deleteProject: function (item) {
                return axios.delete(`site/${item.siteId}/${item.projectId}`)
            },
            getBoard : function (item) {
                this.curProject = item;
                this.window = 2;
                console.log("get Board call :"+item);
                console.log(item);
                if (this.curSite.siteId === 0) return;
                return axios.get(`site/${item.siteId}/${item.projectId}`, {
                    params: {page:0,size:5}
                })
            }
        }
    };
</script>