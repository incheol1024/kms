<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
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
                        <v-spacer></v-spacer>
                        <v-text-field v-model="siteName" label="input SiteName" single-line hide-details></v-text-field>
                        <v-btn color="primary" @click="addSite">Add Site</v-btn>
                    </v-card>
                </v-window-item>


            </v-window>
        </v-flex>
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
                this.window=0;
                this.getSiteList(id);
            },

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

        }),
        methods: {
            getSiteList: function (id) {
                _this = this;
                axios.get(`site/${id}`).then(res => {
                    _this.siteData = res.data;
                }).catch(reason => catchPromise(reason))
            },
            setSite: function (site) {
                let _this = this;
                this.curSite = site;
                this.window = 1;
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
                if (confirm("삭제하시겠습니까?")) {
                    axios.delete(`site/${site.siteId}`).then(res => {
                        _this.siteData.splice(_this.siteData.indexOf(site), 1)
                    }).catch(reason => catchPromise(reason));
                }
            },
            getProjects: function (page) {
                if (this.curSite.siteId === 0) return;
                //  _this.$refs.table.clear();//중복으로 데이터 첨가 되는거 클리어 처리 .
                return axios.get(`site/${this.id}/${this.curSite.siteId}`, {
                    params: page
                })
            }
        }
    };
</script>