<template>
    <v-layout>
        <v-flex>
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
        </v-flex>
        <v-flex>
            <h3>Site : {{curSite.name}}</h3>
            <table-component :headers="header" :page-req="getProjects" :allow-delete="true"
                             :delete-function="deleteProject"></table-component>
            <v-btn color="primary" @click="dialog = true">Add Project</v-btn>
        </v-flex>

        <v-dialog v-model="dialog" width="500">
            <v-card>
                <v-card-title class="headline primary lighten-2" primary-title>
                    ADD NEW PROJECT
                </v-card-title>
                <v-card-text>
                    <v-container grid-list-md>
                        <v-layout wrap>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="curProject.name" label="Name"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="curProject.startDate" label="Start Date"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="curProject.endDate" label="End Date"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="curProject.manager" label="Manager"></v-text-field>
                            </v-flex>
                        </v-layout>
                    </v-container>
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
            },
            dialog:  function (dialog) {
                if(!dialog) this.curProject = Object.assign({}, PROJECTMODEL);
            }
        },
        data: () => ({
            siteData: [],
            curSite: Object.assign({}, SITEMODEL),
            siteName: "",
            curProject: Object.assign({}, PROJECTMODEL),
            dialog: false,
            header: [
                {text: "name", value: "name"},
                {text: "startDate", value: "startDate"},
                {text: "endDate", value: "endDate"},
                {text: "mangerName", value: "mangerName", sortable: false},
                {text: "action", value: "action", sortable: false}
            ]
        }),
        methods: {
            writePage: function writepage() {
                router.push("sites/write/" + this.id);
            },
            getSiteList: function (id) {
                _this = this;
                axios.get(`site/${id}`).then(res => {
                    _this.siteData = res.data;
                }).catch(reason => openError(reason))
            },
            setSite: function (site) {
                this.curSite = site;
            },
            getProjects: function (page) {
                if (this.curSite.siteId === 0) return;
                return axios.get(`site/${this.id}/${this.curSite.siteId}`, {
                    params: page
                })
            },
            addSite: function () {
                let _this = this;
                let site = Object.assign({}, SITEMODEL);
                site.menuId = this.id;
                site.name = this.siteName;
                console.log(site);
                axios.put(`site`, site).then(res => {
                    site.siteId = res.data;
                    _this.siteData.push(site);
                }).catch(reason => openError(reason))
            },
            deleteSite: function (site) {
                let _this = this;
                axios.delete(`site/${site.siteId}`).then(
                    _this.siteData.splice(_this.siteData.indexOf(site), 1)
                ).catch(reason => openError(reason));
            },
            addProject: function () {

            },
            deleteProject: function (item) {

            }
        }
    };
</script>